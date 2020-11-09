import java.util.ArrayList;

public class ChatController {
    private Chatter chatter = new Chatter();

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
            throw new UserNotFoundException("You are not authorized to message this user");
        }
    }

    public void canMessage(User user, Long evt) throws EventNotFoundException {
        if (!user.getEvents().contains(evt)) {
            throw new EventNotFoundException("You are not authorized to message users from this event.");
        }
    }

    public void sendMessage(User user, String recipient, Message message, EventManager em)  {
        ArrayList<String> recipients = new ArrayList<>();
        recipients.add(user.getUserName());
        recipients.add(recipient);
        chatter.sendOne(recipients, message);
    }

    public void sendMessage(User user, Long evt, Message message, EventManager em) {
        Event event = em.getEventById(evt);
        chatter.sendAll(event, message);
    }
}
