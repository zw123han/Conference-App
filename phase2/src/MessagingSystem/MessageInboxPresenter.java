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
    private boolean profanityFilter = true;
    private HashMap<String, String> profanities;


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

    private Integer getNumUnread(String friend) {
        Chatroom c = cm.getChatroom(username, friend);
        return c.getUnread(username);
    }

    /**
     * Displays a series of users with whom the logged in user has chatted.
     *
     */
    public String menuDisplay() {
        ArrayList<String> friends = getUsersTalkto();
        StringBuilder result = new StringBuilder("\nCHAT HISTORY:\n------------------------");
        for (String friend : friends) {
            result.append("\n[")
                    .append(getNumUnread(friend))
                    .append(" Unread] ")
                    .append(reg.getNameByUsername(friend))
                    .append(" (@")
                    .append(friend)
                    .append(")");
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
        ArrayList<Integer> history = c.getMessagePositions();
        return chatroomFormatter(history, c);
    }

    public String viewPinned(String recipient) {
        Chatroom c = cm.getChatroom(username, recipient);
        ArrayList<Integer> pinned = c.getPinned();
        String result = "\nPINNED MESSAGES:\n------------------------";
        return result + chatroomFormatter(pinned, c);
    }

    private String chatroomFormatter(ArrayList<Integer> positions, Chatroom c) {
        StringBuilder result = new StringBuilder();
        for (Integer m : positions) {
            String sender = c.getSender(m);
            result.append("\n(")
                    .append(m)
                    .append(")");
            if (c.isRead(username, m)) {
                result.append(" *");
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

    /**
     * Formats the message such that:
     * - It has at least 80 characters per line
     * - If a line exceeds 80 characters, then the line would be wrapped at the first space after the 80th character
     *
     * @param message       String of message to be formatted
     */
    public String messageFormatter(String message) {
        StringBuilder sbm = new StringBuilder(message);
        if (profanityFilter) {
            sbm = new StringBuilder(filterProfanity(message));
        }
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

    // NOTE: keys of hashmap should be Regex! For example:
    // "fuck" = "(?i)f *u *c *k" to ignore case and whitespace
    // Use parseProfanity to do the conversion automatically when initializing profanities.
    private String parseProfanity(String profanity) {
        StringBuilder result = new StringBuilder("(?i)");
        String[] bad = profanity.split("");
        for (String letter : bad) {
            result.append(letter)
                    .append(" *");
        }
        return result.substring(0, result.length()-2);
    }

    public void changeProfanity(boolean setter) {
        profanityFilter = !profanityFilter;
    }

    private String censorProfanityBuilder(String match, String profanity) {
        ArrayList<String> allFiller = new ArrayList<>();
        Pattern pattern = Pattern.compile("[ .]+");
        Matcher matcher = pattern.matcher(profanity);
        while (matcher.find()) {
            String filler = matcher.group();
            allFiller.add(filler);
        }
        String replacement = profanities.get(match);
        if (allFiller.get(0).contains(".")) {
            replacement = replacement.substring(0, 1).toUpperCase() + replacement.substring(1);
        }
        return allFiller.get(0) + replacement + allFiller.get(allFiller.size()-1);
    }

    private String censorProfanity(String match, String message) {
        String result = message;
        Pattern pattern = Pattern.compile("[ .]+" + match + "[ .]+");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String profanity = matcher.group();
            result = message.replaceFirst(profanity, censorProfanityBuilder(match, profanity));
        }
        return result;
    }

    private String filterProfanity(String message) {
        for (String profanity : profanities.keySet()) {
            message = censorProfanity(profanity, message);
        }
        return message;
    }

    public String whichMessage(String option){
        return "Type the number above the message you want to " + option +".";
    }

    /**
     * Displays text for message menu.
     */

    public String messageMenu() {
        return "1) Delete messages\n2) Pin message\n3) View pinned";
    }

    /**
     * Displays text for when a user can reply to a message
     */
    public String replyMessage() {
        return "4) Reply to messages";
    }
}
