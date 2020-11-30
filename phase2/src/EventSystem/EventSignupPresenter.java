package EventSystem;

import UserSystem.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Presenter for the event signup process.
 *
 * @author Andy, Nithilan
 */
public class EventSignupPresenter {
    private EventManager em;
    private EventSignup es;

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
                System.out.println("Unable to join event");
            } else {
                System.out.println("Joined event");
            }
        } catch (EventNotFoundException e) {
            System.out.println("This event has not yet been registered.");
        } catch (NumberFormatException e){
            System.out.println("Please enter a valid event ID");
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
                System.out.println("Unable to leave event. You are already in this event.");
            } else {
                System.out.println("Left event.");
            }
        } catch (EventNotFoundException e) {
            System.out.println("This event has not yet been registered.");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid event ID");
        }
    }

    /**
     * Lists all the events available in the program for user to signup to
     */
    public void viewEvents(){
        System.out.println("\nAVAILABLE EVENTS:");
        System.out.println("------------------------");
        for(Event ev: this.em.getEventsList()){
            if(!ev.isFull()) {
                System.out.println("Name: " + ev.getName());
                System.out.println("id: " + ev.getId());
                System.out.println("Time: " + DateTimeFormatter.ofLocalizedDateTime(
                        FormatStyle.SHORT)
                        .format(ev.getTime()));
                System.out.println("Room: " + ev.getRoom());
                System.out.println("Capacity: " + ev.getNumberOfSignedUpUsers() + "/" + ev.getCapacity());
                System.out.println("Speakers: " + ev.getSpeakerList());
                System.out.println("------------------------");
            }
        }
    }

    /**
     * prints all events that user is attending
     *
     * @param user      User that is logged in
     */
    public void usersEvents(User user) {
        System.out.println("\nYOUR EVENTS:");
        System.out.println("------------------------");
        for (Long event_long: user.getEvents())  {
            Event ev = em.getEvent(event_long);
            System.out.println("Name: " + ev.getName());
            System.out.println("id: " + ev.getId());
            System.out.println("Time: " + DateTimeFormatter.ofLocalizedDateTime(
                    FormatStyle.SHORT)
                    .format(ev.getTime()));
            System.out.println("Room: " + ev.getRoom());
            System.out.println("Capacity: " + ev.getNumberOfSignedUpUsers() + "/" + ev.getCapacity());
            System.out.println("Speakers: " + ev.getSpeakerList());
            System.out.println("------------------------");
        }
    }
    /**
     * Prints out a list of usernames registered in an event.
     *
     * @param id      The event_id of the event.
     */
    public void getEventInfo(String id){
        try{

            System.out.println("\n PARTICIPANTS:");
            for (String username: em.getSignedUpUsers(Long.parseLong(id))){
                System.out.println("@"+username);
            }
        }
        catch(NumberFormatException e){
            System.out.println("Please enter a valid event ID");
        }
        catch(EventNotFoundException e){
            System.out.println("That event has not yet been registered");
        }
    }
}