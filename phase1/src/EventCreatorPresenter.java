import java.time.LocalDateTime;

public class EventCreatorPresenter {
    private EventManager em;
    private Registrar reg;

    public EventCreatorPresenter(EventManager em, Registrar reg) {
        this.em = em;
        this.reg = reg;
    }

    public String promptEventCreation(String name, String room, LocalDateTime time, String speaker, int capacity) {
        EventCreator ec = new EventCreator(this.em, reg);
        try {
            if (!(ec.createEvent(name, room, time, speaker, capacity))) {
                return ("Unable to create event");
            } else {
                return ("Event created");
            }
        } catch (EventCreationFailureException e) {
            System.out.println(e);
            return ("");
        }
    }
}