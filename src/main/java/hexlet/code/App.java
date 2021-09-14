package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command(name = "gendiff", version = "gendiff 0.1", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable {

    @Option(names = { "-f", "--format" }, description = "output format [default: stylish]", paramLabel = "format")
    private String outputFormat;

    @Parameters(index = "0", description = "path to first file", paramLabel = "filepath1") private Path pathToFirst;
    @Parameters(index = "1", description = "path to second file", paramLabel = "filepath2") private Path pathToSecond;

    /**
     * call.
     */
    @Override
    public Object call() {
        try {
            String diffString = Differ.generate(pathToFirst, pathToSecond);
            System.out.println(diffString);
        } catch (DifferExceptions | IOException d) {
            System.out.println(d.getMessage());
        }

        return null;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}