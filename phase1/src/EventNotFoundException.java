/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class EventNotFoundException extends Exception {
    /**
     * (please describe)
     *
     * @param message       (please describe)
     */
    public EventNotFoundException(String message){
        super(message);
    }

    /**
     * (please describe)
     */
    public EventNotFoundException(){
        super("This event has not yet been registered.");
    }
}
