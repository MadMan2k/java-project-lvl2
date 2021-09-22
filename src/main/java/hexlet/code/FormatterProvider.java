package hexlet.code;

import hexlet.code.formatters.FormatterFactory;
import hexlet.code.formatters.JsonFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

public class FormatterProvider {
    public static FormatterFactory createFormatterByFormat(String format) throws DifferExceptions {
        if (format.equalsIgnoreCase("stylish")) {
            return new StylishFormatter();
        } else if (format.equalsIgnoreCase("plain")) {
            return new PlainFormatter();
        }   else if (format.equalsIgnoreCase("json")) {
            return new JsonFormatter();
        } else {
            throw new DifferExceptions(format + " not supported. Only stylish/plain/json output allowed");
        }
    }
}
