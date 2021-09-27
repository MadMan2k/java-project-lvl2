package hexlet.code.formatters;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PlainFormatter implements Formatter {
    private static final String PROPERTY = "Property '";
    private static final String NEW_LINE = "\n";
    /**
     * Make plain output.
     */
    @Override
    public String format(Map<String, Object> firstMap, Map<String, Object> secondMap, Set<String> keySet) {
        StringBuilder sb = new StringBuilder();

        for (String keyElement : keySet) {
            if (!firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)) {
                sb.append(PROPERTY).append(keyElement).append("' was added with value: ").
                        append(formatValue(secondMap.get(keyElement))).append(NEW_LINE);
                continue;
            }
            if (firstMap.containsKey(keyElement) && !secondMap.containsKey(keyElement)) {
                sb.append(PROPERTY).append(keyElement).append("' was removed").append(NEW_LINE);
                continue;
            }
            if (firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)
                    && !Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
                sb.append(PROPERTY).append(keyElement).append("' was updated. From ").
                        append(formatValue(firstMap.get(keyElement))).append(" to ").
                        append(formatValue(secondMap.get(keyElement))).append(NEW_LINE);
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
