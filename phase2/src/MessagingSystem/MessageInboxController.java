package MessagingSystem;

import UserSystem.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Controller for the Message Inbox.
 * Handles model-side interactions upon request from the presenter and passes data
 * from the model to the presenter.
 *
 * @author Chrisee, Elliot
 */
public class MessageInboxController {
    private Registrar registrar;
    private String currentUser;
    private ChatroomManager chatroomManager;
    private HashMap<String, String> profanities;

    /**
     * Initiates a new MessageInboxPresenter
     *
     * @param registrar                Registrar
     * @param currentUser              username of the currently logged in user
     * @param chatroomManager          ChatroomManager
     * @param profanities              a hashmap of chosen profanities and their replacement
     */
    public MessageInboxController(Registrar registrar, String currentUser, ChatroomManager chatroomManager, HashMap<String, String> profanities) {
        this.registrar = registrar;
        this.currentUser = currentUser;
        this.chatroomManager = chatroomManager;
        this.profanities = profanities;
    }

    /**
     * Sets username to that of the currently logged in user.
     *
     * @param currentUser username of the current user
     */
    public void setLoggedInUser(String currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Gets the total number of unread messages for the currently logged in user.
     *
     * @return the total number of unread messages
     */
    public String getTotalUnread() {
        int totalUnread = 0;
        for (String friend : registrar.getUserFriends(currentUser)) {
            totalUnread += getNumUnread(friend);
        }
        return Integer.toString(totalUnread);
    }

    /**
     * Gets an ArrayList of chatroom data to be displayed in the message inbox.
     * Each set of chatroom data is stored in an ArrayList in the following form:
     * [Display name of the other user, username of the other user, number of unread messages]
     *
     * @return An ArrayList of chatroom data.
     */
    public ArrayList<ArrayList<String>> getChatroomOptions() {
        ArrayList<String> chattableUsers = getChattableUsers();
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (String user : chattableUsers) {
            ArrayList<String> chatroomData = new ArrayList<>();
            chatroomData.add(registrar.getNameByUsername(user));
            chatroomData.add(user);
            chatroomData.add(getNumUnread(user).toString());
            result.add(chatroomData);
        }
        return result;
    }

    /**
     * Gets an ArrayList of all message data to be displayed in the message inbox
     * in the logged in user's chatroom with recipient.
     * Each set of message data is stored in an ArrayList in the following form:
     * [Sender username, local date time, filtered message, message id]
     *
     * @return An ArrayList of all message data.
     */
    public ArrayList<ArrayList<String>> getMessages(String recipient) {
        Chatroom chatroom = chatroomManager.getChatroom(currentUser, recipient);
        ArrayList<Integer> history = chatroom.getMessagePositions();
        return collectMessagesFromChatroom(history, chatroom);
    }

    /**
     * Gets an ArrayList of message data belonging to pinned messages
     * in the logged in user's chatroom with recipient.
     * Each set of message data is stored in an ArrayList in the following form:
     * [Sender username, local date time, filtered message, message id]
     *
     * @return An ArrayList of pinned message data.
     */
    public ArrayList<ArrayList<String>> getPinned(String recipient) {
        Chatroom chatroom = chatroomManager.getChatroom(currentUser, recipient);
        ArrayList<Integer> pinned = chatroom.getPinned();
        return collectMessagesFromChatroom(pinned, chatroom);
    }

    /**
     * Gets the message data for the newest message sent in
     * in the logged in user's chatroom with recipient.
     * The message is stored in an Arraylist in the following form:
     * [Sender username, local date time, filtered message, message id]
     *
     * @return The message data (Arraylist) for the newest message.
     */
    public ArrayList<String> getNewestMessage(String recipient) {
        return getMessages(recipient).get(getMessages(recipient).size() - 1);
    }

    /**
     * Gets the display name associated with the senderUsername.
     *
     * @param username         String of username
     * @return                 String of display name
     */
    public String getDisplayName(String username) {
        return registrar.getNameByUsername(username);
    }

    /**
     * Checks whether the message with id in the logged in user's chatroom with
     * recipient is a pinned message.
     *
     * @param id         id of the message
     * @param recipient  username of the recipient
     * @return    True if the message is pinned.
     */
    public boolean isPinned(String id, String recipient) {
        Chatroom chatroom = chatroomManager.getChatroom(currentUser, recipient);
        return chatroom.isPinned(Integer.parseInt(id));
    }

    /**
     * Deletes a message with id from the logged in user's chatroom with recipient.
     *
     * @param recipient     username of the recipient
     * @param id            id of the message
     */
    public void deleteMessage(String recipient, String id) {
        chatroomManager.deleteMessage(currentUser, recipient, Integer.parseInt(id));
    }

    /**
     * Pins a message with id in the logged in user's chatroom with recipient if the message is pinned.
     * Otherwise, the message will be unpinned.
     *
     * @param recipient     username of the recipient
     * @param id            index of the message
     */
    public void pinUnpinMessage(String recipient, String id){
        Chatroom chatroom = chatroomManager.getChatroom(currentUser, recipient);
        chatroom.pinUnpin(Integer.parseInt(id));
    }

    /**
     * Marks all of the messages in the logged in user's chatroom with recipient
     * as read if they were previously unread. Messages can only be unread if they
     * were sent by a different user from the logged in user.
     *
     * @param recipient     username of recipient
     */
    public void markAllRead(String recipient) {
        Chatroom chatroom = chatroomManager.getChatroom(currentUser, recipient);
        for (Integer id : chatroom.getMessagePositions()) {
            if (chatroom.isUnread(currentUser, id)) {
                chatroom.read(id);
            }
        }
    }

    /**
     * Sends message to a recipient.
     *
     * @param recipient         Username of recipient
     * @param message           String of message to be sent
     */
    public void sendMessage(String recipient, String message) {
        ArrayList<String> recipients = new ArrayList<>();
        recipients.add(currentUser);
        recipients.add(recipient);
        chatroomManager.sendOne(recipients, message.trim(), currentUser);
    }

    /**
     * Checks if message is valid (non-empty).
     *
     * @param message       String of message to be sent
     * @return boolean      True if length is not 0, false otherwise
     */
    public boolean validateMessage(String message) {
        return message.trim().length() != 0;
    }

    /**
     * Checks whether the logged in user can delete a message. They can delete a message when:
     * - The logged in user is the sender;
     * - The logged in user is an admin.
     *
     * @param username      String of the message's sender's username
     * @return boolean      True if length is not 0, false otherwise
     */
    public boolean canDelete(String username) {
        return currentUser.equals(username) || registrar.isAdmin(currentUser);
    }

    /**
     * Checks whether the logged in user can send a group message. They can send a group message
     * when the logged in user is an admin, an organizer, or a speaker.
     *
     * @return boolean      True if the logged in user can send a group message.
     */
    public boolean canGroupMessage() {
        return registrar.isAdmin(currentUser) || registrar.isSpeaker(currentUser) || registrar.isOrganizer(currentUser);
    }


    // ===================
    //   PRIVATE HELPERS
    // ===================


    // Main method for filtering profanities
    private String filterProfanity(String message) {
        String result = message;
        for (String profanity : profanities.keySet()) {
            result = censorProfanity(profanity, result);
        }
        return result;
    }

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

    // Gets the usernames of users that this user can talk with
    // This includes this user's friends and any existing chatrooms
    // they're a part of in chatroom manager.
    private ArrayList<String> getChattableUsers() {
        ArrayList<String> chattableUsers = new ArrayList<>();
        HashMap<ArrayList<String>, Chatroom> cms = chatroomManager.getAllChatrooms(currentUser);
        for (ArrayList<String> key : cms.keySet()) {
            if (key.contains(currentUser)) {
                for (String person : key) {
                    if (!person.equals(currentUser)) {
                        chattableUsers.add(person);
                    }
                }
            }
        }
        for (String user : registrar.getUserFriends(currentUser)) {
            if (!chattableUsers.contains(user)) {
                chattableUsers.add(user);
            }
        }
        return chattableUsers;
    }

    // Get the total number of unread messages in
    // a chatroom with recipient.
    private Integer getNumUnread(String recipient) {
        Chatroom chatroom = chatroomManager.getChatroom(currentUser, recipient);
        return chatroom.getUnread(currentUser);
    }

    // Gets every message from a chatroom given their ids
    private ArrayList<ArrayList<String>> collectMessagesFromChatroom(ArrayList<Integer> ids, Chatroom chatroom) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (Integer id : ids) {
            ArrayList<String> messageData = new ArrayList<>();
            messageData.add(chatroom.getSender(id));
            messageData.add(chatroom.getDate(id));
            messageData.add(filterProfanity(chatroom.getMessage(id)));
            messageData.add(id.toString());
            result.add(messageData);
        }
        return result;
    }
}
