import java.util.*;
import java.io.Serializable;

/**
 * Chatroom that stores message history and has methods for sending messages.
 *
 * @author Elliot, Chrisee
 */
public class Chatroom implements Serializable {
    private HashMap<Integer, Message> history;
    private Integer key = 0;

    /**
     * Constructor for Chatroom, creates a new empty HashMap.
     */
    public Chatroom(){
        this.history = new HashMap<>();
    }

    /**
     * Method for adding a message to history.
     *
     * @param message   Message that was sent.
     */
    public void sendMessage(Message message){
        history.put(key, message);
        key += 1;
    }

    public void deleteMessage(int key){
        for(int i = key;i < history.size() - 1;i++){
            history.replace(i, history.get(i + 1));
        }
        history.remove(history.size() - 1);
        this.key--;
    }

    /**
     * Returns keys of Hashmap as an ArrayList.
     *
     * @return keys     ArrayList of keys
     */
    public ArrayList<Integer> getMessageKeys() {
        ArrayList<Integer> keys = new ArrayList<>(history.keySet());
        Collections.sort(keys);
        return keys;
    }

    /**
     * Returns the message corresponding with the key.
     *
     * @param key    The key
     * @return       Message corresponding with the key
     */
    public String getMessage(Integer key) {
        return history.get(key).getMessage();
    }

    /**
     * Returns the date the message corresponding with the key was sent.
     *
     * @param key   The key
     * @return      Date the message corresponding with the key was sent
     */
    public String getDate(Integer key) {
        return history.get(key).getDate();
    }

    /**
     * Returns the sender of the message corresponding with the key
     *
     * @param key  The key
     * @return     The sender of the message corresponding with the key
     */
    public String getSender(Integer key) {
        return history.get(key).getSender();
    }

    public int getSize(){
        return history.size();
    }

}
