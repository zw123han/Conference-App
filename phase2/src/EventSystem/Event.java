package EventSystem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A class representing the event entity. Consists of methods that operate on events / event details.
 *
 * @author Andy Wu
 * @author Nithilan Manimaran
 */
public class  Event implements Serializable {

    private String name;
    private String room;
    private LocalDateTime time;
    private long duration;
    private String type;
    private ArrayList<String> speaker_list;
    private int capacity;
    private ArrayList<String> signedUpUsers = new ArrayList<>();
    private long id;

    /**
     * The constructor for an Event
     *
     * @param name           The name of the event
     * @param room           The room the event is being held in
     * @param time           The starting time of the event
     * @param duration       The duration of the event in minutes
     * @param speaker_list   The list of speakers for the event
     * @param capacity       The capacity of the event
     */
    public Event(String name, String room, LocalDateTime time, long duration, ArrayList<String> speaker_list, int capacity){
        this.name = name;
        this.room = room;
        this.time = time;
        this.duration = duration;
        if( speaker_list == null || speaker_list.isEmpty()){
            this.speaker_list = new ArrayList<>();
        } else {
            this.speaker_list = speaker_list;
        }
        if(this.speaker_list.size() == 0){
            this.type = "Party";
        }
        else if(this.speaker_list.size() == 1){
            this.type = "Talk";
        }
        else{
            this.type = "Panel";
        }

        this.capacity = capacity;


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

    public void setId(Long id) { this.id = id; }

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
     * Returns the list of speakers for the event
     *
     * @return          The list of speakers for the event as an arraylist
     */
    public ArrayList<String> getSpeakerList(){
        return speaker_list;
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
     * Returns the duration of the event
     *
     * @return          The duration of the event in minutes
     */
    public long getDuration(){
        return duration;
    }

    /**
     * Returns the type of the event
     *
     * @return          The type of the event as a string
     */
    public String getType() { return type; }

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
     * Checks if the event is empty
     *
     * @return          true if the event is empty and false if otherwise
     */
    public boolean isEmpty(){ return this.signedUpUsers.size() == 0; }

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

    /**
     * Sets the name of this event
     *
     * @param name      name of the event
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the room of this event
     *
     * @param room      room to be set
     */
    public void setRoom(String room){
        this.room = room;
    }

    /**
     * Sets the time and duration of this event
     *
     * @param time      start time of the event
     * @param duration  duration of the event
     */
    public void setTime(LocalDateTime time, long duration){
        this.time = time;
        this.duration = duration;
    }
    /**
     * Adds given speaker to this event
     *
     * @param speaker      username of the speaker
     */
    public void addSpeaker(String speaker){
        this.speaker_list.add(speaker);
        if(speaker_list.size() == 0){
            this.type = "Party";
        }
        else if(speaker_list.size() == 1){
            this.type = "Talk";
        }
        else{
            this.type = "Panel";
        }
    }

    /**
     * removes given speaker to this event
     *
     * @param speaker      username of the speaker
     */
    public void removeSpeaker(String speaker){
        this.speaker_list.remove(speaker);
        if(speaker_list.size() == 0){
            this.type = "Party";
        }
        else if(speaker_list.size() == 1){
            this.type = "Talk";
        }
        else{
            this.type = "Panel";
        }
    }

    /**
     * Sets the time and duration of this event
     *
     * @param capacity  maximum capacity of the event
     */
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    /**
     * returns whether the given speaker is at this event
     *
     * @param speaker      username of the speaker
     * @return             true iff speaker is attending this event
     */
    public boolean hasSpeaker(String speaker){
        return this.speaker_list.contains(speaker);
    }

}