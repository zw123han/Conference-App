import java.util.ArrayList;

public class ChatController {

    private boolean isInEvent(User user, String recipient, EventManager em) {
        for (Long evt : user.getEvents()) {
            if (em.getEventById(evt).hasUser(recipient)) {
                return true;
            }
        }
        return false;
    }

    public boolean canMessage(User user, String recipient, EventManager em) {
        return user.hasFriend(recipient) && (!(user instanceof Organizer) || isInEvent(user, recipient, em));
    }

    public boolean canMessage(User user, Long evt, EventManager em) {
        return em.hasEvent(evt) &&
                (em.hasEvent(evt) && user instanceof Speaker && em.getEventById(evt).getSpeaker().isUser(user));
    }

    public boolean validateMessage(String message) {
        return message.length() != 0;
    }

    public boolean sendMessage(User user, String recipient, String message, EventManager em) {
        if (validateMessage(message)) {
            ChatPull pull = new ChatPull();
            pull.readChatlog();
            ChatroomManager cm = pull.getChatroomManager();
            Message msg = new Message(message, user.getUserName());
            ArrayList<String> recipients = new ArrayList<>();
            recipients.add(user.getUserName());
            recipients.add(recipient);
            cm.sendOne(recipients, msg);
            ChatPush push = new ChatPush();
            push.storeChat(cm);
            return true;
        }
        return false;
    }

    public boolean sendMessage(User user, Long evt, String message, EventManager em) {
        if (validateMessage(message)) {
            ChatPull pull = new ChatPull();
            pull.readChatlog();
            ChatroomManager cm = pull.getChatroomManager();
            Message msg = new Message(message, user.getUserName());
            Event event = em.getEventById(evt);
            cm.sendAll(event, msg);
            ChatPush push = new ChatPush();
            push.storeChat(cm);
            return true;
        }
        return false;
    }
}
