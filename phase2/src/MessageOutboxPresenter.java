import java.util.*;

/**
 * Handles text format and display for the client.
 *
 * @author Chrisee Zhu
 */
public class MessageOutboxPresenter extends CommandPresenter{

    /**
     * Displays message composition options by target.
     */
    public void menuDisplay() {
        System.out.println("\n1) Message users\n2) Group message\n3) Message speakers\n");
    }

    /**
     * Displays a list of speakers hosting events at this conference, including their name and username.
     *
     * @param reg       Registrar
     * @param em        EventManager
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
     * Displays a list of friends that this user has, including their name and username.
     *
     * @param reg           Registrar
     * @param username      username of the sender; sender must exist in Registrar
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
     * Displays a list of events hosted by a speaker, including their name, IDs, time, and room.
     *
     * @param username      username of a speaker (sender); speaker must exist in Registrar
     * @param em            EventManager
     * @param reg           Registrar
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
     * Displays a list of events available at this conference, including their name, IDs, time, and room.
     *
     * @param em        EventManager
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
     * Displays a success message for a sent message.
     *
     * @param recipient (enter message)
     */
    public void success(String recipient) {
        System.out.println("\nMessage successfully sent to " + recipient + "!");
    }
}
