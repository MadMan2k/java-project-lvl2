package hexlet.code;

import hexlet.code.formatters.Formatter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Differ {
    public static void main(String[] args) throws IOException {
        String outputFormat = "stylish";
        String str = generate("src/test/resources/TestComplexFile3.json",
                "src/test/resources/TestComplexFile4.json", outputFormat);
        System.out.println(str);
    }


    public static String generate(String firstStringPath, String secondStringPath, String outputFormat)
            throws IOException {
        Path firstPath = Path.of(firstStringPath);
        Path secondPath = Path.of(secondStringPath);
        checkFilepathAndFileIsNotEmptyExceptions(firstPath, secondPath);

        Map<String, Object> parsedContent1 = getParsedMap(firstPath);
        Map<String, Object> parsedContent2 = getParsedMap(secondPath);

        Set<String> keys = Stream.concat(parsedContent1.keySet().stream(), parsedContent2.keySet().stream())
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
        Formatter formatter = FormatterProvider.createFormatterByFormat(outputFormat);

        List<Map<String, Object>> differences = getDifferences(parsedContent1, parsedContent2, keys);

        return formatter.format(differences);
    }

    public static String generate(String firstStringPath, String secondStringPath)
            throws IOException {
        return generate(firstStringPath, secondStringPath, "stylish");
    }

    private static void checkFilepathAndFileIsNotEmptyExceptions(Path path1, Path path2) {
        Path[] pathsArray = new Path[2];
        pathsArray[0] = path1;
        pathsArray[1] = path2;

        for (Path p : pathsArray) {
            if (!Files.exists(p)) {
                throw new IllegalArgumentException("Bad filepath " + p);
            }
            if (new File(String.valueOf(p)).length() == 0) {
                throw new IllegalArgumentException(p + " is empty");
            }
        }
    }

    public static String getFileExtension(String stringPath) {
        String[] pathParts = stringPath.split("/");
        return pathParts[pathParts.length - 1].split("\\.", 2)[1];
    }

    private static Map<String, Object> getParsedMap(Path path) throws IOException {
        String pathAsString = path.toAbsolutePath().toString();
        Parser parser = new Parser();
        return parser.parse(Files.readString(path), getFileExtension(pathAsString));
    }

    private static List<Map<String, Object>> getDifferences(Map<String, Object> firstMap, Map<String, Object> secondMap,
                                                      Set<String> keySet) {
        String oldValue = "oldValue";
        String newValue = "newValue";
        String status = "status";

        List<Map<String, Object>> differences = new LinkedList<>();
        for (String keyElement : keySet) {
            Map<String, Object> differencesByElement = new HashMap<>();
            differencesByElement.put("field", keyElement);
            if (!firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)) {
                differencesByElement.put(newValue, secondMap.get(keyElement));
                differencesByElement.put(status, "added");
            }
            if (firstMap.containsKey(keyElement) && !secondMap.containsKey(keyElement)) {
                differencesByElement.put(oldValue, firstMap.get(keyElement));
                differencesByElement.put(status, "removed");
            }
            if (Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
                differencesByElement.put(oldValue, firstMap.get(keyElement));
                differencesByElement.put(newValue, secondMap.get(keyElement));
                differencesByElement.put(status, "unaffected");
            }
            if (firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)
                    && !Objects.equals(firstMap.get(keyElement), secondMap.get(keyElement))) {
                differencesByElement.put(oldValue, firstMap.get(keyElement));
                differencesByElement.put(newValue, secondMap.get(keyElement));
                differencesByElement.put(status, "updated");
            }
            differences.add(differencesByElement);
        }

        return differences;
    }
}
