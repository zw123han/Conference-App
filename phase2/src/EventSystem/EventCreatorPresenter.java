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
    private EventCreatorInterface eci;
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
     * @return               A string detailing whether the event was successfully created
     */
    public void promptEventCreation(String name, String room, LocalDateTime time, long duration, ArrayList<String> speaker_list, int capacity) {

        EventCreator ec = new EventCreator(this.em, reg);
        try {
            if (!(ec.createEvent(name, room, time, duration, speaker_list, capacity))) {
//                return ("Unable to create event");
                String message = "Unable to create event";
                eci.createPopUp(message);

            } else {
//                return ("Event created");
                String message = "Event created";
                eci.createPopUp(message);
            }
        } catch (EventCreationFailureException e) {
//            e.printStackTrace();
//            return ("");
//            return e.getMessage();
            eci.createPopUp(e.getMessage());
        }
    }

    /**
     * Prompts the creation of the specified event and returns whether the event was successfully created
     *
     * @param eventId           The id of the event
     * @return               A string detailing whether the event was successfully deleted
     */
    public void  promptEventDeletion(Long eventId) {
        EventCreator ec = new EventCreator(this.em, reg);
        try {
            ec.deleteEvent(eventId);
            String message = "Event has been successfully cancelled";
            eci.createPopUp(message);
//            return "Event has been successfully cancelled";
        } catch (EventNotFoundException e) {
//            e.printStackTrace();
//            return ("");
//            return e.getMessage();
            eci.createPopUp(e.getMessage());
        }
    }

    public void viewEvents() {
//        System.out.println("\nEXISTING EVENTS:");
//        System.out.println("------------------------");
//        for(Event ev: this.em.getEventsList()){
//            if(!ev.isFull()) {
//                System.out.println("Name: " + ev.getName());
//                System.out.println("id: " + ev.getId());
//                System.out.println("Time: " + DateTimeFormatter.ofLocalizedDateTime(
//                        FormatStyle.SHORT)
//                        .format(ev.getTime()));
//                System.out.println("Type: " + ev.getType());
//                System.out.println("Room: " + ev.getRoom());
//                System.out.println("Capacity: " + ev.getNumberOfSignedUpUsers() + "/" + ev.getCapacity());
//                System.out.println("Speakers: " + ev.getSpeakerList());
//                System.out.println("------------------------");
//            }
//        }
        for(Event ev: this.em.getEventsList()){
            if(!ev.isFull()) {
                String name = "Name: " + ev.getName();
                String id = "id: " + ev.getId();
                String time = "Time: " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(ev.getTime());
                String duration = "Duration: " + ev.getDuration() + " minutes";
                String room = "Room: " + ev.getRoom();
                String capacity = "Capacity: " + ev.getNumberOfSignedUpUsers() + "/" + ev.getCapacity();
                String speakers = "Speakers: " + ev.getSpeakerList();
                eci.loadAllEvents(name,id, time, duration, room, capacity, speakers);
            }
        }
    }

    public interface EventCreatorInterface {
        public void loadAllEvents(String name,String id, String time, String duration, String room, String capacity, String speakers);
        public void createPopUp(String message);
    }
}
