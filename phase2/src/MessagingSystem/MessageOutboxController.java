package MessagingSystem;

import UserSystem.*;
import EventSystem.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * For managing user permissions when sending messages.
 *
 * @author Elliot, Chrisee
 */
public class MessageOutboxController {
    private String username;
    private Registrar registrar;
    private EventManager eventManager;
    private ChatroomManager chatroomManager;

    /**
     * Initializes a new MessagingSystem.ChatController.
     *
     * @param username          Username of the sender
     * @param registrar               Registrar
     * @param eventManager                EventManager
     */
    public MessageOutboxController(String username, Registrar registrar, EventManager eventManager, ChatroomManager chatroomManager) {
        this.username = username;
        this.registrar = registrar;
        this.eventManager = eventManager;
        this.chatroomManager = chatroomManager;
    }

    /**
     * Sets username to that of the currently logged in user.
     *
     * @param currentUser       username of the current user
     */
    public void setLoggedInUser(String currentUser) {
        username = currentUser;
    }

    /**
     * Checks if message is valid (Non-empty).
     *
     * @param message       Message
     * @return boolean      True if length is not 0, false otherwise
     */
    public boolean validateMessage(String message) {
        return message.trim().length() != 0;
    }

    /**
     * Checks if sender can send messages to a specific recipient.
     *
     * @param recipient         Username of recipient
     * @return                  True if sender has permission to message recipient
     */
    public boolean canMessage(String recipient) {
        return ((registrar.isFriend(username, recipient) || registrar.isOrganizer(username)) && registrar.userExisting(recipient) ||
                registrar.isAdmin(username));
    }

    /**
     * Checks if sender can send messages to all Attendees of an event.
     *
     * @param evt               Event id
     * @return                  True if sender has permission to message, false otherwise
     */
    public boolean canMessage(Long evt) {
        return eventManager.hasEvent(evt) ||
                (eventManager.hasEvent(evt) && registrar.isSpeaker(username) &&
                        eventManager.getEvent(evt).getSpeakerList().contains(username));
    }

    /**
     * Checks if sender can send a message to all Speakers speaking in at least one event in the conference.
     *
     * @return                  True if sender has permission to message all speakers
     */
    public boolean canSendToSpeakers() {
        return registrar.isOrganizer(username) || registrar.isAdmin(username);
    }

    /**
     * Sends message to a recipient.
     *
     * @param recipient         Username of recipient
     * @param message           Message to be sent
     */
    public void sendMessage(String recipient, String message) {
        ArrayList<String> recipients = new ArrayList<>();
        recipients.add(username);
        recipients.add(recipient);
        chatroomManager.sendOne(recipients, message.trim(), username);
    }

    private String formatSendAll(String message, Long evt) {
        return "[Event: " + eventManager.getName(evt) + " in room " + eventManager.getRoom(evt) +
                ", " + eventManager.getTime(evt) + "]\n" + message;
    }

    /**
     * Sends message to all attendees in an event.
     *
     * @param evt           ID of an event
     * @param message       String of message to be sent
     * @return true if messages are sent
     */
    public boolean sendMessage(Long evt, String message) {
        try {
            ArrayList<String> recipients = eventManager.getSignedUpUsers(evt);
            chatroomManager.sendAll(recipients, formatSendAll(message.trim(), evt), username);
            return true;
        } catch (EventNotFoundException e) {
            return false;
        }
    }

    /**
     * Gets the IDs of the events to which this user can send a message to.
     *
     * @return   A list of event IDs.
     */
    public ArrayList<Long> getAllEventIDs() {
        if (registrar.isOrganizer(username) || registrar.isAdmin(username)) {
            return eventManager.getEventIDs();
        } else if (registrar.isSpeaker(username)) {
            return registrar.getSpeakerTalks(username);
        }
        return new ArrayList<>();
    }

    /**
     * Gets the info of the events to which this user can send a message to.
     *
     * @return   A HashMap with event info as keys and ids as values.
     */
    public HashMap<String, Long> getAllEventInfo() {
        HashMap<String, Long> eventInfo = new HashMap<>();
        if (registrar.isOrganizer(username) || registrar.isAdmin(username)) {
            for(Long id : eventManager.getEventIDs()){
                String temp = eventManager.getType(id) + ": " + eventManager.getName(id)  + " [Room " + eventManager.getRoom(id) + " at " + eventManager.getTime(id) + "]";
                eventInfo.put(temp, id);
            }
        } else if (registrar.isSpeaker(username)) {
            for(Long id : registrar.getSpeakerTalks(username)) {
                String temp = eventManager.getType(id) + ": " + eventManager.getName(id)  + " [Room " + eventManager.getRoom(id) + " at " + eventManager.getTime(id) + "]";
                eventInfo.put(temp, id);
            }
        }
        return eventInfo;
    }

    /**
     * Gets a list of speakers in this conference.
     *
     * @return   A list of speaker usernames.
     */
    public ArrayList<String> getMessageSpeakers() {
        ArrayList<String> speakers = new ArrayList<>();
        for (Long event_id : eventManager.getEventIDs()) {
            for (String speaker : eventManager.getSpeakerList(event_id)) {
                if (!speakers.contains(speaker)) {
                    speakers.add(speaker);
                }
            }
        }
        return speakers;
    }
}
