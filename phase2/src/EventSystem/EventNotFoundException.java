package EventSystem;

/**
 * Exception that is thrown when an event cannot be found
 *
 * @author Andy, Nithilan
 */
public class EventNotFoundException extends Exception {
    /**
     * Creates an instance of EventNotFoundException with a custom message
     *
     * @param message       message to be displayed for this exception
     */
    public EventNotFoundException(String message){ //is this ever used?
        super(message);
    }

    /**
     * Creates an instance of EventNotFoundException with a default message
     */
    public EventNotFoundException(){
        super("This event has not yet been registered.");
    }
}
