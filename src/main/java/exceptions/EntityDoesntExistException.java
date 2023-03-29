package exceptions;

public class EntityDoesntExistException extends Exception {
    public EntityDoesntExistException() {
        super("Entity could not be found!");
    }

    public EntityDoesntExistException(String message) {
        super(message);
    }
}
