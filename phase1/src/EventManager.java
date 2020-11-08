import java.util.*;
import java.time.*;

public class EventManager {

    private HashMap<Long, Event> events;

    public EventManager(ArrayList<Event> events) {
        for(Event ev: events){
            this.events.put(ev.getId(), ev);
        }
    }

    public EventManager() {
        this.events = new HashMap<Long, Event>();
    }

    public void createEvent(String name, String room, LocalDateTime time, Speaker speaker, int capacity) {
        Event ev = new Event(name, room, time, speaker, capacity);
        this.events.put(ev.getId(), ev);
    }

    public ArrayList<Event> getEventsList() {
        return new ArrayList(this.events.values());
    }

    public HashMap<String, Event> getEventsMap() {
        return this.events;
    }

    public Event getEventById(long id) {
        return this.events.get(id);
    }
}