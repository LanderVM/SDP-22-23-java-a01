package exceptions;

public class EntityDoesntExistException extends Exception{
	public EntityDoesntExistException() {
        super("There doesn't exist a user with given accountname");
    }

    public EntityDoesntExistException(String message) {
        super(message);
    }

    public EntityDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityDoesntExistException(Throwable cause) {
        super(cause);
    }
}
