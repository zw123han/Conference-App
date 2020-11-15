import java.time.LocalDateTime;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class EventCreatorPresenter {
    private EventManager em;
    private Registrar reg;

    /**
     * (please describe)
     *
     * @param em        (please describe)
     * @param reg       (please describe)
     */
    public EventCreatorPresenter(EventManager em, Registrar reg) {
        this.em = em;
        this.reg = reg;
    }

    /**
     * (please describe)
     *
     * @param name      (please describe)
     * @param room      (please describe)
     * @param time      (please describe)
     * @param speaker   (please describe)
     * @param capacity  (please describe)
     * @return          (please describe)
     */
    public String promptEventCreation(String name, String room, LocalDateTime time, String speaker, int capacity) {
        EventCreator ec = new EventCreator(this.em, reg);
        try {
            if (!(ec.createEvent(name, room, time, speaker, capacity))) {
                return ("Unable to create event");
            } else {
                return ("Event created");
            }
        } catch (EventCreationFailureException e) {
//            e.printStackTrace();
//            return ("");
            System.out.println("Speaker or room selected unavailable for this time.");
            return("");
        }
    }
}