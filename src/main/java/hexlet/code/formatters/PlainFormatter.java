package hexlet.code.formatters;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;

public class PlainFormatter implements FormatterFactory {
    /**
     * Make plain output.
     */
    @Override
    public String format(Map<String, Object> firstMap, Map<String, Object> secondMap, LinkedHashSet<String> keySet) {
//
//        System.out.println(firstMap.toString());
//        System.out.println(secondMap.toString());

        StringBuilder sb = new StringBuilder();

        for (String keyElement : keySet) {
            if (!firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)) {
                sb.append("Property '").append(keyElement).append("' was added with value: ").
                        append(replaceIfComplexValue(secondMap.get(keyElement))).append("\n");
                continue;
            }
            if (firstMap.containsKey(keyElement) && !secondMap.containsKey(keyElement)) {
                sb.append("Property '").append(keyElement).append("' was removed").append("\n");
                continue;
            }
            if (firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)
                    && !Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
                sb.append("Property '").append(keyElement).append("' was updated. From ").
                        append(replaceIfComplexValue(firstMap.get(keyElement))).append(" to ").
                        append(replaceIfComplexValue(secondMap.get(keyElement))).append("\n");
            }
        }
        return sb.toString().trim();
    }

    private static String replaceIfComplexValue(Object inputValue) {
        if (inputValue == null) {
//            inputValue = "null";
            return null;
        }
        String inputValueString = inputValue.toString();
        if ((inputValueString.startsWith("[") && inputValueString.endsWith("]"))
                || (inputValueString.startsWith("{") && inputValueString.endsWith("}"))) {
            return "[complex value]";
        }
        if (inputValue instanceof String) {
            return "'" + inputValueString + "'";
        }
//        if (inputValue == null) {
//            return null;
//        }

        return inputValueString;
    }
}
