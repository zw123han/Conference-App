import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventCreator {
    private EventManager em;

    public EventCreator(EventManager em) {
        this.em = em;
    }

    public boolean createEvent(String name, String room, LocalDateTime time, Speaker speaker, int capacity) {
        ArrayList<Event> events = this.em.getEventsList();
        for (Event event : events) {
            if ((event.getRoom().equals(room) && event.getTime() == time) ||
                    (event.getTime() == time && event.getSpeaker().getUserName().equals(speaker.getUserName()))) {
                return false;
            }
        }
        Event ev = new Event(name, room, time, speaker, capacity);
        this.em.createEvent(name, room, time, speaker, capacity);
        return true;
    }
}