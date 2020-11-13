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

    public long getId() { return id; }

    public String getName(){
        return name;
    }

    public String getRoom(){
        return room;
    }

    public LocalDateTime getTime(){
        return time;
    }

    public String getSpeaker(){
        return speaker;
    }

    public int getCapacity(){
        return capacity;
    }

    public boolean addUser(String username){
        if (signedUpUsers.contains(username)){
            return false;
        }
        signedUpUsers.add(username);
        return true;
    }

    public boolean removeUser(String username){
        if (signedUpUsers.contains(username)){
            signedUpUsers.remove(username);
            return true;
        }
        return false;
    }

    public boolean isFull(){
        return this.capacity == this.signedUpUsers.size();
    }


    public boolean hasUser(String username){
        return this.signedUpUsers.contains(username);
    }

    public int getNumberOfSignedUpUsers() {
        return this.signedUpUsers.size();
    }

    public ArrayList<String> getSignedUpUsers(){
        return signedUpUsers;
    }

}