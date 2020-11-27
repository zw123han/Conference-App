package MessagingSystem;

import UserSystem.*;
import java.util.ArrayList;

/**
 * For managing user permissions when reading, replying to, deleting, or editing messages.
 *
 * @author Chrisee
 */

public class MessageInboxController {
    private Registrar reg;
    private String username;

    /**
     * Initiates a new MessageInboxController
     *
     * @param reg       Registrar
     * @param username  username of the currently logged in user
     */
    public MessageInboxController(Registrar reg, String username) {
        this.reg = reg;
        this.username = username;
    }

    /**
     * Initiates a new InboxController
     *
     * @param cm         The current chatroom the user is reading
     * @param recipient  username of the person which this user is messaging
     */
    public boolean canReply(ChatroomManager cm, String recipient) {
        if (cm.hasChatroom(username, recipient)) {
            if (reg.isAdmin(username) || reg.isOrganizer(username) || reg.isAttendee(username)) {
                return true;
            }
            Chatroom c = cm.getChatroom(username, recipient);
            ArrayList<Integer> history = c.getMessageKeys();
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
     * @param cm       Registrar
     * @param recipient  username of the currently logged in user
     * @param choice        InboxPresenter
     */
    public boolean canDelete(ChatroomManager cm, String recipient, String choice){
        Integer key = Integer.parseInt(choice);
        Chatroom chatroom = cm.getChatroom(username, recipient);
        if(key < chatroom.getSize()){
            return chatroom.getSender(key).equals(username);
        }
        return false;
    }

    public void deleteMessage(String recipient, ChatroomManager cm, String choice) {
        Integer key = Integer.parseInt(choice);
        cm.deleteMessage(username, recipient, key);
    }
}
