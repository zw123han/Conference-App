import java.util.Date;
// Update the implementation of dates to convert to local timezone

public class Message{
    private String message, sender = null;
    private Date date = new Date();

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

    public Date getDate() {
        return date;
    }

}