package hexlet.code;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.nio.file.Path;

class DifferTest {
    private final Path pathTestFile1 = Path.of("src/test/resources/TestFile1.json");
    private final Path pathTestFile2 = Path.of("src/test/resources/TestFile2.json");
    private final Path pathTestFileIsEmpty = Path.of("src/test/resources/TestFile3.json");
    private final Path pathNonValid = Path.of("non-valid-filepath");

    @Test
    void testDiffer1() throws DifferExceptions, IOException {
        String expected = "{\n"
                + "  - follow: false\n"
                + "    host: hexlet.io\n"
                + "  - proxy: 123.234.53.22\n"
                + "  - timeout: 50\n"
                + "  + timeout: 20\n"
                + "  + verbose: true\n"
                + "}";
        assertThat(Differ.generate(pathTestFile1, pathTestFile2)).isEqualTo(expected);
    }

    @Test
    void testEmptyFileException() {
        assertThatThrownBy(() -> {
            Differ.generate(pathTestFile1, pathTestFileIsEmpty);
        }).isInstanceOf(DifferExceptions.class);
    }

    @Test
    void testBadFilepathException() {
        assertThatThrownBy(() -> {
            Differ.generate(pathTestFile2, pathNonValid);
        }).isInstanceOf(DifferExceptions.class);
    }
}
