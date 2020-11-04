import java.util.*;
import java.time.*;

public class EventManager {

    private HashMap<String, Event> events;

    public EventManager(ArrayList<Event> events) {
        for(Event ev: events){
            this.events.put(ev.getName(), ev);
        }
    }

    public EventManager() {
        this.events = new HashMap<String, Event>();
    }

    public void createEvent(String name, String room, LocalDateTime time, Speaker speaker) {
        Event ev = new Event(name, room, time, speaker);
        this.events.put(ev.getName(), ev);
    }

    public ArrayList<Event> getEventsList() {
        return new ArrayList(this.events.values());
    }

    public HashMap<String, Event> getEventsMap() {
        return this.events;
    }
}