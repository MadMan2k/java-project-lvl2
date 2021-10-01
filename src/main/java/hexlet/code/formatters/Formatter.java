package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public interface Formatter {
    String format(List<Map<String, Object>> differences) throws JsonProcessingException;
}
