package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

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
    public Object call() throws Exception {
        String diffString = Differ.generate(pathToFirst, pathToSecond);
        System.out.println(diffString);
        return null;
    }

//    @Parameters(paramLabel = "<word>", defaultValue = "Hello, picocli",
//            description = "Words to be translated into ASCII art.")
//
//    private String[] words = { "Hello,", "picocli" };

//    @Override
//    public void run() {
//
//    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
