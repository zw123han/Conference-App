package EventSystem;

/**
 * Exception class that is thrown when event cannot be created
 *
 * @author Andy, Nithilan
 */
public class EventModificationFailureException extends Exception{

    /**
     * creates an instance of EventCreationFailureException with a default message.
     */
    public EventModificationFailureException() {
        super("This event couldn't be modified");
    }

    /**
     * creates an instance of EventCreationFailureException with a custom message.
     * @param message       message to be thrown.
     */
    public EventModificationFailureException(String message) {
        super(message);
    }
}
