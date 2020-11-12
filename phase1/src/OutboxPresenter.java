import java.util.*;

public class OutboxPresenter extends CommandPresenter{

    public void menuDisplay() {
        System.out.println("\n1) Message users\n2) Group message\n3) Message speakers\n");
    }

    public void speakerMenu(Registrar reg, EventManager em) {
        System.out.println("\nSPEAKERS:");
        ArrayList<Event> events = em.getEventsList();
        ArrayList<String> speakers = new ArrayList<>();
        for (Event event : events) {
            String speaker = event.getSpeaker();
            if (!speakers.contains(speaker)) {
                speakers.add(speaker);
            }
        }
        for (String s : speakers) {
            System.out.println(reg.getUserByUserName(s).getName() + " (@" + s + ")");
        }
        if (events.isEmpty()) {
            System.out.println("There are no speakers.");
        }
        System.out.println("");
    }

    public void friendMenu(Registrar reg, User user) {
        System.out.println("\nFRIENDS:");
        ArrayList<String> friends = user.getFriends();
        for (String friend : friends) {
            System.out.println(reg.getUserByUserName(friend).getName() + " (@" + friend + ")");
        }
        if (friends.isEmpty()) {
            System.out.println("You have no friends.");
        }
        System.out.println("");
    }

    public void eventMenu(Speaker user, EventManager em) {
        System.out.println("\nEVENTS:");
        ArrayList<Long> events = user.getTalks();
        for (Long evt_id : events) {
            Event event = em.getEventById(evt_id);
            System.out.println(event.getName() + " (id: " + event.getId() + ")");
            System.out.println("Time: " + event.getTime());
            System.out.println("Room: " + event.getRoom() + "\n");
        }
        if (events.isEmpty()) {
            System.out.println("You're not hosting any talks.");
        }
        System.out.println("");
    }

    public void eventMenu(EventManager em) {
        System.out.println("\nEVENTS:");
        ArrayList<Event> events = em.getEventsList();
        for (Event event : events) {
            System.out.println(event.getName() + " (id: " + event.getId() + ")");
            System.out.println("Time: " + event.getTime());
            System.out.println("Room: " + event.getRoom() + "\n");
        }
        if (events.isEmpty()) {
            System.out.println("You have no events.");
        }
        System.out.println("");
    }

    public void success() {
        System.out.println("\nMessage successfully sent!\n");
    }
}
