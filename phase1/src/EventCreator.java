import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventCreator {
    private EventManager em;

    public EventCreator(EventManager em) {
        this.em = em;
    }

    public boolean createEvent(String name, String room, LocalDateTime start_time, Speaker speaker, int capacity)
            throws EventCreationFailureException {
        ArrayList<Event> events = this.em.getEventsList();
        for (Event event : events) {
            LocalDateTime lower = event.getTime();
            LocalDateTime upper = lower.plusMinutes(60);
            LocalDateTime end_time = start_time.plusMinutes(60);
            if ((start_time.isAfter(lower) && start_time.isBefore(upper)) ||
                    (end_time.isAfter(lower) && end_time.isBefore(upper)) || (start_time.isEqual(lower))){
                if ((event.getRoom().equals(room))) {
                    throw new EventCreationFailureException("This room is taken for this time");
                } else if (event.getSpeaker().getUserName().equals(speaker.getUserName())) {
                    throw new EventCreationFailureException("This speaker is unavailable for this time");
                }
            }
        }
        this.em.createEvent(name, room, start_time, speaker, capacity);
        return true;
    }
}