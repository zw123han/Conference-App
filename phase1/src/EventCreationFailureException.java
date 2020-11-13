/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class EventCreationFailureException extends Exception{
    public EventCreationFailureException() {
        super("This event couldn't be created");
    }
    public EventCreationFailureException(String message) {
        super(message);
    }
}
