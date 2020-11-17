/**
 * Use Case class that enables signing up and leaving events.
 *
 * @author Andy, Nithilan
 */
// Choosing not to store event manager in here for now
public class EventSignup{

    /**
     * Signs up given user for given Event
     *
     * @param user      User that wants to join the given event
     * @param event     Event object of the event to be joined
     */
    public void signUserUp(User user, Event event){
        user.addEvent(event.getId());
        event.addUser(user.getUserName());
    }

    /**
     * Removes given user from given event
     *
     * @param user      User that wants to leave the event
     * @param event     Event to be left.
     */
    public void removeUser(User user, Event event){
        user.removeEvent(event.getId());
        event.removeUser(user.getUserName());
    }

}
