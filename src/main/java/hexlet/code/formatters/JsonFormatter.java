package hexlet.code.formatters;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonFormatter implements Formatter {
    private static final String OLD_VALUE = "oldValue";
    private static final String NEW_VALUE = "newValue";
    private static final String STATUS = "status";
    /**
     * Make JSON output.
     */
    @Override
    public String format(List<Map<String, Object>> differences) throws JsonProcessingException {
        int counter = -1;
        Map<String, Object[]> jsonAsMap = new LinkedHashMap<>();
        String jsonAsMapKey = "jsonDiff";
        Object[] jsonAsMapValues = new Object[differences.size()];

        for(Map<String, Object> map : differences) {
            Map<String, Object> jsonAsMapValue = new LinkedHashMap<>();
            counter++;
            jsonAsMapValue.put("field", map.get("field"));
            switch (map.get("status").toString()) {
                case "added" -> jsonAsMapValue.put(NEW_VALUE, map.get("newValue"));
                case "removed" -> jsonAsMapValue.put(OLD_VALUE, map.get("oldValue"));
                case "unaffected", "updated" -> {
                    jsonAsMapValue.put(OLD_VALUE, map.get("oldValue"));
                    jsonAsMapValue.put(NEW_VALUE, map.get("newValue"));
                }
                default -> throw new IllegalArgumentException (map.get("status").toString()
                        + " is bad status. JsonFormatter error");
            }
            jsonAsMapValue.put(STATUS, map.get(STATUS));
            jsonAsMapValues[counter] = jsonAsMapValue;
        }

        jsonAsMap.put(jsonAsMapKey, jsonAsMapValues);
        ObjectMapper ob = new ObjectMapper();
        return ob.writeValueAsString(jsonAsMap);
    }
}
