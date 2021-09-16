package hexlet.code.formatters;


import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsonFormatter implements FormatterFactory {
    /**
     * Make JSON output.
     */
    @Override
    public String format(Map<String, Object> firstMap, Map<String, Object> secondMap, LinkedHashSet<String> keySet) {

        StringBuilder sb = new StringBuilder();
        sb.append("{\n").append("\t\"jsonDiff\": [\n");

        for (String keyElement : keySet) {
            if (!firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)) {
                sb.append("\t\t{\n").append("\t\t\t\"field\": \"").append(keyElement).append("\",\n").
                        append("\t\t\t\"newValue\": ").append(replaceIfComplexValue(secondMap.get(keyElement))).
                        append(",\n").
                        append("\t\t\t\"status\": \"added\"\n\t\t},\n");

//                sb.append("  + ").append(keyElement).append(": ").append(secondMap.get(keyElement)).append("\n");
                continue;
            }
            if (firstMap.containsKey(keyElement) && !secondMap.containsKey(keyElement)) {
                sb.append("\t\t{\n").append("\t\t\t\"field\": \"").append(keyElement).append("\",\n").
                        append("\t\t\t\"oldValue\": ").append(replaceIfComplexValue(firstMap.get(keyElement))).
                        append(",\n").
                        append("\t\t\t\"status\": \"removed\"\n\t\t},\n");

//                sb.append("  - ").append(keyElement).append(": ").append(firstMap.get(keyElement)).append("\n");
                continue;
            }
            if (Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
                sb.append("\t\t{\n").append("\t\t\t\"field\": \"").append(keyElement).append("\",\n").
                        append("\t\t\t\"oldValue\": ").append(replaceIfComplexValue(firstMap.get(keyElement))).
                        append(",\n").
                        append("\t\t\t\"newValue\": ").append(replaceIfComplexValue(secondMap.get(keyElement))).
                        append(",\n").
                        append("\t\t\t\"status\": \"unaffected\"\n\t\t},\n");

//                sb.append("    ").append(keyElement).append(": ").append(firstMap.get(keyElement)).append("\n");
                continue;
            }
            if (firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)
                    && !Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
                sb.append("\t\t{\n").append("\t\t\t\"field\": \"").append(keyElement).append("\",\n").
                        append("\t\t\t\"oldValue\": ").append(replaceIfComplexValue(firstMap.get(keyElement))).
                        append(",\n").
                        append("\t\t\t\"newValue\": ").append(replaceIfComplexValue(secondMap.get(keyElement))).
                        append(",\n").
                        append("\t\t\t\"status\": \"updated\"\n\t\t},\n");

//                sb.append("  - ").append(keyElement).append(": ").append(firstMap.get(keyElement)).append("\n");
//                sb.append("  + ").append(keyElement).append(": ").append(secondMap.get(keyElement)).append("\n");
            }
        }
        sb.setLength(sb.length() - 2);
        sb.append("\n\t]\n}");
        return sb.toString();
    }

    private static String replaceIfComplexValue(Object inputValue) {
        if (inputValue == null) {
            return null;
        }
        String clazz = inputValue.getClass().getSimpleName();
        switch (clazz) {
            case "Boolean":
            case "Integer":
                return inputValue.toString();
            case "ArrayList":
                List<Object> list = (List<Object>) inputValue;
                StringBuilder arrListSb = new StringBuilder();
                arrListSb.append("[");
                for (Object o: list) {
                    arrListSb.append(replaceIfComplexValue(o)).append(", ");
                }
                arrListSb.setLength(arrListSb.length() - 2);
                arrListSb.append("]");
                return arrListSb.toString();
            case "LinkedHashMap":
                Map<Object, Object> map = (LinkedHashMap<Object, Object>) inputValue;
                StringBuilder lHMSb = new StringBuilder();
                lHMSb.append("{\n");
                for (Map.Entry<Object, Object> e: map.entrySet()) {
                    lHMSb.append("\t\t\t\t").append(replaceIfComplexValue(e.getKey())).append(": ").
                            append(replaceIfComplexValue(e.getValue())).append(",\n");
                }
                lHMSb.setLength(lHMSb.length() - 2);
                lHMSb.append("\n\t\t\t}");
                return lHMSb.toString();

            default:
                return "\"" + inputValue.toString() + "\"";
        }
    }
}
