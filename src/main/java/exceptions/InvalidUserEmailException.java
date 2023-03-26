package exceptions;

public class InvalidUserEmailException extends Exception {
    public InvalidUserEmailException() {
        super("There is no user with the given name");
    }

    public InvalidUserEmailException(String message) {
        super(message);
    }

    public InvalidUserEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserEmailException(Throwable cause) {
        super(cause);
    }
}
