package EventSystem;

import UserSystem.*;
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

    /**
     * The constructor for EventCreator
     *
     * @param em        The EventManager for the EventCreator
     * @param reg       The Registrar for the EventCreator
     */
    public EventCreator(EventManager em, Registrar reg) {
        this.em = em;
        this.reg = reg;
    }

    /**
     * Creates the specified event unless either the speaker, the room, or both are already booked for the event time
     *
     * @param name                              The name of the event
     * @param room                              The room of the event
     * @param start_time                        The starting time of the event
     * @param speaker_list                      The list of speakers for the event
     * @param capacity                          The capacity of the event
     * @param duration                          The duration of the event in minutes
     * @return                                  true if the event was successfully created
     * @throws EventCreationFailureException    If the event failed to be created
     */
    public boolean createEvent(String name, String room, LocalDateTime start_time, long duration, ArrayList<String> speaker_list, int capacity)
            throws EventCreationFailureException {
        ArrayList<Event> events = this.em.getEventsList();
        for (Event event : events) {
            LocalDateTime lower = event.getTime();
            LocalDateTime upper = lower.plusMinutes(event.getDuration());
            LocalDateTime end_time = start_time.plusMinutes(duration);
            if ((start_time.isAfter(lower) && start_time.isBefore(upper)) ||
                    (end_time.isAfter(lower) && end_time.isBefore(upper)) || (start_time.isEqual(lower))){
                if ((event.getRoom().equals(room))) {
                    throw new EventCreationFailureException("This room is taken for this time");
                }
                for (String s: event.getSpeakerList()){
                    if(speaker_list.contains(s)){
                        throw new EventCreationFailureException(s +  " is unavailable for this time");
                    }
                }
            }
        }
        ArrayList<Speaker> speakerObjectList = new ArrayList<>();
        for(String s: speaker_list){
            speakerObjectList.add((Speaker)reg.getUserByUserName(s));
        }
        this.em.createEvent(name, room, start_time, duration, speaker_list, capacity, speakerObjectList);
        return true;
    }
}