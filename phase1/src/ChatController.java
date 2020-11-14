import java.util.ArrayList;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class ChatController {

    /**
     * (please describe)
     *
     * @param message       (please describe)
     * @return              (please describe)
     */
    public boolean validateMessage(String message) {
        return message.length() != 0;
    }

    /**
     * (please describe)
     *
     * @param username          (please describe)
     * @param recipient     (please describe)
     * @param reg           (please describe)
     * @return              True or false.
     */
    public boolean canMessage(String username, String recipient, Registrar reg) {
        return ((reg.isFriend(username, recipient) || reg.isOrganizer(username)) && reg.userExisting(recipient));
    }

    /**
     * (please describe)
     *
     * @param username          (please describe)
     * @param evt           (please describe)
     * @param em            (please describe)
     * @return              True of false.
     */
    public boolean canMessage(String username, Long evt, Registrar reg, EventManager em) {
        return em.hasEvent(evt) ||
                (em.hasEvent(evt) && reg.isSpeaker(username) &&
                        em.getEventById(evt).getSpeaker().equals(username));
    }

    /**
     * (please describe)
     *
     * @param username          (please describe)
     * @param recipient     (please describe)
     * @param cm            (please describe)
     * @return              True of false.
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
     * (please describe)
     *
     * @param username          (please describe)
     * @param recipient     (please describe)
     * @param cm            (please describe)
     * @return              True of false.
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
     * (please describe)
     *
     * @param username          (please describe)
     * @param recipient     (please describe)
     * @param message       (please describe)
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
     * (please describe)
     *
     * @param username          (please describe)
     * @param evt           (please describe)
     * @param message       (please describe)
     * @param em            (please describe)
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
