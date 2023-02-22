package exceptions;

public class UserAlreadyExistsExcpetion extends Exception{
	
	public UserAlreadyExistsExcpetion() {
		super("User already exists!");
	}
	
	public UserAlreadyExistsExcpetion(String message) {
		super(message);
	}

	public UserAlreadyExistsExcpetion(Throwable cause) {
		super(cause);
	}

	public UserAlreadyExistsExcpetion(String message,Throwable cause) {
		super(message,cause);
	}
}
