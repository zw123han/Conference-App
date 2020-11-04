import java.util.*;
import java.time.*;
public class EventManager{

    private ArrayList<Event> events;

    public EventManager(ArrayList<Event> events){
        this.events = events;
    }

    public EventManager(){
        this.events = new ArrayList<Event>();
    }

    public boolean createEvent(String name, String room, LocalDateTime time, Speaker speaker){
        for (event : this.events){
            if ((event.getRoom() == room && event.getTime() == time) ||
                    (event.getTime() == time && event.getSpeaker().getUserName() == speaker.getUserName())) {
                return false
            }
        }
        Event ev = new Event(name, room, time, speaker);
        this.events.add(ev);
        return true
    }
}