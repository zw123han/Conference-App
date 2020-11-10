import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventCreator {
    private EventManager em;

    public EventCreator(EventManager em) {
        this.em = em;
    }

    public boolean createEvent(String name, String room, LocalDateTime time, Speaker speaker, int capacity) throws EventExistsException {
        ArrayList<Event> events = this.em.getEventsList();
        for (Event event : events) {
            if ((event.getRoom().equals(room) && event.getTime() == time)) {
                throw new EventExistsException("this room is taken for this time");
            } else if (event.getTime() == time && event.getSpeaker().getUserName().equals(speaker.getUserName())) {
                throw new EventExistsException("this speaker is unavailable for this time");
            }
        }
        this.em.createEvent(name, room, time, speaker, capacity);
        return true;
    }
}