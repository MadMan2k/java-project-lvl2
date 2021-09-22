//package hexlet.code.parserFactory;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//
//public class JsonParser implements ParserFactory {
//    /**
//     * Json parser implementation.
//     */
//    @Override
//    public Map<String, Object> parse(String inputPath) throws IOException {
//        ObjectMapper ob = new ObjectMapper();
//        return ob.readValue(new File(inputPath),
//                new TypeReference<Map<String, Object>>() { });
//    }
//}
