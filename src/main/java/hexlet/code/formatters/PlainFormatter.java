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
                        append(getValueInPlainFormatOrReplaceIfComplexValue(secondMap.get(keyElement))).append("\n");
                continue;
            }
            if (firstMap.containsKey(keyElement) && !secondMap.containsKey(keyElement)) {
                sb.append("Property '").append(keyElement).append("' was removed").append("\n");
                continue;
            }
            if (firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)
                    && !Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
                sb.append("Property '").append(keyElement).append("' was updated. From ").
                        append(getValueInPlainFormatOrReplaceIfComplexValue(firstMap.get(keyElement))).append(" to ").
                        append(getValueInPlainFormatOrReplaceIfComplexValue(secondMap.get(keyElement))).append("\n");
            }
        }
        return sb.toString().trim();
    }

    private static String getValueInPlainFormatOrReplaceIfComplexValue(Object inputValue) {
        if (inputValue == null) {
            return null;
        }
        String inputValueString = inputValue.toString();
//        if ((inputValueString.startsWith("[") && inputValueString.endsWith("]"))
//                || (inputValueString.startsWith("{") && inputValueString.endsWith("}"))) {
        if (inputValue.getClass().getSimpleName().equals("ArrayList")
                || inputValue.getClass().getSimpleName().equals("LinkedHashMap")) {
            return "[complex value]";
        }
        if (inputValue instanceof String) {
            return "'" + inputValueString + "'";
        }
        return inputValueString;
    }
}
