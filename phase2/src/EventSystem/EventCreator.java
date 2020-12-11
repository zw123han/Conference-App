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
     * @param eventId
     * @return
     * @throws EventNotFoundException
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
        return true;
    }

    public boolean setName(Long eventId, String name) {
        this.em.setName(eventId, name);
        return true;
    }

    public boolean setRoom(Long eventId, String room) throws EventModificationFailureException {
        if(!this.rm.roomExists(room)){
            throw new EventModificationFailureException("The given room does not exist");
        }
        ArrayList<Event> events = this.em.getEventsList();
        for (Event event : events) {
            LocalDateTime start_time = this.em.getTime(eventId);
            LocalDateTime lower = event.getTime();
            LocalDateTime upper = lower.plusMinutes(event.getDuration());
            LocalDateTime end_time = start_time.plusMinutes(this.em.getEvent(eventId).getDuration());
            if ((start_time.isAfter(lower) && start_time.isBefore(upper)) ||
                    (end_time.isAfter(lower) && end_time.isBefore(upper)) || (start_time.isEqual(lower))) {
                if ((event.getRoom().equals(room))) {
                    throw new EventModificationFailureException("The room you are attempting to change to is already booked for this time");
                }
            }
        }
        this.em.setRoom(eventId, room);
        return true;
    }

    public boolean setTime(Long eventId, LocalDateTime start_time, long duration) throws EventModificationFailureException {
        ArrayList<Event> events = this.em.getEventsList();
        for (Event event : events) {
            String room = this.em.getRoom(eventId);
            LocalDateTime lower = event.getTime();
            LocalDateTime upper = lower.plusMinutes(event.getDuration());
            LocalDateTime end_time = start_time.plusMinutes(this.em.getEvent(eventId).getDuration());
            if ((start_time.isAfter(lower) && start_time.isBefore(upper)) ||
                    (end_time.isAfter(lower) && end_time.isBefore(upper)) || (start_time.isEqual(lower))) {
                if ((event.getRoom().equals(room))) {
                    throw new EventModificationFailureException("The room you are using is booked for the time you are trying to change to");
                }
            }
        }
        this.em.setTime(eventId, start_time, duration);
        return true;
    }

    public boolean addSpeaker(Long eventId, String speaker) throws EventModificationFailureException {
        ArrayList<String> speaker_list = this.em.getSpeakerList(eventId);
        boolean flag = this.reg.userExisting(speaker);
        if (!flag) {
            throw new EventModificationFailureException("This speaker does not exist");
        }
        ArrayList<Event> events = this.em.getEventsList();
        for (Event event : events) {
            LocalDateTime start_time = this.em.getTime(eventId);
            LocalDateTime lower = event.getTime();
            LocalDateTime upper = lower.plusMinutes(event.getDuration());
            LocalDateTime end_time = start_time.plusMinutes(this.em.getEvent(eventId).getDuration());
            if ((start_time.isAfter(lower) && start_time.isBefore(upper)) ||
                    (end_time.isAfter(lower) && end_time.isBefore(upper)) || (start_time.isEqual(lower))) {
                if (event.getSpeakerList().contains(speaker)) {
                    throw new EventModificationFailureException("The speaker you are trying to add is already booked for this time");
                }
            }
        }
        this.em.addSpeaker(eventId, speaker);
        return true;
    }

    public boolean removeSpeaker(Long eventId, String speaker) throws EventModificationFailureException {
        boolean flag = this.reg.userExisting(speaker);
        if (!flag) {
            throw new EventModificationFailureException("This speaker does not exist");
        }
        ArrayList<String> speaker_list = this.em.getSpeakerList(eventId);
        if (!speaker_list.contains(speaker)) {
            throw new EventModificationFailureException("This speaker is not booked for your event");
        }
        this.em.removeSpeaker(eventId, speaker);
        return true;
    }

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