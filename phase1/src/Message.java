import java.time.*;
import java.time.format.DateTimeFormatter;
// Update the implementation of dates to convert to local timezone

public class Message{
    private String message, sender;
    private ZonedDateTime time = ZonedDateTime.now();

    public Message(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getDate() {
        DateTimeFormatter d = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return time.toLocalDateTime().format(d);
    }
}