package EventSystem;

import UserSystem.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

/**
 * Presenter for the event signup process.
 *
 * @author Andy, Nithilan
 */
public class EventSignupPresenter {
    private EventManager em;
    private EventSignup es;
    private EventInterface eventInterface;
    /**
     * Creates an instance of EvenSignupPresenter
     *
     * @param es        instance of EventSignup to be used for the program
     * @param em        instance of EventMangaer used by the program
     */
    public EventSignupPresenter(EventSignup es, EventManager em) {
        this.es = es;
        this.em = em;
    }

    public void setInterface(EventInterface eventInterface) {
        this.eventInterface = eventInterface;
    }
    /**
     * prints response after trying to signup given user for the event corresponding to event id.
     *
     * @param user          User to join event
     * @param event_id      event id of event to be joined
     */
    public void joinEvent(User user, String event_id) {
        EventSignupController esc = new EventSignupController(this.es, this.em);
        try {
            if (!(esc.signUserUp(user, Long.parseLong(event_id)))) {
                String message = "Unable to join event";
                eventInterface.joinEvent(message);
            } else {
                String message = "Joined event";
                eventInterface.joinEvent(message);
            }
        } catch (EventNotFoundException e) {
            String message = "This event has not yet been registered.";
            eventInterface.joinEvent(message);
        } catch (NumberFormatException e){
            String message = "Please enter a valid event ID";
            eventInterface.joinEvent(message);
        }
    }

    /**
     * Prints responses after trying to remove user from event
     *
     * @param user          user to be removed
     * @param event_id      event id of event that user wants to leave
     */
    public void leaveEvent(User user, String event_id) {
        EventSignupController esc = new EventSignupController(this.es, this.em);
        try {
            if (!(esc.removeUser(user, Long.parseLong(event_id)))) {
                String message = "Unable to leave event. You are already in this event.";
                eventInterface.leaveEvent(message);
            } else {
                String message = "Left event";
                eventInterface.leaveEvent(message);
            }
        } catch (EventNotFoundException e) {
            String message = "This event has not yet been registered.";
            eventInterface.leaveEvent(message);
        } catch (NumberFormatException e) {
            String message = "Please enter a valid event ID";
            eventInterface.leaveEvent(message);
        }
    }

    /**
     * Lists all the events available in the program for user to signup to
     */
    public void viewEvents(){
//        System.out.println("\nAVAILABLE EVENTS:");
//        System.out.println("------------------------");
//        for(Event ev: this.em.getEventsList()){
//            if(!ev.isFull()) {
//                System.out.println("Name: " + ev.getName());
//                System.out.println("id: " + ev.getId());
//                System.out.println("Time: " + DateTimeFormatter.ofLocalizedDateTime(
//                        FormatStyle.SHORT)
//                        .format(ev.getTime()));
//                System.out.println("Room: " + ev.getRoom());
//                System.out.println("Capacity: " + ev.getNumberOfSignedUpUsers() + "/" + ev.getCapacity());
//                System.out.println("Speakers: " + ev.getSpeakerList());
//                System.out.println("------------------------");
//            }
//        }
        System.out.println("viewEvents");
        for(Event ev: this.em.getEventsList()){
            if(!ev.isFull()) {
                String name = "Name: " + ev.getName();
                String id = "id: " + ev.getId();
                String time = "Time: " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(ev.getTime());
                String room = "Room: " + ev.getRoom();
                String capacity = "Capacity: " + ev.getNumberOfSignedUpUsers() + "/" + ev.getCapacity();
                String speakers = "Speakers: " + ev.getSpeakerList();
                eventInterface.loadAllEvents(name,id, time, room, capacity, speakers);
            }
        }
    }

    /**
     * prints all events that user is attending
     *
     * @param user      User that is logged in
     */
    public void usersEvents(User user) {
//        System.out.println("\nYOUR EVENTS:");
//        System.out.println("------------------------");
//        for (Long event_long: user.getEvents())  {
//            Event ev = em.getEvent(event_long);
//            System.out.println("Name: " + ev.getName());
//            System.out.println("id: " + ev.getId());
//            System.out.println("Time: " + DateTimeFormatter.ofLocalizedDateTime(
//                    FormatStyle.SHORT)
//                    .format(ev.getTime()));
//            System.out.println("Room: " + ev.getRoom());
//            System.out.println("Capacity: " + ev.getNumberOfSignedUpUsers() + "/" + ev.getCapacity());
//            System.out.println("Speakers: " + ev.getSpeakerList());
//            System.out.println("------------------------");
//        }
        for (Long event_long: user.getEvents())  {
            Event ev = em.getEvent(event_long);
            String name = "Name: " + ev.getName();
            String id = "id: " + ev.getId();
            String time = "Time: " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(ev.getTime());
            String room = "Room: " + ev.getRoom();
            String capacity = "Capacity: " + ev.getNumberOfSignedUpUsers() + "/" + ev.getCapacity();
            String speakers = "Speakers: " + ev.getSpeakerList();
            eventInterface.loadUserEvents(name,id, time, room, capacity, speakers);
        }
    }
    /**
     * Prints out a list of usernames registered in an event.
     *
     * @param id      The event_id of the event.
     */
    public void getEventInfo(String id){
        try{
//            System.out.println("\n PARTICIPANTS:");
            ArrayList<String> list = new ArrayList<>();
            for (String username: em.getSignedUpUsers(Long.parseLong(id))){
//                System.out.println("@"+username);
                list.add("@" + username);

            }
            eventInterface.eventInfo(list);
        }
        catch(NumberFormatException e){
            eventInterface.eventInfoError("Please enter a valid event ID");
//            System.out.println("Please enter a valid event ID");
        }
        catch(EventNotFoundException e){
            eventInterface.eventInfoError("That event has not yet been registered");
//            System.out.println("That event has not yet been registered");
        }
    }
    public interface EventInterface {
        void loadUserEvents(String name, String id, String time, String room, String capacity, String speakers);
        void loadAllEvents(String name, String id, String time, String room, String capacity, String speakers);
        void joinEvent(String message);
        void leaveEvent(String message);
        void eventInfo(ArrayList<String> usernames);
        void eventInfoError(String message);
    }
}