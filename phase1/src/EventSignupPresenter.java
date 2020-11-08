public class EventSignupPresenter {
    private EventManager em;
    private EventSignup es;

    public EventSignupPresenter(EventSignup es, EventManager em) {
        this.es = es;
        this.em = em;
    }

    public void joinEvent(User user, String event_name) {
        EventSignupController esc = new EventSignupController(this.es, this.em);
        if (!(esc.signUserUp(user, event_name))) {
            System.out.println("Unable to join event");
        } else {
            System.out.println("Joined event");
        }
    }

    public void leaveEvent(User user, String event_name) {
        EventSignupController esc = new EventSignupController(this.es, this.em);
        if (!(esc.removeUser(user, event_name))) {
            System.out.println("Unable to leave event");
        } else {
            System.out.println("Left event");
        }
    }
}