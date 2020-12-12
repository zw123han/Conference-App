package UserSystem;

import java.util.*;

/**
 * This class represents speakers for the events. This class will save a list of talks for which a speaker
 * is scheduled to talk.
 *
 * @author Fred
 */
public class Speaker extends User {

    private ArrayList<Long> talks;

    /**
     * Constructor for the speaker class. Initializes the object as required.
     *
     * @param name          The name of the speaker.
     * @param userName      The username of the speaker.
     * @param password      The password for the speaker.
     */
    public Speaker(String name, String userName, String password) {
        super(name, userName, password);
        this.talks = new ArrayList<>();
    }

    /**
     * Returns the talks that the speaker is scheduled to host.
     *
     * @return      A list of talks.
     */
    public ArrayList<Long> getTalks() {
        return talks;
    }

    /**
     * Saves talks for which a speaker will conduct.
     *
     * @param talkId        The identifier for the event.
     */
    public void addTalk(Long talkId) {
        talks.add(talkId);
    }

    /**
     * Removes an event from this speaker's talk list.
     *
     * @param talkId The id of the talk/event to be removed.
     */
    public void removeTalk(Long talkId) {
        talks.remove(talkId);
    }
    /**
     * Gets the type of user this user is.
     *
     * @return "speaker"
     */
    @Override
    public String getUserType(){
        return "speaker";
    };
}
