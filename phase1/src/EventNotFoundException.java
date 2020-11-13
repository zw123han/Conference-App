/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class EventNotFoundException extends Exception {
    public EventNotFoundException(String message){
        super(message);
    }
    public EventNotFoundException(){
        super("This event has not yet been registered.");
    }
}
