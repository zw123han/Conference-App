import java.util.*;

/**
 * Contains the text display for viewing chat history and replying to messages.
 *
 * @author Chrisee, Elliot
 */
public class MessageInboxPresenter extends CommandPresenter {

    /**
     * Displays a series of users with whom the logged in user has chatted.
     *
     * @param reg           Registrar
     * @param friends       An ArrayList of friends; this user has chatted with these friends
     */
    public void menuDisplay(Registrar reg, ArrayList<String> friends) {
        System.out.println("\nCHAT HISTORY:");
        System.out.println("------------------------");
        for (String friend : friends) {
            System.out.println(reg.getNameByUsername(friend) + " (@" + friend + ")");
        }
        if (friends.isEmpty()) {
            System.out.println("You can't chat with any users.");
        }
        System.out.println();
    }

    /**
     * Displays the chatlog, including message content, the sender name and username, and the date and time the message was sent, converted to local time.
     *
     * @param reg       Registrar
     * @param c        Chatroom
     */
    public void chatView(Registrar reg, Chatroom c) {
        ArrayList<Integer> history = c.getMessageKeys();
        for (Integer m : history) {
            String sender = c.getSender(m);
            System.out.println("\n(" + m + ")");
            System.out.println("From: " + reg.getNameByUsername(sender) + " (@" + sender + ")");
            System.out.println("Sent: " + c.getDate(m));
            messageFormatter(c.getMessage(m));
        }
        System.out.println();
    }

    /**
     * Formats the message such that:
     * - It has at least 80 characters per line
     * - If a line exceeds 80 characters, then the line would be wrapped at the first space after the 80th character
     *
     * @param message       String of message to be formatted
     */
    public void messageFormatter(String message) {
        StringBuilder sbm = new StringBuilder(message);
        int i = 0;
        while (i + 80 < sbm.length()) {
            int firstSpace = sbm.indexOf(" ", i + 80);
            if (firstSpace != -1) {
                sbm.replace(firstSpace, firstSpace + 1, "\n");
            }
            i += 80;
        }
        System.out.println(sbm);
    }

    public void whichMessage(){
        System.out.println("Type the number above the message you want to delete");
    }

    /**
     * Displays text for asking if user wants to delete a message
     */
    public void deleteMessage() {
        System.out.println("1) Delete messages");
    }

    /**
     * Displays text for when a user can reply to a message
     */
    public void replyMessage() {
        System.out.println("2) Reply to messages");
    }
}
