package exceptions;

public class UserDoesntExistException extends Exception{
	
	public UserDoesntExistException() {
		super("There doesn't exist a user with given accountname");
	}
	
	public UserDoesntExistException(String message) {
		super(message);
	}
	
	public UserDoesntExistException(String message,Throwable cause) {
		super(message,cause);
	}
	
	public UserDoesntExistException(Throwable cause) {
		super(cause);
	}
}
