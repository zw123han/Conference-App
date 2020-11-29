package MessagingSystem;

import UserSystem.*;
import java.util.*;

/**
 * Contains the text display for viewing chat history and replying to messages.
 *
 * @author Chrisee, Elliot
 */
public class MessageInboxPresenter extends CommandPresenter {
    private Registrar reg;
    private String username;
    private ChatroomManager cm;

    public MessageInboxPresenter(Registrar reg, String username, ChatroomManager cm) {
        this.reg = reg;
        this.username = username;
        this.cm = cm;
    }


    private ArrayList<String> getUsersTalkto() {
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
     * Displays a series of users with whom the logged in user has chatted.
     *
     */
    public String menuDisplay() {
        ArrayList<String> friends = getUsersTalkto();
        String result = "\nCHAT HISTORY:\n------------------------";
        for (String friend : friends) {
            result += "\n" + reg.getNameByUsername(friend) + " (@" + friend + ")";
        }
        if (friends.isEmpty()) {
            return result + "\nYou can't chat with any users.\n";
        }
        return result + "\n";
    }

    /**
     * Displays the chatlog, including message content, the sender name and username, and the date and time the message was sent, converted to local time.
     *
     */
    public String chatView(String recipient) {
        Chatroom c = cm.getChatroom(username, recipient);
        String result = "";
        ArrayList<Integer> history = c.getMessagePositions();
        for (Integer m : history) {
            String sender = c.getSender(m);
            result += "\n(" + m + ")";
            result += ("\nFrom: " + reg.getNameByUsername(sender) + " (@" + sender + ")");
            result += ("\nSent: " + c.getDate(m));
            result += ("\n" + messageFormatter(c.getMessage(m)) + "\n");
        }
        return result;
    }

    /**
     * Formats the message such that:
     * - It has at least 80 characters per line
     * - If a line exceeds 80 characters, then the line would be wrapped at the first space after the 80th character
     *
     * @param message       String of message to be formatted
     */
    public String messageFormatter(String message) {
        StringBuilder sbm = new StringBuilder(message);
        int i = 0;
        while (i + 80 < sbm.length()) {
            int firstSpace = sbm.indexOf(" ", i + 80);
            if (firstSpace != -1) {
                sbm.replace(firstSpace, firstSpace + 1, "\n");
            }
            i += 80;
        }
        return sbm.toString();
    }

    public String whichMessage(){
        return "Type the number above the message you want to delete.";
    }

    /**
     * Displays text for asking if user wants to delete a message
     */
    public String deleteMessage() {
        return "1) Delete messages";
    }

    /**
     * Displays text for when a user can reply to a message
     */
    public String replyMessage() {
        return "2) Reply to messages";
    }
}
