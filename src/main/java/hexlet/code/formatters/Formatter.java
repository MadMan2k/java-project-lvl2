package hexlet.code.formatters;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public interface Formatter {
    String format(Map<String, Object> firstMap, Map<String, Object> secondMap,
                  Set<String> keySet);
}
