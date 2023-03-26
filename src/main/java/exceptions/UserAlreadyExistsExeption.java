package exceptions;

public class UserAlreadyExistsExeption extends Exception {
    public UserAlreadyExistsExeption() {
        super("There already exists a user with that eaail");
    }

    public UserAlreadyExistsExeption(String message) {
        super(message);
    }

    public UserAlreadyExistsExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsExeption(Throwable cause) {
        super(cause);
    }
}
