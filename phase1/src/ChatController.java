import java.util.ArrayList;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class ChatController {

    private boolean isInEvent(User user, String recipient, EventManager em) {
        for (Long evt : user.getEvents()) {
            if (em.getEventById(evt).hasUser(recipient)) {
                return true;
            }
        }
        return false;
    }

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
     * @param user          (please describe)
     * @param recipient     (please describe)
     * @param reg           (please describe)
     * @return              True or false.
     */
    public boolean canMessage(User user, String recipient, Registrar reg) {
        return ((user.hasFriend(recipient) || user instanceof Organizer) && reg.userExisting(recipient));
    }

    /**
     * (please describe)
     *
     * @param user          (please describe)
     * @param evt           (please describe)
     * @param em            (please describe)
     * @return              True of false.
     */
    public boolean canMessage(User user, Long evt, EventManager em) {
        return em.hasEvent(evt) ||
                (em.hasEvent(evt) && user instanceof Speaker && em.getEventById(evt).getSpeaker().equals(user.getName()));
    }

    /**
     * (please describe)
     *
     * @param user          (please describe)
     * @param recipient     (please describe)
     * @param cm            (please describe)
     * @return              True of false.
     */
    public boolean canReply(User user, String recipient, ChatroomManager cm) {
        if (cm.hasChatroom(user, recipient)) {
            if (user instanceof Organizer || user instanceof Attendee) {
                return true;
            }
            ArrayList<Message> history = cm.getChatroom(user, recipient).getHistory();
            for (Message m : history) {
                if (m.getSender().equals(recipient)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * (please describe)
     *
     * @param user          (please describe)
     * @param recipient     (please describe)
     * @param message       (please describe)
     */
    public void sendMessage(User user, String recipient, String message) {
        ChatPull pull = new ChatPull();
        ChatroomManager cm = pull.readChatlog();
        Message msg = new Message(message, user.getUserName());
        ArrayList<String> recipients = new ArrayList<>();
        recipients.add(user.getUserName());
        recipients.add(recipient);
        cm.sendOne(recipients, msg);
        ChatPush push = new ChatPush();
        push.storeChat(cm);
    }

    /**
     * (please describe)
     *
     * @param user          (please describe)
     * @param evt           (please describe)
     * @param message       (please describe)
     * @param em            (please describe)
     */
    public void sendMessage(User user, Long evt, String message, EventManager em) {
        ChatPull pull = new ChatPull();
        ChatroomManager cm = pull.readChatlog();
        Message msg = new Message(message, user.getUserName());
        Event event = em.getEventById(evt);
        cm.sendAll(event, msg);
        ChatPush push = new ChatPush();
        push.storeChat(cm);
    }
}
