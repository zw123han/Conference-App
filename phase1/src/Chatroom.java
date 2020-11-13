import java.util.*;
import java.io.Serializable;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 * @serial
 */
public class Chatroom implements Serializable {
    private ArrayList<Message> history;

    /**
     * (please describe)
     */
    public Chatroom(){
        this.history = new ArrayList<>();
    }

    /**
     * (please describe)
     *
     * @param message
     */
    public void sendMessage(Message message){
        history.add(message);
    }

    /**
     * (please describe)
     *
     * @return
     */
    public ArrayList<Message> getHistory(){
        return history;
    }
}
