public class EventSignupPresenter {

    private EventSignupController esc;

    public EventSignupPresenter(EventSignupController esc) {
        this.esc = esc;
    }

    public boolean JoinEvent(String event_name, User user) {
        return esc.signUserUp(user, event_name);
    }

    public boolean LeaveEvent(String event_name, User user) {
        return esc.removeUser(user, event_name);
    }
}
