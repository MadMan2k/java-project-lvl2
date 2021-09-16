package hexlet.code;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.nio.file.Path;

class DifferTest {
    private final Path pathTestFile1 = Path.of("src/test/resources/TestFile1.json");
    private final Path pathTestFile2 = Path.of("src/test/resources/TestFile2.yml");
    private final Path pathTestComplexFile3 = Path.of("src/test/resources/TestComplexFile3.json");
    private final Path pathTestComplexFile4 = Path.of("src/test/resources/TestComplexFile4.json");
    private final Path pathTestFileIsEmpty = Path.of("src/test/resources/TestFileEmpty.json");
    private final Path pathNonValid = Path.of("non-valid-filepath");
    private final Path pathTestFormatNotSupported = Path.of("src/test/resources/TestFormatNotSupported.txt");

    @Test
    void testDifferStylish() throws DifferExceptions, IOException {
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
        assertThat(Differ.generate(pathTestFile1, pathTestFile2, "stylish")).isEqualTo(expected);
    }

    @Test
    void testDifferPlain() throws DifferExceptions, IOException {
        String expected = """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'""";
        assertThat(Differ.generate(pathTestComplexFile3, pathTestComplexFile4, "plain")).isEqualTo(expected);
    }

    @Test
    void testDifferJson() throws DifferExceptions, IOException {
        String expected = """
                {
                \t"jsonDiff": [
                \t\t{
                \t\t\t"field": "chars1",
                \t\t\t"oldValue": ["a", "b", "c"],
                \t\t\t"newValue": ["a", "b", "c"],
                \t\t\t"status": "unaffected"
                \t\t},
                \t\t{
                \t\t\t"field": "chars2",
                \t\t\t"oldValue": ["d", "e", "f"],
                \t\t\t"newValue": false,
                \t\t\t"status": "updated"
                \t\t},
                \t\t{
                \t\t\t"field": "checked",
                \t\t\t"oldValue": false,
                \t\t\t"newValue": true,
                \t\t\t"status": "updated"
                \t\t},
                \t\t{
                \t\t\t"field": "default",
                \t\t\t"oldValue": null,
                \t\t\t"newValue": ["value1", "value2"],
                \t\t\t"status": "updated"
                \t\t},
                \t\t{
                \t\t\t"field": "id",
                \t\t\t"oldValue": 45,
                \t\t\t"newValue": null,
                \t\t\t"status": "updated"
                \t\t},
                \t\t{
                \t\t\t"field": "key1",
                \t\t\t"oldValue": "value1",
                \t\t\t"status": "removed"
                \t\t},
                \t\t{
                \t\t\t"field": "key2",
                \t\t\t"newValue": "value2",
                \t\t\t"status": "added"
                \t\t},
                \t\t{
                \t\t\t"field": "numbers1",
                \t\t\t"oldValue": [1, 2, 3, 4],
                \t\t\t"newValue": [1, 2, 3, 4],
                \t\t\t"status": "unaffected"
                \t\t},
                \t\t{
                \t\t\t"field": "numbers2",
                \t\t\t"oldValue": [2, 3, 4, 5],
                \t\t\t"newValue": [22, 33, 44, 55],
                \t\t\t"status": "updated"
                \t\t},
                \t\t{
                \t\t\t"field": "numbers3",
                \t\t\t"oldValue": [3, 4, 5],
                \t\t\t"status": "removed"
                \t\t},
                \t\t{
                \t\t\t"field": "numbers4",
                \t\t\t"newValue": [4, 5, 6],
                \t\t\t"status": "added"
                \t\t},
                \t\t{
                \t\t\t"field": "obj1",
                \t\t\t"newValue": {
                \t\t\t\t"nestedKey": "value",
                \t\t\t\t"isNested": true
                \t\t\t},
                \t\t\t"status": "added"
                \t\t},
                \t\t{
                \t\t\t"field": "setting1",
                \t\t\t"oldValue": "Some value",
                \t\t\t"newValue": "Another value",
                \t\t\t"status": "updated"
                \t\t},
                \t\t{
                \t\t\t"field": "setting2",
                \t\t\t"oldValue": 200,
                \t\t\t"newValue": 300,
                \t\t\t"status": "updated"
                \t\t},
                \t\t{
                \t\t\t"field": "setting3",
                \t\t\t"oldValue": true,
                \t\t\t"newValue": "none",
                \t\t\t"status": "updated"
                \t\t}
                \t]
                }""";
        assertThat(Differ.generate(pathTestComplexFile3, pathTestComplexFile4, "json")).isEqualTo(expected);
    }

    @Test
    void testEmptyFileException() {
        assertThatThrownBy(() -> {
            Differ.generate(pathTestFile1, pathTestFileIsEmpty, "");
        }).isInstanceOf(DifferExceptions.class);
    }

    @Test
    void testBadFilepathException() {
        assertThatThrownBy(() -> {
            Differ.generate(pathTestFile1, pathNonValid, "");
        }).isInstanceOf(DifferExceptions.class);
    }

    @Test
    void testFileNotSupportedException() {
        assertThatThrownBy(() -> {
            Differ.generate(pathTestFile1, pathTestFormatNotSupported, "");
        }).isInstanceOf(DifferExceptions.class);
    }

    @Test
    void testFormatNotSupportedException() {
        assertThatThrownBy(() -> {
            Differ.generate(pathTestFile1, pathTestFile2, "notSupported");
        }).isInstanceOf(DifferExceptions.class);
    }
}
