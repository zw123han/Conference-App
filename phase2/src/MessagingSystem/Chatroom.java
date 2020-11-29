package MessagingSystem;

import java.lang.reflect.Array;
import java.util.*;
import java.io.Serializable;

/**
 * Chatroom that stores message history and has methods for sending messages.
 *
 * @author Elliot, Chrisee
 */
public class Chatroom implements Serializable {
    private ArrayList<Message> history;

    /**
     * Constructor for Chatroom, creates a new empty HashMap.
     */
    public Chatroom(){
        this.history = new ArrayList<Message>();
    }

    /**
     * Method for adding a message to history.
     *
     * @param message   Message that was sent.
     */
    public void sendMessage(Message message){
        history.add(message);
    }

    public void deleteMessage(int position){
        history.remove(position);
    }

    /**
     * Returns keys of Hashmap as an ArrayList.
     *
     * @return keys     ArrayList of keys
     */
    public ArrayList<Integer> getMessagePositions() {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        for(int i = 0;i < history.size(); i++){
            positions.add(i);
        }
        return positions;
    }

    /**
     * Returns the message corresponding with the key.
     *
     * @param position  The position of the message
     * @return       Message corresponding with the key
     */
    public String getMessage(Integer position) {
        return history.get(position).getMessage();
    }

    /**
     * Returns the date the message corresponding with the key was sent.
     *
     * @param position  The position of the message
     * @return      Date the message corresponding with the key was sent
     */
    public String getDate(Integer position) {
        return history.get(position).getDate();
    }

    /**
     * Returns the sender of the message corresponding with the key
     *
     * @param position  The position of the message
     * @return     The sender of the message corresponding with the key
     */
    public String getSender(Integer position) {
        return history.get(position).getSender();
    }

    public int getSize(){
        return history.size();
    }

}
