import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;

public class EventSignupPresenter {
    private EventManager em;
    private EventSignup es;

    public EventSignupPresenter(EventSignup es, EventManager em) {
        this.es = es;
        this.em = em;
    }

    public void joinEvent(User user, String event_id) {
        EventSignupController esc = new EventSignupController(this.es, this.em);
        try {
            if (!(esc.signUserUp(user, Long.parseLong(event_id)))) {
                System.out.println("Unable to join event");
            } else {
                System.out.println("Joined event");
            }
        } catch (EventNotFoundException e) {
            System.out.println(e);
        }
    }

    public void leaveEvent(User user, String event_id) {
        EventSignupController esc = new EventSignupController(this.es, this.em);
        try {
            if (!(esc.removeUser(user, Long.parseLong(event_id)))) {
                System.out.println("Unable to leave event. You are already in this event.");
            } else {
                System.out.println("Left event");
            }
        } catch (EventNotFoundException e) {
            System.out.println(e.toString());
        }
    }

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
                System.out.println("------------------------");
            }
        }
    }
    public void usersEvents(User user) {
        System.out.println("\nYour Events: ");
        for (Long event_long: user.getEvents())  {
            Event ev = em.getEventById(event_long);
            System.out.println("Name: " + ev.getName());
            System.out.println("id: " + ev.getId());
            System.out.println("Time: " + DateTimeFormatter.ofLocalizedDateTime(
                    FormatStyle.SHORT)
                    .format(ev.getTime()));
            System.out.println("Room: " + ev.getRoom());
            System.out.println("------------------------");
        }
    }
}