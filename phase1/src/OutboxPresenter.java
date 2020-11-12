import java.util.*;

public class OutboxPresenter extends CommandPresenter{

    public void menuDisplay() {
        System.out.println("\n1) Message users\n2) Group message\n3) Message speakers\n");
    }

    public void speakerMenu(Registrar reg, EventManager em) {
        System.out.println("\nSPEAKERS:");
        ArrayList<String> speakers = new ArrayList<>();
        ArrayList<Event> events = em.getEventsList();
        for (Event event : events) {
            String name = event.getSpeaker().getUserName();
            if (!speakers.contains(name)) {
                speakers.add(name);
            }
        }
        for (String s : speakers) {
            System.out.println(reg.getUserByUserName(s).getName() + " (@" + s + ")");
        }
        System.out.println("\n");
    }

    public void friendMenu(Registrar reg, User user) {
        System.out.println("\nFRIENDS:");
        ArrayList<String> friends = user.getFriends();
        for (String friend : friends) {
            System.out.println(reg.getUserByUserName(friend).getName() + " (@" + friend + ")");
        }
        System.out.println("\n");
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
    }

    public void eventMenu(EventManager em) {
        System.out.println("\nEVENTS:");
        ArrayList<Event> events = em.getEventsList();
        for (Event event : events) {
            System.out.println(event.getName() + " (id: " + event.getId() + ")");
            System.out.println("Time: " + event.getTime());
            System.out.println("Room: " + event.getRoom() + "\n");
        }
    }

    public void success() {
        System.out.println("\nMessage successfully sent!\n");
    }
}
