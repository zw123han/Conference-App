package EventSystem;

import UserSystem.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

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
     * @param name      The name of the event
     * @param room      The room of the event
     * @param time      The starting time of the event
     * @param speaker   The speaker for the event
     * @param capacity  The capacity of the event
     * @return          A string detailing whether the event was successfully created
     */
    public String promptEventCreation(String name, String room, LocalDateTime time, long duration, String speaker, int capacity) {

        EventCreator ec = new EventCreator(this.em, reg);
        try {
            if (!(ec.createEvent(name, room, time, duration, speaker, capacity))) {
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
                System.out.println("Room: " + ev.getRoom());
                System.out.println("Capacity: " + ev.getNumberOfSignedUpUsers() + "/" + ev.getCapacity());
                System.out.println("Speaker: " + ev.getSpeaker());
                System.out.println("------------------------");
            }
        }
    }
}
