package hexlet.code;

import hexlet.code.formatters.Formatter;
import hexlet.code.formatters.JsonFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

public class FormatterProvider {
    public static Formatter createFormatterByFormat(String format) throws DifferExceptions {
        return switch (format) {
            case "stylish" -> new StylishFormatter();
            case "plain" -> new PlainFormatter();
            case "json" -> new JsonFormatter();
            default -> throw new DifferExceptions(format + " not supported. Only stylish/plain/json output allowed");
        };
    }
}
