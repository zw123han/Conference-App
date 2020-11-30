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
     * Constructor for Chatroom, creates a new empty ArrayList.
     */
    public Chatroom(){
        this.history = new ArrayList<Message>();
    }

    /**
     * Method for adding a message to history.
     *
     * @param message    Message that was sent.
     */
    public void sendMessage(Message message){
        history.add(message);
    }

    /**
     * Method for removing a message from history.
     *
     * @param position   the index of the message that is to be deleted
     */
    public void deleteMessage(int position){
        history.remove(position);
    }

    /**
     * Returns the indices in history as an ArrayList.
     *
     * @return          ArrayList of indices in history
     */
    public ArrayList<Integer> getMessagePositions() {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        for(int i = 0;i < history.size(); i++){
            positions.add(i);
        }
        return positions;
    }

    /**
     * Returns the message at the index.
     *
     * @param position  The index of the message
     * @return          Message corresponding with the key
     */
    public String getMessage(Integer position) {
        return history.get(position).getMessage();
    }

    /**
     * Returns the date the message at the index.
     *
     * @param position  The position of the message
     * @return          Date the message corresponding with the key was sent
     */
    public String getDate(Integer position) {
        return history.get(position).getDate();
    }

    /**
     * Returns the sender of the message at the index.
     *
     * @param position  The position of the message
     * @return          The sender of the message corresponding with the key
     */
    public String getSender(Integer position) {
        return history.get(position).getSender();
    }

    /**
     * Returns the number of messages in history.
     *
     * @return    number of elements of history
     */
    public int getSize(){
        return history.size();
    }

    public boolean getRead(Integer position) {
        return history.get(position).getRead();
    }

    public void setRead(Integer position, boolean setter) {
        history.get(position).setRead(setter);
    }


}
