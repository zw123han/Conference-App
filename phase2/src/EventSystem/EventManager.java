package EventSystem;

import UserSystem.Speaker;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Use Case Class that stores all events in the program and enables creation and deletion of events.
 *
 * @author Andy, Nithilan
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
     * @param duration          duration of the event
     * @param speakerNameList   list of names of speakers at this event
     * @param capacity          capacity of the event
     * @param speakerList       list of speaker objects for the speakers of this event
     */
    public void createEvent(String name, String room, LocalDateTime time, long duration, ArrayList<String> speakerNameList, int capacity, ArrayList<Speaker> speakerList) {
        Event ev = new Event(name, room, time, duration, speakerNameList, capacity);
        this.events.put(ev.getId(), ev);
        for(Speaker s: speakerList){
            s.addTalk(ev.getId());
        }
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
     * checks whether event with given id exists
     *
     * @param id    id of event being checked
     * @return      True or false.
     */
    public boolean hasEvent(long id) {
        return this.events.containsKey(id);
    }

    /**
     * Gets Event object for specific event id.
     *
     * @param id    id of event to be returned
     * @return      Event object corresponding to given id
     */
    public Event getEvent(Long id){
        return this.events.get(id);
    }

    /**
     *
     * gets the name of event with given id
     *
     * @param id id of event
     * @return name of event with given event id
     */
    public String getName(Long id) {
        return getEvent(id).getName();
    }

    /**
     *
     * gets the start time of event with given id
     *
     * @param id id of event
     * @return start time of event with given event id
     */
    public LocalDateTime getTime(Long id) {
        return getEvent(id).getTime();
    }

    /**
     *
     * gets the room that event with given id takes place in
     *
     * @param id id of event
     * @return room that event with given id takes place in
     */
    public String getRoom(Long id) {
        return getEvent(id).getRoom();
    }

    /**
     *
     * gets the list of names of speaker for event with given id
     *
     * @param id id of event
     * @return list of names of speakers for event with given event id
     */
    public ArrayList<String> getSpeakerList(Long id) {
        return getEvent(id).getSpeakerList();
    }

    /**
     *
     * gets the type of the event with the given id
     *
     * @return the type of the event with the given id
     */
    public String getType(Long id) {
        return getEvent(id).getType();
    }

    /**
     *
     * gets a list of all event ids in this program
     *
     * @return list of all event ids in event manager
     */
    public ArrayList<Long> getEventIDs() {
        return new ArrayList<>(events.keySet());
    }

    /**
     *
     * gets all signed up users for given event id
     *
     * @param id id of event
     * @return list of usernames
     * @throws EventNotFoundException thrown if event does not exist
     */
    public ArrayList<String> getSignedUpUsers(Long id) throws EventNotFoundException {
        if (getEvent(id) == null){
            throw new EventNotFoundException();
        }
        return getEvent(id).getSignedUpUsers();
    }
}