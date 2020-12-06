package EventSystem;

import UserSystem.Registrar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

/**
 * A presenter for the event class. Consists of a method that prompts an event creation
 * and tells the user whether the event was successfully created.
 *
 * @author Andy Wu
 * @author Nithilan Manimaran
 */
public class EventCreatorPresenter {
    private EventManager em;
    private Registrar reg;

    /**
     * The constructor for EventCreatorPresenter
     *
     * @param em        The EventManager for EventCreatorPresenter
     * @param reg       The Registrar for EventCreatorPresenter
     */
    public EventCreatorPresenter(EventManager em, Registrar reg) {
        this.em = em;
        this.reg = reg;
    }

    /**
     * Prompts the creation of the specified event and returns whether the event was successfully created
     *
     * @param name           The name of the event
     * @param room           The room of the event
     * @param time           The starting time of the event
     * @param duration       The duration of the event in minutes
     * @param speaker_list   The list of speakers for the event
     * @param capacity       The capacity of the event
     * @return               A string detailing whether the event was successfully created
     */
    public String promptEventCreation(String name, String room, LocalDateTime time, long duration, ArrayList<String> speaker_list, int capacity) {

        EventCreator ec = new EventCreator(this.em, reg);
        try {
            if (!(ec.createEvent(name, room, time, duration, speaker_list, capacity))) {
                return ("Unable to create event");
            } else {
                return ("Event created");
            }
        } catch (EventCreationFailureException e) {
//            e.printStackTrace();
//            return ("");
            return e.getMessage();
        }
    }

    /**
     * Prompts the creation of the specified event and returns whether the event was successfully created
     *
     * @param eventId           The id of the event
     * @return               A string detailing whether the event was successfully deleted
     */
    public String promptEventDeletion(Long eventId) {
        EventCreator ec = new EventCreator(this.em, reg);
        try {
            ec.deleteEvent(eventId);
            return "Event has been successfully cancelled";
        } catch (EventNotFoundException e) {
//            e.printStackTrace();
//            return ("");
            return e.getMessage();
        }
    }

    public void viewEvents(){
        System.out.println("\nEXISTING EVENTS:");
        System.out.println("------------------------");
        for(Event ev: this.em.getEventsList()){
            if(!ev.isFull()) {
                System.out.println("Name: " + ev.getName());
                System.out.println("id: " + ev.getId());
                System.out.println("Time: " + DateTimeFormatter.ofLocalizedDateTime(
                        FormatStyle.SHORT)
                        .format(ev.getTime()));
                System.out.println("Type: " + ev.getType());
                System.out.println("Room: " + ev.getRoom());
                System.out.println("Capacity: " + ev.getNumberOfSignedUpUsers() + "/" + ev.getCapacity());
                System.out.println("Speakers: " + ev.getSpeakerList());
                System.out.println("------------------------");
            }
        }
    }
    public interface EventCreatorInterface {

    }
}
