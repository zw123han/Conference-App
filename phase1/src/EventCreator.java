import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventCreator {
    private EventManager em;

    public EventCreator(EventManager em) {
        this.em = em;
    }

    public boolean createEvent(String name, String room, LocalDateTime time, Speaker speaker) {
        ArrayList<Event> events = this.em.getEventsList();
        for (Event event : events) {
            if ((event.getRoom() == room && event.getTime() == time) ||
                    (event.getTime() == time && event.getSpeaker().getUserName() == speaker.getUserName())) {
                return false;
            }
        }
        Event ev = new Event(name, room, time, speaker);
        this.em.createEvent(name, room, time, speaker);
        return true;
    }
}