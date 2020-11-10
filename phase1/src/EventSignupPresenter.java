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
                System.out.println("Unable to leave event");
            } else {
                System.out.println("Left event");
            }
        } catch (EventNotFoundException e) {
            System.out.println(e.toString());
        }
    }
}