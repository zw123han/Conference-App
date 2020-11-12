import java.util.*;

public class OutboxPresenter extends CommandPresenter{

    public void menuDisplay() {
        System.out.println("\n1 Message users\n2 Group message\n3 Message speakers\n");
    }

    public void speakerMenu(EventManager em) {
        System.out.println("\nSPEAKERS:");
        ArrayList<Event> events = em.getEventsList();
        for (Event event : events) {
            System.out.println(event.getSpeaker().getUserName() +
                    " | Event (" + event.getId() + "): " + event.getName());
        }
        System.out.println("\n");
    }

    public void friendMenu(User user) {
        System.out.println("\nFRIENDS:");
        ArrayList<String> friends = user.getFriends();
        int n = 1;
        for (String friend : friends) {
            System.out.println(n + ". " + friend);
            n += 1;
        }
        System.out.println("\n");
    }

    public void eventMenu(Speaker user, EventManager em) {
        System.out.println("\nEVENTS:");
        ArrayList<Long> events = user.getTalks();
        for (Long evt_id : events) {
            Event event = em.getEventById(evt_id);
            System.out.println(event.getName() + " (" + event.getId() + ")");
            System.out.println("Time: " + event.getTime() + " | " + "Room: " + event.getRoom() + "\n");
        }
        System.out.println("\n");
    }

    public void eventMenu(EventManager em) {
        System.out.println("\nEVENTS:");
        ArrayList<Event> events = em.getEventsList();
        for (Event event : events) {
            System.out.println(event.getName() + " (" + event.getId() + ")");
            System.out.println("Time: " + event.getTime() + " | " + "Room: " + event.getRoom() + "\n");
        }
        System.out.println("\n");
    }

    public void success() {
        System.out.println("\nMessage successfully sent!\n");
    }
}
