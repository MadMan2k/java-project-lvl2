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
