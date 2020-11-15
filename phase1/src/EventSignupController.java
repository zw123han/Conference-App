import java.util.HashMap;

/**
 * Controller that manages signing up and leaving events
 *
 * @author Andy, Nithilan
 * @version %I%, %G%
 */
public class EventSignupController {
    private EventManager em;
    private EventSignup es;

    /**
     * Creates an instance of EventSignupController.
     *
     * @param es        Instance of EventSignup to be assigned
     * @param em        Instance of EventManager to be assigned
     */
    public EventSignupController(EventSignup es, EventManager em){
        this.es = es;
        this.em = em;
    }

    /**
     * Signs up given user to the event corresponding to the event_id.
     *
     * @param user                      User that needs to be signed up to given event
     * @param event_id                  event id of the Event to join
     * @return                          True iff user has joined given event
     * @throws EventNotFoundException   Thrown when the given event id does not exist
     */
    public boolean signUserUp(User user, Long event_id) throws EventNotFoundException{
        //HashMap<Long, Event> nameToEventDict = em.getEventsMap();
        Event this_event = em.getEvent(event_id);
        if(this_event == null){
            throw new EventNotFoundException();
        }
        if(this_event.hasUser(user.getUserName()) || this_event.isFull()){
            return false;
        }
        es.signUserUp(user, this_event);
        return true;
    }

    /**
     * removes given user from the corresponding event of event_id
     *
     * @param user                      User that wants to leave the event
     * @param event_id                  id of event to be left
     * @return                          true iff user was removed from event
     * @throws EventNotFoundException   thrown when given event id does not exist
     */
    public boolean removeUser(User user, Long event_id) throws EventNotFoundException{
        //HashMap<Long, Event> nameToEventDict = em.getEventsMap(); //never used

        Event this_event = em.getEvent(event_id);
        if(this_event == null){
            throw new EventNotFoundException();
        }
        if(!this_event.hasUser(user.getUserName())){
            return false;
        }
        es.removeUser(user, this_event);
        return true;
    }
}