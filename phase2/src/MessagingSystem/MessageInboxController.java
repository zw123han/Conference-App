package MessagingSystem;

import UserSystem.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * For managing user permissions when reading, replying to, deleting, or editing messages.
 *
 * @author Chrisee
 */

public class MessageInboxController implements MessageControllerInterface {
    private Registrar reg;
    private String username;
    private ChatroomManager cm;

    /**
     * Initiates a new MessageInboxController
     *
     * @param reg       Registrar
     * @param username  username of the currently logged in user
     */
    public MessageInboxController(Registrar reg, String username, ChatroomManager cm) {
        this.reg = reg;
        this.username = username;
        this.cm = cm;
    }

    public void setLoggedInUser(String currentUser) {
        username = currentUser;
    }

    /**
     * Initiates a new InboxController
     *
     * @param recipient  username of the person which this user is messaging
     */
    public boolean canReply(String recipient) {
        if (cm.hasChatroom(username, recipient)) {
            if (reg.isAdmin(username) || reg.isOrganizer(username) || reg.isAttendee(username)) {
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
     * Initiates a new InboxController
     *
     * @param recipient  username of the currently logged in user
     * @param choice        InboxPresenter
     */
    public boolean canDelete(String recipient, String choice){
        int key = Integer.parseInt(choice);
        Chatroom chatroom = cm.getChatroom(username, recipient);
        if(key < chatroom.getSize()){
            return chatroom.getSender(key).equals(username);
        }
        return false;
    }

    public void deleteMessage(String recipient, String choice) {
        int key = Integer.parseInt(choice);
        cm.deleteMessage(username, recipient, key);
    }

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

    public boolean canViewChat(String recipient) {
        ArrayList<String> friends = getUsersTalkTo();
        return friends.contains(recipient);
    }

    public boolean validateMessage(String message) {
        return message.length() != 0;
    }

    public void sendMessage(String recipient, String message) {
        ArrayList<String> recipients = new ArrayList<>();
        recipients.add(username);
        recipients.add(recipient);
        cm.sendOne(recipients, message, username);
    }

    public void markAllRead(String recipient) {
        Chatroom c = cm.getChatroom(username, recipient);
        for (Integer i : c.getMessagePositions()) {
            if (c.isUnread(username, i)) {
                c.read(i);
            }
        }
    }

}
