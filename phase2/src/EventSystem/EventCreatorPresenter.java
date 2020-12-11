package EventSystem;

import RoomSystem.RoomManager;
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
    private RoomManager rm;
    private EventCreatorInterface eci;

    /**
     * The constructor for EventCreatorPresenter
     *
     * @param em        The EventManager for EventCreatorPresenter
     * @param reg       The Registrar for EventCreatorPresenter
     */
    public EventCreatorPresenter(EventManager em, Registrar reg, RoomManager rm) {
        this.em = em;
        this.reg = reg;
        this.rm = rm;
    }
    public void setInterface(EventCreatorInterface eci) {
        this.eci = eci;
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
     */
    public void promptEventCreation(String name, String room, LocalDateTime time, long duration, ArrayList<String> speaker_list, int capacity) {

        EventCreator ec = new EventCreator(this.em, reg, rm);
        try {
            if (!(ec.createEvent(name, room, time, duration, speaker_list, capacity))) {
                String message = "Unable to create event";
                eci.createPopUp(message);

            } else {
                String message = "Event created";
                eci.createPopUp(message);
            }
        } catch (EventCreationFailureException e) {

            eci.createPopUp(e.getMessage());
        }
    }

    /**
     * Prompts the deletion of the specified event and creates popup of whether the event was successfully deleted
     *
     * @param eventId           The id of the event
     */
    public void  promptEventDeletion(Long eventId) {
        EventCreator ec = new EventCreator(this.em, reg, rm);
        try {
            ec.deleteEvent(eventId);
            String message = "Event has been successfully cancelled";
            eci.createPopUp(message);
        } catch (EventNotFoundException e) {

            eci.createPopUp(e.getMessage());
        }
    }

    /**
     * Prompts the change of name of the specified event and creates popup of whether the modification was successful
     *
     * @param eventId           The id of the event
     */
    public void  promptSetName(Long eventId, String name) {
        EventCreator ec = new EventCreator(this.em, reg, rm);
            ec.setName(eventId, name);
            String message = "Event name has been successfully updated";
            eci.createPopUp(message);
    }

    /**
     * Prompts the change of capacity of the specified event and creates popup of whether the modification was successful
     *
     * @param eventId           The id of the event
     */
    public void  promptSetCapacity(Long eventId, int capacity) {
        EventCreator ec = new EventCreator(this.em, reg, rm);
        try {
            ec.setCapacity(eventId, capacity);
            String message = "Event capacity has been updated";
            eci.createPopUp(message);
        } catch (EventModificationFailureException | EventNotFoundException e) {

            eci.createPopUp(e.getMessage());
        }
    }

    /**
     * views all the events available in this conference
     */
    public void viewEvents() {

        for(Event ev: this.em.getEventsList()){
            if(!ev.isFull()) {
                String name = "Name: " + ev.getName();
                String id = "" + ev.getId();
                String time = "Time: " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(ev.getTime());
                String duration = "Duration: " + ev.getDuration() + " minutes";
                String room = "Room: " + ev.getRoom();
                String capacity = "Capacity: " + ev.getNumberOfSignedUpUsers() + "/" + ev.getCapacity();
                String speakers = "Speakers: " + ev.getSpeakerList();
                eci.loadAllEvents(name,id, time, duration, room, capacity, speakers);
            }
        }
    }

    /**
     * Used by the UI to interface interface with events.
     *
     */
    public interface EventCreatorInterface {
        void loadAllEvents(String name, String id, String time, String duration, String room, String capacity, String speakers);
        void createPopUp(String message);
    }
}
