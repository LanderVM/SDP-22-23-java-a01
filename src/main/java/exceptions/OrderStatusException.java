package exceptions;

public class OrderStatusException extends Exception {

    public OrderStatusException() {
        super("There doesn't exist a user with given accountname");
    }

    public OrderStatusException(String message) {
        super(message);
    }

    public OrderStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderStatusException(Throwable cause) {
        super(cause);
    }
}
