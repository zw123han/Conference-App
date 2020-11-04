import java.util.ArrayList;
import java.time.*;

public class Event {

    private String name;
    private String room;
    private LocalDateTime time;
    private Speaker speaker;
    private ArrayList<String> signedUpUsers = new ArrayList<String>();

    public Event(String name, String room, LocalDateTime time, Speaker speaker){
        this.name = name;
        this.room = room;
        this.time = time;
        this.speaker = speaker;
    }

    public String getName(){
        return name;
    }

    public String getRoom(){
        return room;
    }

    public LocalDateTime getTime(){
        return time;
    }

    public Speaker getSpeaker(){
        return speaker;
    }

    public boolean addUser(String user){
        if (signedUpUsers.contains(user)){
            return false;
        }
        signedUpUsers.add(user);
        return true;
    }



}