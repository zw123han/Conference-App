import java.util.*;

public class OutboxPresenter {

    public void exitMessage() {
        System.out.println("$q to exit.");
    }

    public void menuDisplay() {
        System.out.println("1 Direct message\n2 Group message");
        exitMessage();
    }

    public void friendMenu(User user) {
        System.out.println("FRIENDS:");
        ArrayList<String> friends = user.getFriends();
        int n = 1;
        for (String friend : friends) {
            System.out.println(n + ". " + friend);
            n += 1;
        }
    }

    public void eventMenu(Speaker user) {
        System.out.println("EVENTS:");
        ArrayList<Long> events = user.getTalks();
        int n = 1;
        for (Long event : events) {
            System.out.println(n + ". " + event);
            n += 1;
        }
    }

    public void eventMenu(EventManager em) {
        System.out.println("EVENTS:");
        ArrayList<Event> events = em.getEventsList();
        int n = 1;
        for (Event event : events) {
            System.out.println(n + ". " + event.getId());
            n += 1;
        }
    }

    public void invalidCommand(String field) {
        System.out.println(field + " not valid.");
    }

    public void commandPrompt(String field) {
        System.out.println("Enter a valid " + field + ".\n$back to exit");
        exitMessage();
    }

    public void success() {
        System.out.println("Message successfully sent!");
    }
}
