package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Differ extends App {

//    public static void main(String[] args) throws DifferExceptions, IOException {
//        try {
//            String str = generate(Path.of("src/test/java/hexlet/code/TestFile1.json"),
//                    Path.of("src/test/java/hexlet/code/TestFile2.json"));
//            System.out.println(str);
//        } catch (DifferExceptions | IOException d) {
//            System.out.println(d.getMessage());
//        }
//    }

    public static String generate(Path firstPath, Path secondPath) throws DifferExceptions, IOException {
        checkExceptions(firstPath, secondPath);
        String stringFirstInputPath = getAbsolutePathString(firstPath);
        String stringSecondInputPath = getAbsolutePathString(secondPath);
        ObjectMapper ob = new ObjectMapper();
        Map<String, String> jsonMap1 = ob.readValue(new File(stringFirstInputPath),
                new TypeReference<Map<String, String>>() { });
        Map<String, String> jsonMap2 = ob.readValue(new File(stringSecondInputPath),
                new TypeReference<Map<String, String>>() { });
        return calculateDifference(jsonMap1, jsonMap2);
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
}
