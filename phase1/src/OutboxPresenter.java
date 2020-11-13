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
        System.out.println("------------------------");
        ArrayList<Long> events = em.getEventIDs();
        ArrayList<String> speakers = new ArrayList<>();
        for (Long evt_id : events) {
            String speaker = em.getSpeaker(evt_id);
            if (!speakers.contains(speaker)) {
                speakers.add(speaker);
            }
        }
        for (String s : speakers) {
            System.out.println(reg.getNameByUsername(s) + " (@" + s + ")");
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
        System.out.println("------------------------");
        ArrayList<String> friends = reg.getUserFriends(username);
        for (String friend : friends) {
            System.out.println(reg.getNameByUsername(friend) + " (@" + friend + ")");
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
        System.out.println("------------------------");
        ArrayList<Long> events = reg.getSpeakerTalks(username);
        for (Long evt_id : events) {
            System.out.println("Name: " + em.getName(evt_id));
            System.out.println("id: " + evt_id);
            System.out.println("Time: " + em.getTime(evt_id));
            System.out.println("Room: " + em.getRoom(evt_id));
            System.out.println("------------------------");
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
        System.out.println("------------------------");
        ArrayList<Long> events = em.getEventIDs();
        for (Long evt_id : events) {
            System.out.println("Name: " + em.getName(evt_id));
            System.out.println("id: " + evt_id);
            System.out.println("Time: " + em.getTime(evt_id));
            System.out.println("Room: " + em.getRoom(evt_id));
            System.out.println("------------------------");
        }
        if (events.isEmpty()) {
            System.out.println("There are no events.\n");
        }
    }

    /**
     * (please describe)
     */
    public void success() {
        System.out.println("\nMessage successfully sent!");
    }
}
