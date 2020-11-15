import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class EventSignupPresenter {
    private EventManager em;
    private EventSignup es;

    /**
     * (please describe)
     *
     * @param es        (please describe)
     * @param em        (please describe)
     */
    public EventSignupPresenter(EventSignup es, EventManager em) {
        this.es = es;
        this.em = em;
    }

    /**
     * (please describe)
     *
     * @param user          (please describe)
     * @param event_id      (please describe)
     */
    public void joinEvent(User user, String event_id) {
        EventSignupController esc = new EventSignupController(this.es, this.em);
        try {
            if (!(esc.signUserUp(user, Long.parseLong(event_id)))) {
                System.out.println("Unable to join event");
            } else {
                System.out.println("Joined event");
            }
        } catch (EventNotFoundException e) {
            System.out.println("This event has not yet been registered.");
        } catch (NumberFormatException e){
            System.out.println("Please enter a valid event ID");
        }
    }

    /**
     * (please describe)
     *
     * @param user          (please describe)
     * @param event_id      (please describe)
     */
    public void leaveEvent(User user, String event_id) {
        EventSignupController esc = new EventSignupController(this.es, this.em);
        try {
            if (!(esc.removeUser(user, Long.parseLong(event_id)))) {
                System.out.println("Unable to leave event. You are already in this event.");
            } else {
                System.out.println("Left event.");
            }
        } catch (EventNotFoundException e) {
            System.out.println("This event has not yet been registered.");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid event ID");
        }
    }

    /**
     * (please describe)
     */
    public void viewEvents(){
        System.out.println("Available Events: ");
        for(Event ev: this.em.getEventsList()){
            if(!ev.isFull()) {
                System.out.println("Name: " + ev.getName());
                System.out.println("id: " + ev.getId());
                System.out.println("Time: " + DateTimeFormatter.ofLocalizedDateTime(
                        FormatStyle.SHORT)
                        .format(ev.getTime()));
                System.out.println("Room: " + ev.getRoom());
                System.out.println("Capacity: " + ev.getNumberOfSignedUpUsers() + "/" + ev.getCapacity());
                System.out.println("Speaker: " + ev.getSpeaker());
                System.out.println("------------------------");
            }
        }
    }

    /**
     * (please describe)
     *
     * @param user      (please describe)
     */
    public void usersEvents(User user) {
        System.out.println("Your Events: ");
        for (Long event_long: user.getEvents())  {
            Event ev = em.getEventById(event_long);
            System.out.println("Name: " + ev.getName());
            System.out.println("id: " + ev.getId());
            System.out.println("Time: " + DateTimeFormatter.ofLocalizedDateTime(
                    FormatStyle.SHORT)
                    .format(ev.getTime()));
            System.out.println("Room: " + ev.getRoom());
            System.out.println("Capacity: " + ev.getNumberOfSignedUpUsers() + "/" + ev.getCapacity());
            System.out.println("Speaker: " + ev.getSpeaker());
            System.out.println("------------------------");
        }
    }
}