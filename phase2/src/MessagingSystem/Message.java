package MessagingSystem;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

/**
 * An object that represents a message sent between users
 *
 * @author Chrisee Zhu
 */
public class Message implements Serializable {
    private String message, sender;
    private ZonedDateTime time = ZonedDateTime.now();
    private boolean read = false;
    private boolean pinned = false;

    /**
     * Initializes a new Message
     *
     * @param message       String to be stored in Message
     * @param sender        username of sender
     */
    public Message(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    /**
     * Gets the message String stored in this object.
     *
     * @return      String of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the sender of this message.
     *
     * @return      username of sender
     */
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Gets the date and time this message was sent, converted to local time.
     *
     * @return      date sent (local)
     */
    public String getDate() {
        DateTimeFormatter d = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return time.toLocalDateTime().format(d);
    }

    public void setDate(ZonedDateTime time) {
        this.time = time;
    }

    /**
     * Method for checking if message is read by the recipient (not the sender)
     * @return           True if the message at index is read, false otherwise
     */
    public boolean isRead() {
        return read;
    }

    /**
     * Method for setting the message as read.
     */
    public void read() {
        read = true;
    }

    /**
     * Method for checking if message is pinned.
     *
     * @return           True if message is pinned, false otherwise
     */
    public boolean isPinned(){
        return pinned;
    }

    /**
     * Method for setting the message as pinned or unpinned.
     *
     * @param setter   True or false
     */
    public void setPinned(boolean setter){
        pinned = setter;
    }
}