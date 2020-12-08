package MessagingSystem;

import UserSystem.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * For managing user permissions when reading, replying to, deleting, or editing messages.
 *
 * @author Chrisee
 */

public class MessageInboxController {
    private Registrar reg;
    private String username;
    private ChatroomManager cm;

    /**
     * Initiates a new MessageInboxController
     *
     * @param reg       Registrar
     * @param username  username of the currently logged in user
     * @param cm        ChatroomManager
     */
    public MessageInboxController(Registrar reg, String username, ChatroomManager cm) {
        this.reg = reg;
        this.username = username;
        this.cm = cm;
    }

    public boolean canSendAll() {
        return reg.isAdmin(username) || reg.isSpeaker(username) || reg.isOrganizer(username);
    }

    public boolean canDelete(String person) {
        return username.equals(person) || reg.isAdmin(username);
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
     * Initiates a new InboxController
     *
     * @param recipient  username of the person which this user is messaging
     * @return           true if the logged in user can reply in their chat with recipient.
     */
    public boolean canReply(String recipient) {
        if (cm.hasChatroom(username, recipient)) {
            if (reg.isAdmin(username) || reg.isOrganizer(username) || (reg.isAttendee(username)) &&
                    (reg.isFriend(username, recipient) || !reg.isAttendee(recipient))) {
                return true;
            }
            Chatroom c = cm.getChatroom(username, recipient);
            ArrayList<Integer> history = c.getMessagePositions();
            for (Integer m : history) {
                if (c.getSender(m).equals(recipient)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a message can be deleted from chatroom.
     *
     * @param recipient     username of the recipient
     * @param choice        index of the message
     * @return              true if the message can be deleted.
     */
    public boolean canDelete(String recipient, String choice){
        int key = Integer.parseInt(choice);
        Chatroom chatroom = cm.getChatroom(username, recipient);
        if(key < chatroom.getSize()){
            return chatroom.getSender(key).equals(username);
        }
        return false;
    }

    /**
     * Deletes a message with index from chatroom.
     *
     * @param recipient     username of the recipient
     * @param choice        index of the message
     */
    public void deleteMessage(String recipient, String choice) {
        int index = Integer.parseInt(choice);
        cm.deleteMessage(username, recipient, index);
    }

    /**
     * Pins a message with index in chatroom.
     *
     * @param recipient     username of the recipient
     * @param choice        index of the message
     */
    public void pinUnpinMessage(String recipient, String choice){
        Integer key = Integer.parseInt(choice);
        Chatroom chatroom = cm.getChatroom(username, recipient);
        chatroom.pinUnpin(key);
    }

    private ArrayList<String> getUsersTalkTo() {
        ArrayList<String> users = new ArrayList<>();
        HashMap<ArrayList<String>, Chatroom> cms = cm.getAllChatrooms(username);
        for (ArrayList<String> key : cms.keySet()) {
            if (key.contains(username)) {
                for (String person : key) {
                    if (!person.equals(username)) {
                        users.add(person);
                    }
                }
            }
        }
        return users;
    }

    /**
     * Checks if the logged in user can chat with a certain user.
     *
     * @param recipient     username of the recipient
     * @return              true if the logged in user has chatted with recipient.
     */
    public boolean canViewChat(String recipient) {
        ArrayList<String> friends = getUsersTalkTo();
        return friends.contains(recipient);
    }

    /**
     * Marks all of the messages sent by recipient as read.
     *
     * @param recipient     username of the recipient
     */
    public void markAllRead(String recipient) {
        Chatroom c = cm.getChatroom(username, recipient);
        for (Integer i : c.getMessagePositions()) {
            if (c.isUnread(username, i)) {
                c.read(i);
            }
        }
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
        cm.sendOne(recipients, message.trim(), username);
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

}
