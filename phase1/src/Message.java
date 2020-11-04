import java.time.*;
// Update the implementation of dates to convert to local timezone

public class Message{
    private String message, sender = null;
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

    public LocalDateTime getDate() {
        return time.toLocalDateTime();
    }
}