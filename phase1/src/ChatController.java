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

    public void canMessage(User user, String recipient, EventManager em) throws UserNotFoundException {
        if (!user.hasFriend(recipient) || (user instanceof Organizer && !isInEvent(user, recipient, em))) {
            throw new UserNotFoundException("User not found.");
        }
    }

    public void canMessage(User user, Long evt, EventManager em) throws EventNotFoundException {
        if (!em.hasEvent(evt) ||
                !(em.hasEvent(evt) && user instanceof Speaker && em.getEventById(evt).getSpeaker().isUser(user))) {
            throw new EventNotFoundException("Event not found.");
        }
    }

    public void sendMessage(User user, String recipient, String message) throws EmptyMessageException {
        if (message.length() == 0) {
            throw new EmptyMessageException("Message cannot be empty.");
        }
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
    }

    public void sendMessage(User user, Long evt, String message, EventManager em) throws EmptyMessageException {
        if (message.length() == 0) {
            throw new EmptyMessageException("Message cannot be empty.");
        }
        ChatPull pull = new ChatPull();
        pull.readChatlog();
        ChatroomManager cm = pull.getChatroomManager();
        Message msg = new Message(message, user.getUserName());
        Event event = em.getEventById(evt);
        cm.sendAll(event, msg);
        ChatPush push = new ChatPush();
        push.storeChat(cm);
    }
}
