package hexlet.code.formatters;

import java.util.LinkedHashSet;
import java.util.Map;

public interface Formatter {
    String format(Map<String, Object> firstMap, Map<String, Object> secondMap,
                  LinkedHashSet<String> keySet);
}
