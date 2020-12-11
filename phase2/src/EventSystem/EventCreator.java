package EventSystem;

import RoomSystem.RoomManager;
import UserSystem.Registrar;
import UserSystem.Speaker;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A controller for the event class. Consists of a method that creates an event if the event is allowed.
 *
 * @author Andy Wu
 * @author Nithilan Manimaran
 */
public class EventCreator {
    private EventManager em;
    private Registrar reg;
    private RoomManager rm;

    /**
     * The constructor for EventCreator
     *
     * @param em  The EventManager for the EventCreator
     * @param reg The Registrar for the EventCreator
     */
    public EventCreator(EventManager em, Registrar reg, RoomManager rm) {
        this.em = em;
        this.reg = reg;
        this.rm = rm;
    }

    /**
     * Creates the specified event unless either the speaker, the room, or both are already booked for the event time
     *
     * @param name         The name of the event
     * @param room         The room of the event
     * @param start_time   The starting time of the event
     * @param speaker_list The list of speakers for the event
     * @param capacity     The capacity of the event
     * @param duration     The duration of the event in minutes
     * @return true if the event was successfully created
     * @throws EventCreationFailureException If the event failed to be created
     */
    public boolean createEvent(String name, String room, LocalDateTime start_time, long duration, ArrayList<String> speaker_list, int capacity)
            throws EventCreationFailureException {
        if (!this.rm.roomExists(room)){
            throw new EventCreationFailureException("the given room does not exist");
        } else if (this.rm.getRoomCapacity(room) < capacity){
            throw new EventCreationFailureException("the given room does not have the required capacity");
        }
        ArrayList<Event> events = this.em.getEventsList();
        for (Event event : events) {
            LocalDateTime lower = event.getTime();
            LocalDateTime upper = lower.plusMinutes(event.getDuration());
            LocalDateTime end_time = start_time.plusMinutes(duration);
            if ((start_time.isAfter(lower) && start_time.isBefore(upper)) ||
                    (end_time.isAfter(lower) && end_time.isBefore(upper)) || (start_time.isEqual(lower))) {
                if ((event.getRoom().equals(room))) {
                    throw new EventCreationFailureException("This room is taken for this time");
                }
                for (String s : event.getSpeakerList()) {
                    if (speaker_list.contains(s)) {
                        throw new EventCreationFailureException(s + " is unavailable for this time");
                    }
                }
            }
        }
        ArrayList<Speaker> speakerObjectList = new ArrayList<>();
        for (String s : speaker_list) {
            speakerObjectList.add((Speaker) reg.getUserByUserName(s));
        }
        this.em.createEvent(name, room, start_time, duration, speaker_list, capacity, speakerObjectList);
        return true;
    }

    /**
     * Attempts to delete the event with the given id
     *
     * @param eventId                   The identifier of the event
     * @return                          true iff the event was deleted
     * @throws EventNotFoundException   The exception thrown if the event is not found
     */
    public boolean deleteEvent(Long eventId)
            throws EventNotFoundException {
        if (!this.em.hasEvent(eventId)) {
            throw new EventNotFoundException();
        }
        Event ev = this.em.getEvent(eventId);
        for (String u : ev.getSignedUpUsers()) {
            this.reg.getUserByUserName(u).removeEvent(eventId);
        }
        for (String s : ev.getSpeakerList()) {
            Speaker speaker = (Speaker) this.reg.getUserByUserName(s);
            speaker.removeEvent(eventId);
        }
        this.em.deleteEvent(eventId);
        reg.deleteEventFromSpeakers(eventId);
        return true;
    }

    /**
     * Attempts to change the name of an event.
     *
     * @param eventId The event id of the event.
     * @param name The new name of the event.
     * @return True if and only if the event name is changed to name.
     */
    public boolean setName(Long eventId, String name) {
        this.em.setName(eventId, name);
        return true;
    }

    /**
     * Attempts to set the new event capacity of an event.
     *
     * @param eventId The event id of this event.
     * @param capacity The new capacity of the event.
     * @return True if and only if the event capacity is set to the given parameter.
     * @throws EventNotFoundException If no such event exists.
     * @throws EventModificationFailureException If the capacity cannot be changed.
     */
    public boolean setCapacity(Long eventId, int capacity) throws EventNotFoundException, EventModificationFailureException {
        int numAttendees = this.em.getSignedUpUsers(eventId).size();
        if (numAttendees > capacity){
            throw new EventModificationFailureException("The new capacity can not hold all the currently signed up users");
        } else if (this.rm.getRoomCapacity(this.em.getRoom(eventId)) < capacity){
            throw new EventModificationFailureException("The room for this event cannot hold the new given capacity");
        }
        this.em.setCapacity(eventId, capacity);
        return true;
    }
}