import java.util.*;
import java.time.*;

public class EventManager {

    private ArrayList<Event> events;

    public EventManager(ArrayList<Event> events) {
        this.events = events;
    }

    public EventManager() {
        this.events = new ArrayList<Event>();
    }

    public void createEvent(String name, String room, LocalDateTime time, Speaker speaker) {
        Event ev = new Event(name, room, time, speaker);
        this.events.add(ev);
    }

    public ArrayList<Event> getEvents() {
        return this.events;
    }
}