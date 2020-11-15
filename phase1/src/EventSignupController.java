import java.util.HashMap;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class EventSignupController {
    private EventManager em;
    private EventSignup es;

    /**
     * (please describe)
     *
     * @param es        (please describe)
     * @param em        (please describe)
     */
    public EventSignupController(EventSignup es, EventManager em){
        this.es = es;
        this.em = em;
    }

    /**
     * (please describe)
     *
     * @param user                      (please describe)
     * @param event_id                  (please describe)
     * @return                          (please describe)
     * @throws EventNotFoundException   (please describe)
     */
    public boolean signUserUp(User user, Long event_id) throws EventNotFoundException{
        //HashMap<Long, Event> nameToEventDict = em.getEventsMap();
        Event this_event = em.getEvent(event_id);
        if(this_event == null){
            throw new EventNotFoundException();
        }
        if(this_event.hasUser(user.getUserName()) || !(this_event.getNumberOfSignedUpUsers() < this_event.getCapacity())){
            return false;
        }
        es.signUserUp(user, this_event);
        return true;
    }

    /**
     * (please describe)
     *
     * @param user                      (please describe)
     * @param event_id                  (please describe)
     * @return                          (please describe)
     * @throws EventNotFoundException   (please describe)
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