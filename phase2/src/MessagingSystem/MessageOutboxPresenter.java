package MessagingSystem;

import java.util.*;

/**
 * OutboxController handles user requests for sending messages.
 *
 * @author  Chrisee Zhu, Elliot
 */
public class MessageOutboxPresenter {
    private MessageOutboxController oc;

    /**
     * initializes a new OutboxController.
     *
     * @param oc        OutboxController
     */
    public MessageOutboxPresenter(MessageOutboxController oc) {
        this.oc = oc;
    }

    /**
     * Sets username to that of the currently logged in user.
     *
     * @param currentUser       username of the current user
     */
    public void setLoggedInUser(String currentUser) {
        oc.setLoggedInUser(currentUser);
    }

    /**
     * Checks if message is valid (Non-empty).
     *
     * @param message       Message
     * @return boolean      True if length is not 0, false otherwise
     */
    public boolean validateMessage(String message) {
        return oc.validateMessage(message);
    }

    /**
     * Sends message to all attendees in an event.
     *
     * @param evt           ID of an event
     * @param message       String of message to be sent
     * @return true if messages are sent
     */
    public boolean sendMessage(Long evt, String message) {
        return oc.sendMessage(evt, message);
    }

    /**
     * Sends message to all speakers in the conference.
     *
     * @param message       String of message to be sent
     * @return true if messages are sent
     */
    public boolean sendToSpeakers(String message){
        for(String recipient : oc.getMessageSpeakers()){
            oc.sendMessage(recipient, message);
        }
        return true;
    }

    /**
     * Checks if sender can send a message to all Speakers.
     *
     * @return                  True if sender has permission to message all speakers
     */
    public boolean canSendToSpeakers() {
        return oc.canSendToSpeakers();
    }

    /**
     * Gets the info of the events to which this user can send a message to.
     *
     * @return   A HashMap with event info as keys and ids as values.
     */
    public HashMap<String, Long> getAllEventInfo() {
        return oc.getAllEventInfo();
    }

}
