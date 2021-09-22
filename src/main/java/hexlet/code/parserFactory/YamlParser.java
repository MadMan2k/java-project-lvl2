//package hexlet.code.parserFactory;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//
//public class YamlParser implements ParserFactory {
//    /**
//     * Yaml parser implementation.
//     */
//    @Override
//    public Map<String, Object> parse(String inputPath) throws IOException {
//        ObjectMapper ob = new ObjectMapper(new YAMLFactory());
//        return ob.readValue(new File(inputPath),
//                new TypeReference<Map<String, Object>>() { });
//    }
//}
