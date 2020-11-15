import java.io.Serializable;
import java.util.*;
import java.time.*;

/**
 * (please describe)
 *
 * @author Andy, Nithilan
 * @version %I%, %G%
 * @serial
 */
public class EventManager implements Serializable {

    private HashMap<Long, Event> events;

    /**
     * Creates an instance of EventManager with a list of preloaded events.
     *
     * @param events        List of event objects
     */
    public EventManager(ArrayList<Event> events) {
        this.events = new HashMap<Long, Event>();

        for(Event ev: events){
            this.events.put(ev.getId(), ev);
        }
    }

    /**
     * Creates an instance of EventManager with no preloaded events.
     */
    public EventManager() {
        this.events = new HashMap<Long, Event>();
    }

    /**
     * Creates a new Event and adds event to HashMap of events.
     *
     * @param name              name of event
     * @param room              room that event takes place in
     * @param time              start time of the event
     * @param speakerName       name of the speaker at this event
     * @param capacity          capacity of the event
     * @param speaker           Speaker object of the speaker of this event
     */
    public void createEvent(String name, String room, LocalDateTime time, String speakerName, int capacity, Speaker speaker) {
        Event ev = new Event(name, room, time, speakerName, capacity);
        this.events.put(ev.getId(), ev);
        speaker.addTalk(ev.getId());
    }

    /**
     * Gets list of events that have been created so far in the program.
     *
      * @return     ArrayList of Event objects.
     */
    public ArrayList<Event> getEventsList() {

        return new ArrayList(this.events.values());
    }

    /**
     * Gets a map of event id to Event object of all events stored in this EventManager
     *
     * @return      HashMap of event id to Event object
     */
    public HashMap<Long, Event> getEventsMap() {
        return this.events;
    }

    /**
     * Gets Event object for specific event id.
     *
     * @param id    id of event to be returned
     * @return      Event object corresponding to given id
     */
    public Event getEventById(long id) {
        return this.events.get(id);
    }

    /**
     * checks whether event with given id exists
     *
     * @param id    id of event being checked
     * @return      True or false.
     */
    public boolean hasEvent(long id) {
        return this.events.containsKey(id);
    }

    /**
     * (please describe)
     *
     * @param id    (please describe)
     * @return      (please describe)
     */
    public Event getEvent(Long id){
        return this.events.get(id);
    }

    /**
     *
     * @param id
     * @return
     */
    public String getName(Long id) {
        return getEvent(id).getName();
    }

    /**
     *
     * @param id
     * @return
     */
    public LocalDateTime getTime(Long id) {
        return getEvent(id).getTime();
    }

    /**
     *
     * @param id
     * @return
     */
    public String getRoom(Long id) {
        return getEvent(id).getRoom();
    }

    /**
     *
     * @param id
     * @return
     */
    public String getSpeaker(Long id) {
        return getEvent(id).getSpeaker();
    }

    /**
     *
     * @param id
     * @return
     */
    public int getCapacity(Long id) {
        return getEvent(id).getCapacity();
    }

    /**
     *
     * @return
     */
    public ArrayList<Long> getEventIDs() {
        return new ArrayList<>(events.keySet());
    }

    /**
     *
     * @param id
     * @return
     */
    public ArrayList<String> getSignedUpUsers(Long id) throws EventNotFoundException {
        if (getEvent(id) == null){
            throw new EventNotFoundException();
        }
        return getEvent(id).getSignedUpUsers();
    }
}