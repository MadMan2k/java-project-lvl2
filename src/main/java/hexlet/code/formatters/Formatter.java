package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;
import java.util.Set;

public interface Formatter {
    String format(Map<String, Object> firstMap, Map<String, Object> secondMap,
                  Set<String> keySet) throws JsonProcessingException;
}
