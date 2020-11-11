import java.util.*;

public class OutboxPresenter extends CommandPresenter{

    public void menuDisplay() {
        System.out.println("1 Message users\n2 Group message\n3 Message speakers");
        exitMessage();
    }

    public void speakerMenu(EventManager em) {
        System.out.println("SPEAKERS:");
        ArrayList<Event> events = em.getEventsList();
        for (Event event : events) {
            System.out.println(event.getSpeaker().getUserName() +
                    " | Event (" + event.getId() + "): " + event.getName());
        }
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

    public void eventMenu(Speaker user, EventManager em) {
        System.out.println("EVENTS:");
        ArrayList<Long> events = user.getTalks();
        for (Long evt_id : events) {
            Event event = em.getEventById(evt_id);
            System.out.println(event.getName() + " (" + event.getId() + ")");
            System.out.println("Time: " + event.getTime() + " | " + "Room: " + event.getRoom() + "\n");
        }
    }

    public void eventMenu(EventManager em) {
        System.out.println("EVENTS:");
        ArrayList<Event> events = em.getEventsList();
        for (Event event : events) {
            System.out.println(event.getName() + " (" + event.getId() + ")");
            System.out.println("Time: " + event.getTime() + " | " + "Room: " + event.getRoom() + "\n");
        }
    }

    public void success() {
        System.out.println("Message successfully sent!");
    }
}
