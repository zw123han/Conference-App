package MessagingSystem;

import UserSystem.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains the text display for viewing chat history and replying to messages.
 *
 * @author Chrisee, Elliot
 */
public class MessageInboxPresenter extends CommandPresenter {
    private Registrar reg;
    private String username;
    private ChatroomManager cm;
    private HashMap<String, String> profanities;

    /**
     * Initiates a new MessageInboxPresenter
     *
     * @param reg          Registrar
     * @param username     username of the currently logged in user
     * @param cm           ChatroomManager
     * @param profanities  a hashmap of chosen profanities and their replacement
     */
    public MessageInboxPresenter(Registrar reg, String username, ChatroomManager cm, HashMap<String, String> profanities) {
        this.reg = reg;
        this.username = username;
        this.cm = cm;
        this.profanities = profanities;
    }

    /**
     * Sets username to that of the currently logged in user.
     *
     * @param currentUser       username of the current user
     */
    public void setLoggedInUser(String currentUser) {
        username = currentUser;
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

    private Integer getNumUnread(String friend) {
        Chatroom c = cm.getChatroom(username, friend);
        return c.getUnread(username);
    }

    public String getTotalUnread() {
        int counter = 0;
        for (String friend : getUsersTalkto()) {
            counter += getNumUnread(friend);
        }
        return Integer.toString(counter);
    }

    /**
     * Formats a series of users with whom the logged in user has chatted, including the number of unread messages, name of the sender, and username.
     *
     * @return     text display for chat histories
     */
    public ArrayList<ArrayList<String>> getChatroomOptions() {
        ArrayList<String> users = getUsersTalkto();
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (String user : users) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(reg.getNameByUsername(user));
            temp.add(user);
            temp.add(getNumUnread(user).toString());
            result.add(temp);
        }
        return result;
    }

    public ArrayList<ArrayList<String>> getAllMessages(String recipient) {
        Chatroom c = cm.getChatroom(username, recipient);
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        ArrayList<Integer> history = c.getMessagePositions();
        for (Integer i : history) {
            ArrayList<String> messageData = new ArrayList<>();
            messageData.add(c.getSender(i));
            messageData.add(c.getDate(i));
            messageData.add(filterProfanity(c.getMessage(i)));
            messageData.add(i.toString());
            result.add(messageData);
        }
        return result;
    }

    public ArrayList<String> getNewestMessage(String recipient) {
        return getAllMessages(recipient).get(getAllMessages(recipient).size());
    }

    /**
     * Formats the chatlog, including message content, the sender name and username, and the date and time the message was sent, converted to local time.
     *
     * @return   text display for chatlog
     */
    public String chatView(String recipient) {
        Chatroom c = cm.getChatroom(username, recipient);
        ArrayList<Integer> history = c.getMessagePositions();
        return chatroomFormatter(history, c);
    }

    /**
     * Formats the pinned messages in the same manner as in the chat.
     *
     * @return   text display for pinned messages
     */
    public String viewPinned(String recipient) {
        Chatroom c = cm.getChatroom(username, recipient);
        ArrayList<Integer> pinned = c.getPinned();
        String result = "\nPINNED MESSAGES:\n------------------------";
        if (pinned.isEmpty()) {
            return result + "\nThere are no pinned messages.\n";
        }
        return result + chatroomFormatter(pinned, c);
    }

    private String chatroomFormatter(ArrayList<Integer> positions, Chatroom c) {
        StringBuilder result = new StringBuilder();
        for (Integer m : positions) {
            String sender = c.getSender(m);
            result.append("\n(")
                    .append(m)
                    .append(") ");
            if (c.isPinned(m)) {
                result.append("PIN");
            }
            if (c.isUnread(username, m)) {
                result.append("*");
            }
            result.append("\nFrom: ")
                    .append(reg.getNameByUsername(sender))
                    .append(" (@")
                    .append(sender)
                    .append(")")
                    .append("\nSent: ")
                    .append(c.getDate(m))
                    .append("\n")
                    .append(messageFormatter(c.getMessage(m)))
                    .append("\n");
        }
        return result.toString();
    }

    private String messageFormatter(String message) {
        StringBuilder sbm = new StringBuilder(filterProfanity(message));
        int i = 0;
        while (i + 80 < sbm.length()) {
            int firstSpace = sbm.indexOf(" ", i + 80);
            if (firstSpace != -1) {
                sbm.replace(firstSpace, firstSpace + 1, "\n");
            }
            i += 80;
        }
        return sbm.substring(0, sbm.toString().length()-1);
    }

    private ArrayList<String> getTrailingStrings(String profanity, ArrayList<String> allFiller) {
        ArrayList<String> result = new ArrayList<>();
        if (profanity.substring(0, 1).matches("[a-zA-Z]")) {
            result.add("");
        } else {
            result.add(allFiller.get(0));
        }
        if (profanity.substring(profanity.length()-1).matches("[a-zA-z]")) {
            result.add("");
        } else {
            result.add(allFiller.get(allFiller.size()-1));
        }
        return result;
    }

    private String censorProfanityBuilder(String match, String profanity) {
        String replacement = profanities.get(match);
        String upperReplacement = replacement.substring(0, 1).toUpperCase() + replacement.substring(1);
        ArrayList<String> allFiller = new ArrayList<>();
        Pattern pattern = Pattern.compile("[\\s.0-9]+");
        Matcher matcher = pattern.matcher(profanity);
        while (matcher.find()) {
            String filler = matcher.group();
            allFiller.add(filler);
        }
        ArrayList<String> trailingStrings = getTrailingStrings(profanity, allFiller);
        String firstChar = profanity.substring(trailingStrings.get(0).length(), trailingStrings.get(0).length() + 1);
        if (firstChar.matches("[a-zA-Z]") && firstChar.equals(firstChar.toUpperCase())) {
            replacement = upperReplacement;
        }
        return trailingStrings.get(0) + replacement + trailingStrings.get(1);
    }

    private String censorProfanity(String match, String message) {
        String result = message;
        String regex = "[\\s.]*" + match + "[\\s.s]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String profanity = matcher.group();
            result = result.replaceFirst(profanity, censorProfanityBuilder(match, profanity));
        }
        return result;
    }

    private String filterProfanity(String message) {
        String result = message;
        for (String profanity : profanities.keySet()) {
            result = censorProfanity(profanity, result);
        }
        return result;
    }

    /**
     * Formats a prompt for user to select a message.
     *
     * @param option   purpose of the selection
     * @return         text display for chatlog
     */

    public String whichMessage(String option){
        return "Type the number above the message you want to " + option +".";
    }

    /**
     * Formats a menu for messaging that includes basic options the select.
     *
     * @return     text display for message menu.
     */
    public String messageMenu() {
        return "1) Delete messages\n2) Pin/Unpin message\n3) View pinned";
    }

    /**
     * Formats a menu option for when a user can reply to a message.
     *
     * @return    text display option for a reply.
     */
    public String replyMessage() {
        return "4) Reply to messages";
    }
}
