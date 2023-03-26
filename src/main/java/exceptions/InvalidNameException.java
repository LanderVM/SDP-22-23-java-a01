package exceptions;

public class InvalidNameException extends Exception {
    public InvalidNameException() {
        super("There is no user with the given name");
    }

    public InvalidNameException(String message) {
        super(message);
    }

    public InvalidNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNameException(Throwable cause) {
        super(cause);
    }
}
