package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Differ extends App {

//    public static void main(String[] args) throws Exception {
//        generate();
//    }

    public static String generate(Path firstPath, Path secondPath) throws Exception {
        String stringFirstInputPath = getAbsolutePathString(firstPath);
        String stringSecondInputPath = getAbsolutePathString(secondPath);

        ObjectMapper ob = new ObjectMapper();

        Map<String, String> jsonMap1 = ob.readValue(new File(stringFirstInputPath),
                new TypeReference<Map<String, String>>() { });
        Map<String, String> jsonMap2 = ob.readValue(new File(stringSecondInputPath),
                new TypeReference<Map<String, String>>() { });

        String result = calculateDifference(jsonMap1, jsonMap2);
        return result;
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







//    private static Map<String, Boolean> areEqualKeyValues(Map<String, String> first, Map<String, String> second) {
//        return first.entrySet().stream()
//                .collect(Collectors.toMap(e -> e.getKey(),
//                        e -> e.getValue().equals(second.get(e.getKey()))));
//    }
}
