import java.util.*;
import java.io.Serializable;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class Chatroom implements Serializable {
    private ArrayList<Message> history;

    public Chatroom(){
        this.history = new ArrayList<>();
    }

    public void sendMessage(Message message){
        history.add(message);
    }

    public ArrayList<Message> getHistory(){
        return history;
    }
}
