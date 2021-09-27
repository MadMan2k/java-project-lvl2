package hexlet.code.formatters;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class JsonFormatter implements Formatter {
    private static final String OLD_VALUE = "oldValue";
    private static final String NEW_VALUE = "newValue";
    private static final String STATUS = "status";
    private static final String NEW_LINE = "\n";
    /**
     * Make JSON output.
     */
    @Override
    public String format(Map<String, Object> firstMap, Map<String, Object> secondMap,
                         Set<String> keySet) throws JsonProcessingException {
        int counter = -1;
        Map<String, Object[]> mapOfFinal = new LinkedHashMap<>();
        Object[] maps = new Object[keySet.size()];
        String finalMapStringValue = "jsonDiff";

        for (String keyElement : keySet) {
            counter++;
            Map<String, Object> tempMap = new LinkedHashMap<>();
            tempMap.put("field", keyElement);

            if (!firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)) {
                tempMap.put(NEW_VALUE, secondMap.get(keyElement));
                tempMap.put(STATUS, "added");
                maps[counter] = tempMap;
                continue;
            }
            if (firstMap.containsKey(keyElement) && !secondMap.containsKey(keyElement)) {
                tempMap.put(OLD_VALUE, firstMap.get(keyElement));
                tempMap.put(STATUS, "removed");
                maps[counter] = tempMap;
                continue;
            }
            if (Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
                tempMap.put(OLD_VALUE, firstMap.get(keyElement));
                tempMap.put(NEW_VALUE, secondMap.get(keyElement));
                tempMap.put(STATUS, "unaffected");
                maps[counter] = tempMap;
                continue;
            }
            if (firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)
                    && !Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
                tempMap.put(OLD_VALUE, firstMap.get(keyElement));
                tempMap.put(NEW_VALUE, secondMap.get(keyElement));
                tempMap.put(STATUS, "updated");
                maps[counter] = tempMap;
            }
        }
        mapOfFinal.put(finalMapStringValue, maps);
        ObjectMapper ob = new ObjectMapper();
        return ob.writeValueAsString(mapOfFinal);
    }
}
