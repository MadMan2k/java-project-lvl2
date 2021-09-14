package hexlet.code;

public class DifferExceptions extends Exception{
    private String message;

    public DifferExceptions(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
