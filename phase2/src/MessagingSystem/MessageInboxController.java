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
public class MessageInboxController extends CommandPresenter {
    private Registrar registrar;
    private String username;
    private ChatroomManager chatroomManager;
    private HashMap<String, String> profanities;

    /**
     * Initiates a new MessageInboxPresenter
     *
     * @param registrar         Registrar
     * @param username    username of the currently logged in user
     * @param chatroomManager          ChatroomManager
     * @param profanities a hashmap of chosen profanities and their replacement
     */
    public MessageInboxController(Registrar registrar, String username, ChatroomManager chatroomManager, HashMap<String, String> profanities) {
        this.registrar = registrar;
        this.username = username;
        this.chatroomManager = chatroomManager;
        this.profanities = profanities;
    }

    /**
     * Sets username to that of the currently logged in user.
     *
     * @param currentUser username of the current user
     */
    public void setLoggedInUser(String currentUser) {
        username = currentUser;
    }

    public String getTotalUnread() {
        int counter = 0;
        for (String friend : registrar.getUserFriends(username)) {
            counter += getNumUnread(friend);
        }
        return Integer.toString(counter);
    }

    /**
     * Formats a series of users with whom the logged in user has chatted, including the number of unread messages, name of the sender, and username.
     *
     * @return text display for chat histories
     */
    public ArrayList<ArrayList<String>> getChatroomOptions() {
        ArrayList<String> users = getChattableUsers();
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (String user : users) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(registrar.getNameByUsername(user));
            temp.add(user);
            temp.add(getNumUnread(user).toString());
            result.add(temp);
        }
        return result;
    }

    public ArrayList<ArrayList<String>> getMessages(String recipient) {
        Chatroom chatroom = chatroomManager.getChatroom(username, recipient);
        ArrayList<Integer> history = chatroom.getMessagePositions();
        return collectMessagesFromChatroom(history, chatroom);
    }

    public ArrayList<ArrayList<String>> getPinned(String recipient) {
        Chatroom chatroom = chatroomManager.getChatroom(username, recipient);
        ArrayList<Integer> pinned = chatroom.getPinned();
        return collectMessagesFromChatroom(pinned, chatroom);
    }

    public ArrayList<String> getNewestMessage(String recipient) {
        return getMessages(recipient).get(getMessages(recipient).size() - 1);
    }

    public String getDisplayName(String username) {
        return registrar.getNameByUsername(username);
    }

    public boolean isPinned(String id, String recipient) {
        Chatroom c = chatroomManager.getChatroom(username, recipient);
        return c.isPinned(Integer.parseInt(id));
    }

    /**
     * Deletes a message with index from chatroom.
     *
     * @param recipient     username of the recipient
     * @param choice        index of the message
     */
    public void deleteMessage(String recipient, String choice) {
        int index = Integer.parseInt(choice);
        chatroomManager.deleteMessage(username, recipient, index);
    }

    /**
     * Pins a message with index in chatroom.
     *
     * @param recipient     username of the recipient
     * @param choice        index of the message
     */
    public void pinUnpinMessage(String recipient, String choice){
        Integer key = Integer.parseInt(choice);
        Chatroom chatroom = chatroomManager.getChatroom(username, recipient);
        chatroom.pinUnpin(key);
    }

    /**
     * Marks all of the messages sent by recipient as read.
     *
     * @param recipient     username of the recipient
     */
    public void markAllRead(String recipient) {
        Chatroom c = chatroomManager.getChatroom(username, recipient);
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
        chatroomManager.sendOne(recipients, message.trim(), username);
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

    public boolean canDelete(String person) {
        return username.equals(person) || registrar.isAdmin(username);
    }

    public boolean canSendAll() {
        return registrar.isAdmin(username) || registrar.isSpeaker(username) || registrar.isOrganizer(username);
    }

    // ===============
    // PRIVATE HELPERS
    // ===============

    // takes the censored profanity and merges it with
    // the appropriate trailing strings
    private ArrayList<String> getTrailingStrings(String profanity, ArrayList<String> allFiller) {
        ArrayList<String> result = new ArrayList<>();
        if (profanity.substring(0, 1).matches("[a-zA-Z]")) {
            result.add("");
        } else {
            result.add(allFiller.get(0));
        }
        if (profanity.substring(profanity.length() - 1).matches("[a-zA-z]")) {
            result.add("");
        } else {
            result.add(allFiller.get(allFiller.size() - 1));
        }
        return result;
    }

    // formats the censored profanity to match the profanity
    // replaces profanity with the censored profanity
    private String censorProfanityBuilder(String match, String profanity) {
        String replacement = profanities.get(match);
        String upperReplacement = replacement.substring(0, 1).toUpperCase() + replacement.substring(1);
        ArrayList<String> allFiller = new ArrayList<>();
        Pattern pattern = Pattern.compile("[\\s.0-9?!]+");
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

    // locates the location of the profanity in the message
    // takes into consideration the trailing strings around the profanity
    private String censorProfanity(String match, String message) {
        String result = message;
        String regex = "[\\s.]*" + match + "[\\s.s?!]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String profanity = matcher.group();
            result = result.replaceFirst(profanity, censorProfanityBuilder(match, profanity));
        }
        return result;
    }

    // Main method for filtering profanities
    private String filterProfanity(String message) {
        String result = message;
        for (String profanity : profanities.keySet()) {
            result = censorProfanity(profanity, result);
        }
        return result;
    }

    private ArrayList<String> getChattableUsers() {
        ArrayList<String> users = new ArrayList<>();
        HashMap<ArrayList<String>, Chatroom> cms = chatroomManager.getAllChatrooms(username);
        for (ArrayList<String> key : cms.keySet()) {
            if (key.contains(username)) {
                for (String person : key) {
                    if (!person.equals(username)) {
                        users.add(person);
                    }
                }
            }
        }
        for (String user : registrar.getUserFriends(username)) {
            if (!users.contains(user)) {
                users.add(user);
            }
        }
        return users;
    }

    private Integer getNumUnread(String friend) {
        Chatroom c = chatroomManager.getChatroom(username, friend);
        return c.getUnread(username);
    }

    private ArrayList<ArrayList<String>> collectMessagesFromChatroom(ArrayList<Integer> ids, Chatroom chatroom) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (Integer i : ids) {
            ArrayList<String> messageData = new ArrayList<>();
            messageData.add(chatroom.getSender(i));
            messageData.add(chatroom.getDate(i));
            messageData.add(filterProfanity(chatroom.getMessage(i)));
            messageData.add(i.toString());
            result.add(messageData);
        }
        return result;
    }
}
