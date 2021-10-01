package hexlet.code.formatters;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PlainFormatter implements Formatter {
    private static final String PROPERTY = "Property '";
    private static final String FIELD = "field";
    private static final String OLD_VALUE = "oldValue";
    private static final String NEW_VALUE = "newValue";
    private static final String NEW_LINE = "\n";
    /**
     * Make plain output.
     */
    @Override
    public String format(List<Map<String, Object>> differences) {
        StringBuilder sb = new StringBuilder();

        for(Map<String, Object> map : differences) {
            if (map.get("status").toString().equals("unaffected")) {
                continue;
            }
            sb.append(PROPERTY).append(map.get(FIELD));
            switch (map.get("status").toString()) {
                case "added" -> sb.append("' was added with value: ").append(formatValue(map.get(NEW_VALUE))).append(NEW_LINE);
                case "removed" -> sb.append("' was removed").append(NEW_LINE);
                case "updated" -> sb.append("' was updated. From ").append(formatValue(map.get(OLD_VALUE))).
                        append(" to ").append(formatValue(map.get(NEW_VALUE))).append(NEW_LINE);
                default -> throw new IllegalArgumentException (map.get("status").toString()
                        + " is bad status. PlainFormatter error");
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
