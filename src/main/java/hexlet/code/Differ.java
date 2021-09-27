package hexlet.code;

import hexlet.code.formatters.Formatter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Differ {

    public static void main(String[] args) {
        try {
            String outputFormat = "json";
            String str = generate("src/test/resources/TestComplexFile3.json",
                "src/test/resources/TestComplexFile4.json", outputFormat);
            System.out.println(str);
        } catch (DifferExceptions | IOException d) {
            System.out.println(d.getMessage());
        }
    }

    public static String generate(String firstStringPath, String secondStringPath, String outputFormat)
            throws DifferExceptions, IOException {
        Path firstPath = Path.of(firstStringPath);
        Path secondPath = Path.of(secondStringPath);
        checkFilepathAndFileIsNotEmptyExceptions(firstPath, secondPath);

        Map<String, Object> parsedMap1 = getParsedMap(firstPath);
        Map<String, Object> parsedMap2 = getParsedMap(secondPath);

        LinkedHashSet<String> keySet = Stream.concat(parsedMap1.keySet().stream(), parsedMap2.keySet().stream())
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
        Formatter formatter = FormatterProvider.createFormatterByFormat(outputFormat);

        return formatter.format(parsedMap1, parsedMap2, keySet);
    }

    public static String generate(String firstStringPath, String secondStringPath)
            throws IOException, DifferExceptions {
        return generate(firstStringPath, secondStringPath, "stylish");
    }

    private static String getAbsolutePathString(Path path) {
        if (!path.isAbsolute()) {
            path = path.toAbsolutePath();
        }
        return path.toString();
    }

    private static void checkFilepathAndFileIsNotEmptyExceptions(Path path1, Path path2) throws DifferExceptions {
        Path[] pathsArray = new Path[2];
        pathsArray[0] = path1;
        pathsArray[1] = path2;

        for (Path p : pathsArray) {
            if (!Files.exists(p)) {
                throw new DifferExceptions("Bad filepath " + p);
            }
            if (new File(String.valueOf(p)).length() == 0) {
                throw new DifferExceptions(getFileNameFromStringPath(String.valueOf(p)) + " is empty");
            }
        }
    }

    public static String getFileNameFromStringPath(String stringPath) {
        String[] pathParts = stringPath.split("/");
        return pathParts[pathParts.length - 1];
    }

    private static Map<String, Object> getParsedMap(Path path) throws DifferExceptions, IOException {
        String stringInputPath = getAbsolutePathString(path);
        Parser parser = new Parser();
        return parser.parse(getContentFromFileByPath(path), getFileNameFromStringPath(stringInputPath).
                split("\\.", 2)[1]);
    }

    private static String getContentFromFileByPath(Path path) throws IOException {
        return Files.readString(path);
    }
}
