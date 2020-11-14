import java.util.ArrayList;

/**
 * Contains methods for checking if a user has permission to send messages and the methods for sending messages.
 *
 * @author Elliot, Chrisee
 * @version %I%, %G%
 */
public class ChatController {

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
     * @param username          Username of sender
     * @param recipient         Username of recipient
     * @param reg               Registrar
     * @return                  True if sender has permission to message recipient
     */
    public boolean canMessage(String username, String recipient, Registrar reg) {
        return ((reg.isFriend(username, recipient) || reg.isOrganizer(username)) && reg.userExisting(recipient));
    }

    /**
     * Checks if sender can send messages to all Attendees of an event.
     *
     * @param username          Username of the sender
     * @param evt               Event id
     * @param em                EventManager
     * @return                  True if sender has permission to message, false otherwise
     */
    public boolean canMessage(String username, Long evt, Registrar reg, EventManager em) {
        return em.hasEvent(evt) ||
                (em.hasEvent(evt) && reg.isSpeaker(username) &&
                        em.getEventById(evt).getSpeaker().equals(username));
    }

    /**
     * Checks if sender can send a message to all Speakers.
     *
     * @param reg               Registrar
     * @param username          Username of sender
     * @param speakers          List of usernames of speakers that the sender wants to send to
     * @return                  True if sender has permission to message all speakers
     */
    public boolean canSendSpeakers(Registrar reg, String username, ArrayList<String> speakers) {
        boolean result = true;
        for (String speaker : speakers) {
            if (!canMessage(username, speaker, reg)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Checks if sender can send to all participants of all events in a list.
     *
     * @param reg               Registrar
     * @param em                EventManager
     * @param username          Username of sender
     * @param event_ids         List of Event id
     * @return                  True if sender has permission to message all participants of all events in the list
     */
    public boolean canSendEvents(Registrar reg, EventManager em, String username, ArrayList<Long> event_ids) {
        boolean result = true;
        for (Long event_id : event_ids) {
            if (!canMessage(username, event_id, reg, em)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Sends message to a recipient.
     *
     * @param username          Username of sender
     * @param recipient         Username of recipient
     * @param message           Message to be sent
     */
    public void sendMessage(String username, String recipient, String message) {
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
     * @param username      username of the sender
     * @param evt           ID of an event
     * @param message       String of message to be sent
     * @param em            EventManager
     */
    public void sendMessage(String username, Long evt, String message, EventManager em) {
        ChatPull pull = new ChatPull();
        ChatroomManager cm = pull.readChatlog();
        Message msg = new Message(message, username);
        Event event = em.getEventById(evt);
        cm.sendAll(event, msg);
        ChatPush push = new ChatPush();
        push.storeChat(cm);
    }
}
