package hexlet.code.formatters;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;

public class StylishFormatter implements FormatterFactory {
    /**
     * Make stylish output.
     */
    @Override
    public String format(Map<String, Object> firstMap, Map<String, Object> secondMap, LinkedHashSet<String> keySet) {

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        for (String keyElement : keySet) {
            if (!firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)) {
                sb.append("  + ").append(keyElement).append(": ").append(secondMap.get(keyElement)).append("\n");
                continue;
            }
            if (firstMap.containsKey(keyElement) && !secondMap.containsKey(keyElement)) {
                sb.append("  - ").append(keyElement).append(": ").append(firstMap.get(keyElement)).append("\n");
                continue;
            }
//            if (firstMap.get(keyElement).equals(secondMap.get(keyElement))) {
            if (Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
                sb.append("    ").append(keyElement).append(": ").append(firstMap.get(keyElement)).append("\n");
                continue;
            }
            if (firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)
//                    && !firstMap.get(keyElement).equals(secondMap.get(keyElement))) {
                    && !Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
                sb.append("  - ").append(keyElement).append(": ").append(firstMap.get(keyElement)).append("\n");
                sb.append("  + ").append(keyElement).append(": ").append(secondMap.get(keyElement)).append("\n");
            }
        }

        sb.append("}");
        return sb.toString();
    }
}
