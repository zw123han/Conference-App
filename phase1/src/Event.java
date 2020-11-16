import java.io.Serializable;
import java.util.ArrayList;
import java.time.*;
import java.util.Date;
import java.util.Calendar;

/**
 * A class representing the event entity. Consists of methods that operate on events / event details.
 *
 * @author Andy Wu
 * @author Nithilan Manimaran
 * @version %I%, %G%
 * @serial
 */
public class Event implements Serializable {

    private String name;
    private String room;
    private LocalDateTime time;
    private String speaker;
    private int capacity;
    private ArrayList<String> signedUpUsers = new ArrayList<String>();
    private long id;

    /**
     * The constructor for an Event
     *
     * @param name      The name of the event
     * @param room      The room the event is being held in
     * @param time      The starting time of the event
     * @param speaker   The speaker for the event
     * @param capacity  The capacity of the event
     */
    public Event(String name, String room, LocalDateTime time, String speaker, int capacity){
        this.name = name;
        this.room = room;
        this.time = time;
        this.speaker = speaker;
        this.capacity = 2;

        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        this.id = d.getTime();
    }

    /**
     * Returns the id of the event
     *
     * @return          The id of the event as a long
     */
    public long getId() { return id; }

    /**
     * Returns the name of the event
     *
     * @return          The name of the event as a String
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the room of the event
     *
     * @return          The room of the event as a String
     */
    public String getRoom(){
        return room;
    }

    /**
     * Returns the starting time of the event
     *
     * @return          The starting time of the event as a LocalDateTime
     */
    public LocalDateTime getTime(){
        return time;
    }

    /**
     * Returns the speaker of the event
     *
     * @return          The speaker of the event as a String
     */
    public String getSpeaker(){
        return speaker;
    }

    /**
     * Returns the capacity of the event
     *
     * @return          The capacity of the event as an int
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * Signs up the given user for the event
     *
     * @param username  The username of the user signing up for the event
     * @return          true if the user was successfully signed up for the event, false otherwise
     */
    public boolean addUser(String username){
        if (signedUpUsers.contains(username)){
            return false;
        }
        signedUpUsers.add(username);
        return true;
    }

    /**
     * Removes a given user from the event
     *
     * @param username  The username of the user to be removed from the event
     * @return          true if the user was successfully removed from the event, false otherwise
     */
    public boolean removeUser(String username){
        if (signedUpUsers.contains(username)){
            signedUpUsers.remove(username);
            return true;
        }
        return false;
    }

    /**
     * Checks if the event is full
     *
     * @return          true if the event is full, false otherwise
     */
    public boolean isFull(){
        return this.capacity == this.signedUpUsers.size();
    }

    /**
     * Checks if the given user is signed up for the event
     *
     * @param username  The username of the user being checked for
     * @return          true if the given user is signed up for the event, false otherwise
     */
    public boolean hasUser(String username){
        return this.signedUpUsers.contains(username);
    }

    /**
     * Returns the number of users signed up for the event
     *
     * @return          The number of users signed up for the event
     */
    public int getNumberOfSignedUpUsers() {
        return this.signedUpUsers.size();
    }

    /**
     * Returns a list of all the users signed up for the event
     *
     * @return          An Arraylist of Strings representing the usernames of the users signed up for the event
     */
    public ArrayList<String> getSignedUpUsers(){
        return signedUpUsers;
    }

}