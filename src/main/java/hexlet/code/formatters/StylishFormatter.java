package hexlet.code.formatters;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class StylishFormatter implements Formatter {
    private static final String ADDED = "  + ";
    private static final String REMOVED = "  - ";
    private static final String UNAFFECTED = "    ";
    private static final String COLON = ": ";
    private static final String NEW_LINE = "\n";
    /**
     * Make stylish output.
     */
    @Override
    public String format(Map<String, Object> firstMap, Map<String, Object> secondMap, Set<String> keySet) {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(NEW_LINE);

        for (String keyElement : keySet) {
            if (!firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)) {
                sb.append(ADDED).append(keyElement).append(COLON).append(secondMap.get(keyElement)).append(NEW_LINE);
                continue;
            }
            if (firstMap.containsKey(keyElement) && !secondMap.containsKey(keyElement)) {
                sb.append(REMOVED).append(keyElement).append(COLON).append(firstMap.get(keyElement)).append(NEW_LINE);
                continue;
            }
            if (Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
                sb.append(UNAFFECTED).append(keyElement).append(COLON).append(firstMap.get(keyElement)).
                        append(NEW_LINE);
                continue;
            }
            if (firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)
                    && !Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
                sb.append(REMOVED).append(keyElement).append(COLON).append(firstMap.get(keyElement)).append(NEW_LINE);
                sb.append(ADDED).append(keyElement).append(COLON).append(secondMap.get(keyElement)).append(NEW_LINE);
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
