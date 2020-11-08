public class EventSignupPresenter {

    private EventSignupController esc;

    public EventSignupPresenter(EventSignupController esc) {
        this.esc = esc;
    }

    public boolean joinEvent(String event_name, User user) {
        return esc.signUserUp(user, event_name);
    }

    public boolean leaveEvent(String event_name, User user) {
        return esc.removeUser(user, event_name);
    }
}
