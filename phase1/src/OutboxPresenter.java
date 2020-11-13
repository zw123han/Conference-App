import java.util.*;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class OutboxPresenter extends CommandPresenter{

    /**
     * (please describe)
     */
    public void menuDisplay() {
        System.out.println("\n1) Message users\n2) Group message\n3) Message speakers\n");
    }

    /**
     * (please describe)
     *
     * @param reg       (please describe)
     * @param em        (please describe)
     */
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
        System.out.println();
    }

    /**
     * (please describe)
     *
     * @param reg       (please describe)
     * @param username      (please describe)
     */
    public void friendMenu(Registrar reg, String username) {
        System.out.println("\nFRIENDS:");
        ArrayList<String> friends = reg.getUserFriends(username);
        for (String friend : friends) {
            System.out.println(username + " (@" + friend + ")");
        }
        if (friends.isEmpty()) {
            System.out.println("You have no friends.");
        }
        System.out.println();
    }

    /**
     * (please describe)
     *
     * @param username      (please describe)
     * @param em        (please describe)
     */
    public void eventMenu(String username, EventManager em, Registrar reg) {
        System.out.println("\nEVENTS:");
        ArrayList<Long> events = reg.getSpeakerTalks(username);
        for (Long evt_id : events) {
            Event event = em.getEventById(evt_id);
            System.out.println(event.getName() + " (id: " + event.getId() + ")");
            System.out.println("Time: " + event.getTime());
            System.out.println("Room: " + event.getRoom() + "\n");
        }
        if (events.isEmpty()) {
            System.out.println("You're not hosting any talks.\n");
        }
    }

    /**
     * (please describe)
     *
     * @param em        (please describe)
     */
    public void eventMenu(EventManager em) {
        System.out.println("\nEVENTS:");
        ArrayList<Event> events = em.getEventsList();
        for (Event event : events) {
            System.out.println(event.getName() + " (id: " + event.getId() + ")");
            System.out.println("Time: " + event.getTime());
            System.out.println("Room: " + event.getRoom() + "\n");
        }
        if (events.isEmpty()) {
            System.out.println("You have no events.\n");
        }
    }

    /**
     * (please describe)
     */
    public void success() {
        System.out.println("\nMessage successfully sent!");
    }
}
