import java.util.ArrayList;

/**
 * Contains methods for checking if a user has permission to send messages and the methods for sending messages.
 *
 * @author Elliot, Chrisee
 */
public class ChatController {
    private String username;
    private Registrar reg;
    private EventManager em;

    /**
     * Initializes a new ChatController.
     *
     * @param username          Username of the sender
     * @param reg               Registrar
     * @param em                EventManager
     */
    public ChatController(String username, Registrar reg, EventManager em) {
        this.username = username;
        this.reg = reg;
        this.em = em;
    }

    /**
     * Checks if message is valid (Non-empty).
     *
     * @param message       Message
     * @return boolean      True if length is not 0, false otherwise
     */
    public boolean validateMessage(String message) {
        return message.length() != 0;
    }

    /**
     * Checks if sender can send messages to a specific recipient.
     *
     * @param recipient         Username of recipient
     * @return                  True if sender has permission to message recipient
     */
    public boolean canMessage(String recipient) {
        return ((reg.isFriend(username, recipient) || reg.isOrganizer(username)) && reg.userExisting(recipient));
    }

    /**
     * Checks if sender can send messages to all Attendees of an event.
     *
     * @param evt               Event id
     * @return                  True if sender has permission to message, false otherwise
     */
    public boolean canMessage(Long evt) {
        return em.hasEvent(evt) ||
                (em.hasEvent(evt) && reg.isSpeaker(username) &&
                        em.getEvent(evt).getSpeaker().equals(username));
    }

    /**
     * Checks if sender can send a message to all Speakers.
     *
     * @param speakers          List of usernames of speakers that the sender wants to send to
     * @return                  True if sender has permission to message all speakers
     */
    public boolean canSendSpeakers(ArrayList<String> speakers) {
        boolean result = true;
        for (String speaker : speakers) {
            if (!canMessage(speaker)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Checks if sender can send to all participants of all events in a list.
     *
     * @param event_ids         List of Event id
     * @return                  True if sender has permission to message all participants of all events in the list
     */
    public boolean canSendEvents(ArrayList<Long> event_ids) {
        boolean result = true;
        for (Long event_id : event_ids) {
            if (!canMessage(event_id)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Sends message to a recipient.
     *
     * @param recipient         Username of recipient
     * @param message           Message to be sent
     */
    public void sendMessage(String recipient, String message) {
        ChatPull pull = new ChatPull();
        ChatroomManager cm = pull.readChatlog();
        Message msg = new Message(message, username);
        ArrayList<String> recipients = new ArrayList<>();
        recipients.add(username);
        recipients.add(recipient);
        cm.sendOne(recipients, msg);
        ChatPush push = new ChatPush();
        push.storeChat(cm);
    }

    /**
     * Sends message to all attendees in an event.
     *
     * @param evt           ID of an event
     * @param message       String of message to be sent
     * @return true if messages are sent
     */
    public boolean sendMessage(Long evt, String message) {
        ChatPull pull = new ChatPull();
        ChatroomManager cm = pull.readChatlog();
        Message msg = new Message(message, username);
        try {
            ArrayList<String> recipients = em.getSignedUpUsers(evt);
            cm.sendAll(recipients, msg);
            ChatPush push = new ChatPush();
            push.storeChat(cm);
            return true;
        } catch (EventNotFoundException e) {
            return false;
        }
    }
}
