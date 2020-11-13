import java.io.Serializable;
import java.util.ArrayList;
import java.time.*;
import java.util.Date;
import java.util.Calendar;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
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
     * (please describe)
     *
     * @param name      (please describe)
     * @param room      (please describe)
     * @param time      (please describe)
     * @param speaker   (please describe)
     * @param capacity  (please describe)
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
     * (please describe)
     *
     * @return          (please describe)
     */
    public long getId() { return id; }

    /**
     * (please describe)
     *
     * @return          (please describe)
     */
    public String getName(){
        return name;
    }

    /**
     * (please describe)
     *
     * @return          (please describe)
     */
    public String getRoom(){
        return room;
    }

    /**
     * (please describe)
     *
     * @return          (please describe)
     */
    public LocalDateTime getTime(){
        return time;
    }

    /**
     * (please describe)
     *
     * @return          (please describe)
     */
    public String getSpeaker(){
        return speaker;
    }

    /**
     * (please describe)
     *
     * @return          (please describe)
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * (please describe)
     *
     * @param username  (please describe)
     * @return          (please describe)
     */
    public boolean addUser(String username){
        if (signedUpUsers.contains(username)){
            return false;
        }
        signedUpUsers.add(username);
        return true;
    }

    /**
     * (please describe)
     *
     * @param username  (please describe)
     * @return          (please describe)
     */
    public boolean removeUser(String username){
        if (signedUpUsers.contains(username)){
            signedUpUsers.remove(username);
            return true;
        }
        return false;
    }

    /**
     * (please describe)
     *
     * @return          (please describe)
     */
    public boolean isFull(){
        return this.capacity == this.signedUpUsers.size();
    }

    /**
     * (please describe)
     *
     * @param username  (please describe)
     * @return          (please describe)
     */
    public boolean hasUser(String username){
        return this.signedUpUsers.contains(username);
    }

    /**
     * (please describe)
     *
     * @return          (please describe)
     */
    public int getNumberOfSignedUpUsers() {
        return this.signedUpUsers.size();
    }

    /**
     * (please describe)
     *
     * @return          (please describe)
     */
    public ArrayList<String> getSignedUpUsers(){
        return signedUpUsers;
    }

}