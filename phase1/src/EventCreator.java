import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * (please describe)
 *
 * @author
 */
public class EventCreator {
    private EventManager em;
    private Registrar reg;

    /**
     * (please describe)
     *
     * @param em        (please describe)
     * @param reg       (please describe)
     */
    public EventCreator(EventManager em, Registrar reg) {
        this.em = em;
        this.reg = reg;
    }

    /**
     * (please describe)
     *
     * @param name                              (please describe)
     * @param room                              (please describe)
     * @param start_time                        (please describe)
     * @param speaker                           (please describe)
     * @param capacity                          (please describe)
     * @return                                  (please describe)
     * @throws EventCreationFailureException    (please describe)
     */
    public boolean createEvent(String name, String room, LocalDateTime start_time, String speaker, int capacity)
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
                } else if (event.getSpeaker().equals(speaker)) {
                    throw new EventCreationFailureException("This speaker is unavailable for this time");
                }
            }
        }
        this.em.createEvent(name, room, start_time, speaker, capacity, (Speaker) reg.getUserByUserName(speaker));
        return true;
    }
}