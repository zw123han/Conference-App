/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class EventCreationFailureException extends Exception{

    /**
     * (please describe)
     */
    public EventCreationFailureException() {
        super("This event couldn't be created");
    }

    /**
     * (please describe)
     * @param message       (please describe)
     */
    public EventCreationFailureException(String message) {
        super(message);
    }
}
