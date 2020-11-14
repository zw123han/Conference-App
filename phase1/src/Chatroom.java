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
    private HashMap<Integer, Message> history;
    private Integer key = 0;

    /**
     * (please describe)
     */
    public Chatroom(){
        this.history = new HashMap<>();
    }

    /**
     * (please describe)
     *
     * @param message
     */
    public void sendMessage(Message message){
        history.put(key, message);
        key += 1;
    }

    /**
     * (please describe)
     *
     * @return
     */
    public ArrayList<Integer> getMessageKeys() {
        ArrayList<Integer> keys = new ArrayList<>(history.keySet());
        Collections.sort(keys);
        return keys;
    }

    public String getMessage(Integer key) {
        return history.get(key).getMessage();
    }

    public String getDate(Integer key) {
        return history.get(key).getDate();
    }

    public String getSender(Integer key) {
        return history.get(key).getSender();
    }

}
