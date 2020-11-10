public class EventExistsException extends Exception{
    public EventExistsException() {
        super("This event already exists");
    }
    public EventExistsException(String message) {
        super(message);
    }
}
