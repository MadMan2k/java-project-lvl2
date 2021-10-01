/**
 * This class is reserved for better human-readable formatted JSON output but NOT used in this CLI by default
 *
 * If you want to use this type of output:
 *      - uncomment this class;
 *      - uncomment line "case "jsonform" -> new FormattedJsonFormatter();" in FormatterProvider.java
 *      - uncomment line "import hexlet.code.formatters.FormattedJsonFormatter;" in FormatterProvider.java
 *
 * Now you can build your CLI and use formatted JSON output by using "jsonform" as output format name
 *
 * Enjoy
 */
//package hexlet.code.formatters;
//
//
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//
//public class FormattedJsonFormatter implements Formatter {
//    /**
//     * Make formatted JSON output.
//     */
//    @Override
//    public String format(Map<String, Object> firstMap, Map<String, Object> secondMap, Set<String> keySet) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("{\n").append("\t\"jsonDiff\": [\n");
//
//        for (String keyElement : keySet) {
//            StringBuilder field = new StringBuilder();
//            field.append("\t\t{\n").append("\t\t\t\"field\": \"").append(keyElement).append("\",\n");
//
//            StringBuilder oldValue = new StringBuilder();
//            oldValue.append("\t\t\t\"oldValue\": ").append(getValueInJsonFormat(firstMap.get(keyElement))).
//                    append(",\n");
//
//            StringBuilder newValue = new StringBuilder();
//            newValue.append("\t\t\t\"newValue\": ").append(getValueInJsonFormat(secondMap.get(keyElement))).
//                    append(",\n");
//
//            String status = "\t\t\t\"status\": \"%s\"\n\t\t},\n";
//
//            if (!firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)) {
//                sb.append(field).append(newValue).append(status.formatted("added"));
//                continue;
//            }
//            if (firstMap.containsKey(keyElement) && !secondMap.containsKey(keyElement)) {
//                sb.append(field).append(oldValue).append(status.formatted("removed"));
//                continue;
//            }
//            if (Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
//                sb.append(field).append(oldValue).append(newValue).append(status.formatted("unaffected"));
//                continue;
//            }
//            if (firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)
//                    && !Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
//                sb.append(field).append(oldValue).append(newValue).append(status.formatted("updated"));
//            }
//        }
//        sb.setLength(sb.length() - 2);
//        sb.append("\n\t]\n}");
//        return sb.toString();
//    }
//
//    private static String getValueInJsonFormat(Object inputValue) {
//        if (inputValue == null) {
//            return null;
//        }
//        String clazz = inputValue.getClass().getSimpleName();
//        switch (clazz) {
//            case "Boolean":
//            case "Integer":
//                return inputValue.toString();
//            case "ArrayList":
//                return getObjectArrayListInJsonFormat(inputValue);
//            case "LinkedHashMap":
//                return getObjectLinkedHashMapInJsonFormat(inputValue);
//            default:
//                return "\"" + inputValue.toString() + "\"";
//        }
//    }
//
//    private static String getObjectArrayListInJsonFormat(Object inputValue) {
//        List<Object> list = (List<Object>) inputValue;
//        StringBuilder arrListSb = new StringBuilder();
//        arrListSb.append("[");
//        for (Object o : list) {
//            arrListSb.append(getValueInJsonFormat(o)).append(", ");
//        }
//        arrListSb.setLength(arrListSb.length() - 2);
//        arrListSb.append("]");
//        return arrListSb.toString();
//    }
//
//    private static String getObjectLinkedHashMapInJsonFormat(Object inputValue) {
//        Map<Object, Object> map = (LinkedHashMap<Object, Object>) inputValue;
//        StringBuilder lHMSb = new StringBuilder();
//        lHMSb.append("{\n");
//        for (Map.Entry<Object, Object> e : map.entrySet()) {
//            lHMSb.append("\t\t\t\t").append(getValueInJsonFormat(e.getKey())).append(": ").
//                    append(getValueInJsonFormat(e.getValue())).append(",\n");
//        }
//        lHMSb.setLength(lHMSb.length() - 2);
//        lHMSb.append("\n\t\t\t}");
//        return lHMSb.toString();
//    }
//}