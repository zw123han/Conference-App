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

    public boolean canSendOne(User user, String recipient, Message message, EventManager em) {
        if ((user instanceof Attendee && user.getFriends().contains(recipient)) ||
                (user instanceof Organizer &&
                        (user.getFriends().contains(recipient) || isInEvent(user, recipient, em)))){
            ArrayList<String> recipients = new ArrayList<>();
            recipients.add(user.getUserName());
            recipients.add(recipient);
            chatter.sendOne(recipients, message);
            return true;
        }
        return false;
    }

    public boolean canSendAll(User user, Long evt, Message message, EventManager em) {
        if ((user instanceof Organizer || user instanceof Speaker) && user.getEvents().contains(evt)) {
            Event event = em.getEventById(evt);
            chatter.sendAll(event, message);
            return true;
        }
        return false;
    }

}
