package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.concurrent.Callable;

// Differ
@Command(name = "gendiff", version = "gendiff 0.1", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable {

    @Option(names = { "-f", "--format" }, defaultValue = "stylish",
            description = "output format [default: ${DEFAULT-VALUE}]", paramLabel = "format")
    private String outputFormat;

    @Parameters(index = "0", description = "path to first file", paramLabel = "filepath1")
    private String pathToFirstString;
    @Parameters(index = "1", description = "path to second file", paramLabel = "filepath2")
    private String pathToSecondString;

    /**
     * call.
     */
    @Override
    public Object call() {
        try {
            String diffString = Differ.generate(pathToFirstString, pathToSecondString, outputFormat);
            System.out.println(diffString);
        } catch (IOException d) {
            System.out.println(d.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
