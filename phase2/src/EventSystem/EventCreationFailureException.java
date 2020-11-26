package EventSystem;

/**
 * Exception class that is thrown when event cannot be created
 *
 * @author Andy, Nithilan
 */
public class EventCreationFailureException extends Exception{

    /**
     * creates an instance of EventCreationFailureException with a default message.
     */
    public EventCreationFailureException() {
        super("This event couldn't be created");
    }

    /**
     * creates an instance of EventCreationFailureException with a custom message.
     * @param message       message to be thrown.
     */
    public EventCreationFailureException(String message) {
        super(message);
    }
}
