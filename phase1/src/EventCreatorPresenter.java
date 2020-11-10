import java.time.LocalDateTime;

public class EventCreatorPresenter {
    private EventManager em;

    public EventCreatorPresenter(EventManager em) {
        this.em = em;
    }

    public String promptEventCreation(String name, String room, LocalDateTime time, Speaker speaker, int capacity) {
        EventCreator ec = new EventCreator(this.em);
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