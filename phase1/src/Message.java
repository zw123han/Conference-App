import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class Message implements Serializable {
    private String message, sender;
    private ZonedDateTime time = ZonedDateTime.now();

    /**
     * (please describe)
     *
     * @param message       (please describe)
     * @param sender        (please describe)
     */
    public Message(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String getMessage() {
        return message;
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String getSender() {
        return sender;
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String getDate() {
        DateTimeFormatter d = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return time.toLocalDateTime().format(d);
    }
}