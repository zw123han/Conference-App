package MessagingSystem;

import UserSystem.*;
import EventSystem.*;
import java.util.*;

/**
 * Handles text format and display for the client.
 *
 * @author Chrisee Zhu
 */
public class MessageOutboxPresenter extends CommandPresenter {

    /**
     * Displays message composition options by target.
     */
    public String menuDisplay() {
        return "\n1) Message users\n2) Group message\n3) Message speakers\n";
    }

    /**
     * Displays a list of speakers hosting events at this conference, including their name and username.
     *
     * @param reg       Registrar
     * @param em        EventManager
     */
    public String speakerMenu(Registrar reg, EventManager em) {
        String result = "\nSPEAKERS:\n------------------------";
        ArrayList<Long> events = em.getEventIDs();
        ArrayList<String> speakers = new ArrayList<>();
        for (Long evt_id : events) {
            String speaker = em.getSpeaker(evt_id);
            if (!speakers.contains(speaker)) {
                speakers.add(speaker);
            }
        }
        for (String s : speakers) {
            result += ("\n" + reg.getNameByUsername(s) + " (@" + s + ")");
        }
        if (events.isEmpty()) {
            return result + "\nThere are no speakers.\n";
        }
        return result + "\n";
    }

    /**
     * Displays a list of friends that this user has, including their name and username.
     *
     * @param reg           Registrar
     * @param username      username of the sender; sender must exist in Registrar
     */
    public String friendMenu(Registrar reg, String username) {
        String result = "\nFRIENDS:\n------------------------";
        ArrayList<String> friends = reg.getUserFriends(username);
        for (String friend : friends) {
            result += ("\n" + reg.getNameByUsername(friend) + " (@" + friend + ")");
        }
        if (friends.isEmpty()) {
            return result + "\nYou have no friends.\n";
        }
        return result + "\n";
    }

    /**
     * Displays a list of events hosted by a speaker, including their name, IDs, time, and room.
     *
     * @param username      username of a speaker (sender); speaker must exist in Registrar
     * @param em            EventManager
     * @param reg           Registrar
     */
    public String eventMenu(String username, EventManager em, Registrar reg) {
        String result = "\nEVENTS:\n------------------------";
        ArrayList<Long> events = reg.getSpeakerTalks(username);
        for (Long evt_id : events) {
            result += ("\nName: " + em.getName(evt_id));
            result += ("\nid: " + evt_id);
            result += ("\nTime: " + em.getTime(evt_id));
            result += ("\nRoom: " + em.getRoom(evt_id) + "\n------------------------");
        }
        if (events.isEmpty()) {
            return "\nYou're not hosting any talks.\n";
        }
        return result;
    }

    /**
     * Displays a list of events available at this conference, including their name, IDs, time, and room.
     *
     * @param em        EventManager
     */
    public String eventMenu(EventManager em) {
        String result = "\nEVENTS:\n------------------------";
        ArrayList<Long> events = em.getEventIDs();
        for (Long evt_id : events) {
            result += ("\nName: " + em.getName(evt_id));
            result += ("\nid: " + evt_id);
            result += ("\nTime: " + em.getTime(evt_id));
            result += ("\nRoom: " + em.getRoom(evt_id) + "\n------------------------");
        }
        if (events.isEmpty()) {
            return result + "\nThere are no events.\n";
        }
        return result;
    }

    /**
     * Displays a success message for a sent message.
     *
     * @param recipient (enter message)
     */
    public String success(String recipient) {
        return "\nMessage successfully sent to " + recipient + "!";
    }
}
