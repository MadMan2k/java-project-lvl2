package hexlet.code.parserFactory;

import java.io.IOException;
import java.util.Map;

public interface Parser {
    Map<String, String> parse(String inputPath) throws IOException;
}
