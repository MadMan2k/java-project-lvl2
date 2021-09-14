package hexlet.code;

public class DifferExceptions extends Exception {
    private String message;

    public DifferExceptions(String inputMessage) {
        this.message = inputMessage;
    }

    /**
     * return message.
     */
    @Override
    public String getMessage() {
        return message;
    }
}
