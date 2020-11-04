import java.util.*;
import java.time.*;

// Choosing not to store event manager in here for now
public class EventSignup{


    public void signUserUp(User user, Event event){
        event.addUser(user.getUserName());
    }

    public void removeUser(User user, Event event){
        event.removeUser(user.getUserName());
    }

}
