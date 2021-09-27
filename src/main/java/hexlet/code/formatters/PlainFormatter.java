package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PlainFormatter implements Formatter {
    /**
     * Make plain output.
     */
    @Override
    public String format(Map<String, Object> firstMap, Map<String, Object> secondMap, Set<String> keySet) {
        StringBuilder sb = new StringBuilder();

        for (String keyElement : keySet) {
            if (!firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)) {
                sb.append("Property '").append(keyElement).append("' was added with value: ").
                        append(formatValue(secondMap.get(keyElement))).append("\n");
                continue;
            }
            if (firstMap.containsKey(keyElement) && !secondMap.containsKey(keyElement)) {
                sb.append("Property '").append(keyElement).append("' was removed").append("\n");
                continue;
            }
            if (firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)
                    && !Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
                sb.append("Property '").append(keyElement).append("' was updated. From ").
                        append(formatValue(firstMap.get(keyElement))).append(" to ").
                        append(formatValue(secondMap.get(keyElement))).append("\n");
            }
        }
        return sb.toString().trim();
    }

    private static String formatValue(Object inputValue) {
        if (inputValue == null) {
            return null;
        }
        String inputValueString = inputValue.toString();
        if (inputValue instanceof Collection
                || inputValue instanceof Map) {
            return "[complex value]";
        }
        if (inputValue instanceof String) {
            return "'" + inputValueString + "'";
        }
        return inputValueString;
    }
}
