import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class EventSignupController {
    private EventManager em;
    private EventSignup es;

    public EventSignupController(EventSignup es, EventManager em){
        this.es = es;
        this.em = em;
    }

    public boolean signUserUp(User user, String event_name){
        HashMap<String, Event> nameToEventDict = em.getEventsMap();
        Event this_event = nameToEventDict.get(event_name);
        if(this_event.hasUser(user.getUserName()) || !(this_event.numberOfSignedUpUsers() < 2)){
            return false;
        }
        es.signUserUp(user, this_event);
        return true;
    }

    public boolean removeUser(User user, String event_name){
        HashMap<String, Event> nameToEventDict = em.getEventsMap();
        Event this_event = nameToEventDict.get(event_name);
        if(!this_event.hasUser(user.getUserName())){
            return false;
        }
        es.removeUser(user, this_event);
        return true;
    }
}