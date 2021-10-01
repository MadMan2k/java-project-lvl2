package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class StylishFormatter implements Formatter {
    private static final String FIELD = "field";
    private static final String OLD_VALUE = "oldValue";
    private static final String NEW_VALUE = "newValue";
    private static final String ADDED = "  + ";
    private static final String REMOVED = "  - ";
    private static final String UNAFFECTED = "    ";
    private static final String COLON = ": ";
    private static final String NEW_LINE = "\n";
    /**
     * Make stylish output.
     */
    @Override
    public String format(List<Map<String, Object>> differences) {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(NEW_LINE);

        for(Map<String, Object> map : differences) {
            switch (map.get("status").toString()) {
                case "added" -> sb.append(ADDED).append(map.get(FIELD)).append(COLON).append(map.get(NEW_VALUE)).
                        append(NEW_LINE);
                case "removed" -> sb.append(REMOVED).append(map.get(FIELD)).append(COLON).append(map.get(OLD_VALUE)).
                        append(NEW_LINE);
                case "unaffected" -> sb.append(UNAFFECTED).append(map.get(FIELD)).append(COLON).append(map.get(OLD_VALUE)).
                        append(NEW_LINE);
                case "updated" -> {
                    sb.append(REMOVED).append(map.get(FIELD)).append(COLON).append(map.get(OLD_VALUE)).append(NEW_LINE);
                    sb.append(ADDED).append(map.get(FIELD)).append(COLON).append(map.get(NEW_VALUE)).append(NEW_LINE);
                }
                default -> throw new IllegalArgumentException (map.get("status").toString()
                        + " is bad status. StylishFormatter error");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
