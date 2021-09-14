package hexlet.code;

import hexlet.code.parserFactory.JsonParser;
import hexlet.code.parserFactory.Parser;
import hexlet.code.parserFactory.YamlParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Differ extends App {

//    public static void main(String[] args) {
//        try {
//            String str = generate(Path.of("src/test/resources/TestFile1.json"),
//                    Path.of("src/test/resources/TestFile5.yml"));
//            System.out.println(str);
//        } catch (DifferExceptions | IOException d) {
//            System.out.println(d.getMessage());
//        }
//    }

    public static String generate(Path firstPath, Path secondPath) throws DifferExceptions, IOException {
        checkExceptions(firstPath, secondPath);

        Map<String, String> parsedMap1 = getParsedMap(firstPath);
        Map<String, String> parsedMap2 = getParsedMap(secondPath);

        return calculateDifference(parsedMap1, parsedMap2);
    }

    private static String calculateDifference(Map<String, String> firstMap, Map<String, String> secondMap) {

        LinkedHashSet<String> keySet = Stream.concat(firstMap.keySet().stream(), secondMap.keySet().stream())
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        for (String keyElement : keySet) {
            if (!firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)) {
                sb.append("  + ").append(keyElement).append(": ").append(secondMap.get(keyElement)).append("\n");
                continue;
            }
            if (firstMap.containsKey(keyElement) && !secondMap.containsKey(keyElement)) {
                sb.append("  - ").append(keyElement).append(": ").append(firstMap.get(keyElement)).append("\n");
                continue;
            }
            if (firstMap.get(keyElement).equals(secondMap.get(keyElement))) {
                sb.append("    ").append(keyElement).append(": ").append(firstMap.get(keyElement)).append("\n");
                continue;
            }
            if (firstMap.containsKey(keyElement) && secondMap.containsKey(keyElement)
                    && !firstMap.get(keyElement).equals(secondMap.get(keyElement))) {
                sb.append("  - ").append(keyElement).append(": ").append(firstMap.get(keyElement)).append("\n");
                sb.append("  + ").append(keyElement).append(": ").append(secondMap.get(keyElement)).append("\n");
            }
        }

        sb.append("}");
        return sb.toString();
    }

    private static String getAbsolutePathString(Path path) {
        if (!path.isAbsolute()) {
            path = path.toAbsolutePath();
        }
        return path.toString();
    }

    public static void checkExceptions(Path path1, Path path2) throws DifferExceptions {
        Path[] pathsArray = new Path[2];
        pathsArray[0] = path1;
        pathsArray[1] = path2;

        for (Path p : pathsArray) {
            if (!Files.exists(p)) {
                throw new DifferExceptions("Bad filepath " + p);
            }
            if (new File(String.valueOf(p)).length() == 0) {
                throw new DifferExceptions(getFileNameFromPath(p) + " is empty");
            }
        }
    }

    private static String getFileNameFromPath(Path path) {
        String[] pathParts = String.valueOf(path).split("/");
        return pathParts[pathParts.length - 1];
    }

    private static Parser createParserByFormat(String format) throws DifferExceptions {
        if (format.equalsIgnoreCase("json")) {
            return new JsonParser();
        } else if (format.equalsIgnoreCase("yml") || format.equalsIgnoreCase("yaml")) {
            return new YamlParser();
        } else {
            throw new DifferExceptions("." + format + " not supported. Only json/yml input allowed");
        }
    }

    private static Map<String, String> getParsedMap(Path path) throws DifferExceptions, IOException {
        String stringInputPath = getAbsolutePathString(path);
        Parser parser = createParserByFormat(getFileNameFromPath(path).split("\\.", 2)[1]);
        return parser.parse(stringInputPath);
    }
}
