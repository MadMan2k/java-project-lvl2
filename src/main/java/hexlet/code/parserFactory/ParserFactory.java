package hexlet.code.parserFactory;

import java.io.IOException;
import java.util.Map;

public interface ParserFactory {
    Map<String, Object> parse(String inputPath) throws IOException;
}
