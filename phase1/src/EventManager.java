import java.io.Serializable;
import java.util.*;
import java.time.*;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 * @serial
 */
public class EventManager implements Serializable {

    private HashMap<Long, Event> events;

    /**
     * (please describe)
     *
     * @param events        (please describe)
     */
    public EventManager(ArrayList<Event> events) {
        this.events = new HashMap<Long, Event>();

        for(Event ev: events){
            this.events.put(ev.getId(), ev);
        }
    }

    /**
     * (please describe)
     */
    public EventManager() {
        this.events = new HashMap<Long, Event>();
    }

    /**
     * (please describe)
     *
     * @param name              (please describe)
     * @param room              (please describe)
     * @param time              (please describe)
     * @param speakerName       (please describe)
     * @param capacity          (please describe)
     * @param speaker           (please describe)
     */
    public void createEvent(String name, String room, LocalDateTime time, String speakerName, int capacity, Speaker speaker) {
        Event ev = new Event(name, room, time, speakerName, capacity);
        this.events.put(ev.getId(), ev);
        speaker.addTalk(ev.getId());
    }

    /**
     * (please describe)
     *
      * @return     (please describe)
     */
    public ArrayList<Event> getEventsList() {

        return new ArrayList(this.events.values());
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public HashMap<Long, Event> getEventsMap() {
        return this.events;
    }

    /**
     * (please describe)
     *
     * @param id    (please describe)
     * @return      (please describe)
     */
    public Event getEventById(long id) {
        return this.events.get(id);
    }

    /**
     * (please describe)
     *
     * @param id    (please describe)
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

}