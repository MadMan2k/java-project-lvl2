package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Parser {
    /**
     * Parser class for JSON and YAML parsers.
     */
    public Map<String, Object> parse(String inputPath) throws IOException, DifferExceptions {
        ObjectMapper ob = getParser(inputPath);
        return ob.readValue(new File(inputPath),
                new TypeReference<Map<String, Object>>() { });
    }

    private ObjectMapper getParser(String inputPath) throws DifferExceptions {
        String format = Differ.getFileNameFromStringPath(inputPath).split("\\.", 2)[1];
        if (format.equalsIgnoreCase("json")) {
            return new ObjectMapper();
        } else if (format.equalsIgnoreCase("yml") || format.equalsIgnoreCase("yaml")) {
            return new ObjectMapper(new YAMLFactory());
        } else {
            throw new DifferExceptions("." + format + " not supported. Only json/yml input allowed");
        }
    }
}
