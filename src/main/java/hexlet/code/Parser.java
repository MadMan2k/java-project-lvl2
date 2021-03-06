package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.Map;

public class Parser {
    /**
     *
     * @param content - String of data which should be parsed
     * @param inputFormat - native format of the input data. Only JSON/YAML allowed
     * @return result of parsing in Map
     */
    public Map<String, Object> parse(String content, String inputFormat) throws IOException {
        ObjectMapper ob = getParser(inputFormat);
        return ob.readValue(content,
                new TypeReference<Map<String, Object>>() { });
    }

    private ObjectMapper getParser(String inputFormat) {
        if (inputFormat.equalsIgnoreCase("json")) {
            return new ObjectMapper();
        } else if (inputFormat.equalsIgnoreCase("yml") || inputFormat.equalsIgnoreCase("yaml")) {
            return new ObjectMapper(new YAMLFactory());
        } else {
            throw new IllegalArgumentException("." + inputFormat + " not supported. Only json/yml input allowed");
        }
    }
}
