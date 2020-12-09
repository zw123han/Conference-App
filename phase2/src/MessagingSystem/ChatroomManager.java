package MessagingSystem;

import DatabaseSystem.*;

import java.io.Serializable;
import java.util.*;

/**
 * Stores all Chatrooms in the program, deals with adding new Chatrooms and sending messages.
 *
 * @author Elliot, Chrisee
 */
public class ChatroomManager implements Serializable, Savable {
    private HashMap<ArrayList<String>, Chatroom> chatrooms;

    public ChatroomManager(HashMap<ArrayList<String>, Chatroom> chatrooms) { this.chatrooms = chatrooms; }

    /**
     * Constructor for Chatroom, creates new empty HashMap.
     */
    public ChatroomManager(){
        this.chatrooms = new HashMap<>();
    }

    @Override
    public String getCollectionName() {
        return "chatrooms";
    }

    @Override
    public ConversionStrategy getConversionStrategy() {
        return new ChatroomManagerConverter();
    }

    @Override
    public ParserStrategy getDocumentParserStrategy() {
        return new ParseToChatroomManager();
    }

    public HashMap<ArrayList<String>, Chatroom> getChatrooms() { return chatrooms; }

    /**
     * Get method for getting the Chatroom for a specific list of users.
     *
     * @param usernames     The list of usernames
     * @return              The Chatroom corresponding with the list of usernames
     */
    public Chatroom getChatroom(ArrayList<String> usernames){
        Collections.sort(usernames);
        if (!chatrooms.containsKey(usernames)){
            createChatroom(usernames);
        }
        return chatrooms.get(usernames);
    }

    /**
     * Get method for getting the Chatroom with two users.
     *
     * @param user          Username of one of the users
     * @param recipient     Username of the other user
     * @return              Chatroom with the two users
     */
    public Chatroom getChatroom(String user, String recipient){
        ArrayList<String> recipients = new ArrayList<>();
        recipients.add(user);
        recipients.add(recipient);
        Collections.sort(recipients);
        if (!chatrooms.containsKey(recipients)){
            createChatroom(recipients);
        }
        return chatrooms.get(recipients);
    }

    /**
     * Get method for all chatrooms that involve a specific user.
     *
     * @param username          Username of the user
     * @return                  HashMap of all Chatrooms involving user, with list of usernames as keys and chatroom as
     *                          values
     */
    public HashMap<ArrayList<String>, Chatroom> getAllChatrooms(String username) {
        HashMap<ArrayList<String>, Chatroom> cms = new HashMap<>();
        Set<ArrayList<String>> keys = chatrooms.keySet();
        for (ArrayList<String> key : keys) {
            if (key.contains(username)) {
                cms.put(key, getChatroom(key));
            }
        }
        return cms;
    }

    /**
     * Method for creating Chatroom with specific users.
     *
     * @param usernames     List of usernames of users
     */
    public void createChatroom(ArrayList<String> usernames){
        chatrooms.put(usernames, new Chatroom());
    }

    /**
     * Deletes Chatroom with specific users.
     *
     * @param usernames     list of usernames of users
     */
    private void deleteChatroom(ArrayList<String> usernames){
        chatrooms.remove(usernames);
    }

    public void deleteChatrooms(String username) {
        Set<ArrayList<String>> keys = chatrooms.keySet();
        for (ArrayList<String> key : keys) {
            if (key.contains(username)) {
                chatrooms.remove(key);
            }
        }
    }

    /**
     * Deletes Chatroom with specific users.
     *
     * @param username     username of the sender
     * @param recipient    username of the recipient
     * @param index        index of message
     */
    public void deleteMessage(String username, String recipient, int index){
        Chatroom chatroom = getChatroom(username, recipient);
        chatroom.deleteMessage(index);
        if(chatroom.getSize() == 0){
            ArrayList usernames = new ArrayList<>();
            usernames.add(username);
            usernames.add(recipient);
            Collections.sort(usernames);
            deleteChatroom(usernames);
        }
    }

    /**
     * Method for sending a message to a specific chatroom.
     *
     * @param users         List of usernames
     * @param message       Message to be sent
     * @param sender        Username of the sender
     */
    public void sendOne(ArrayList<String> users, String message, String sender) {
        for (String user : users) {
            if (!user.equals(sender)) {
                Message msg = new Message(message + " ", sender);
                getChatroom(users).sendMessage(msg);
            }
        }
    }

    /**
     * Method for sending a message to all chatrooms that involve users that are signed up for a specific event.
     *
     * @param users         an ArrayList fo recipients
     * @param message       Message to be sent
     */
    public void sendAll(ArrayList<String> users, String message, String sender) {
        for (String user : users) {
            if (!user.equals(sender)) {
                ArrayList<String> recipients = new ArrayList<>();
                recipients.add(user);
                recipients.add(sender);
                sendOne(recipients, message + " ", sender);
            }
        }
    }

    /**
     * Method for checking if a chatroom with specific users exists.
     *
     * @param user          Username of
     * @param recipient     Username of the other user
     * @return              True if chatroom exists, false otherwise
     */
    public boolean hasChatroom(String user, String recipient) {
        ArrayList<String> recipients = new ArrayList<>();
        recipients.add(user);
        recipients.add(recipient);
        Collections.sort(recipients);
        return chatrooms.containsKey(recipients);
    }

    /**
     * Updates all chatrooms containing prevUsername to contain newUsername instead.
     * For example, if a user changes their username from "user1" to "user2," then this
     * method can be used to update all instances of "user1" to "user2" so that the user
     * still has access to their message history.
     *
     * @param prevUsername    username current in use in chatrooms
     * @param newUsername     the uew username
     */
    public void updateChatroomUsername(String prevUsername, String newUsername) {
        Set<ArrayList<String>> keys = chatrooms.keySet();
        for (ArrayList<String> key : keys) {
            if (key.contains(prevUsername)) {
                Chatroom chatroom = chatrooms.get(key);
                chatroom.updateSenders(prevUsername, newUsername);
                ArrayList<String> newKey = new ArrayList<>();
                for (String name : key) {
                    if (!name.equals(prevUsername)) {
                        newKey.add(name);
                    }
                }
                newKey.add(newUsername);
                Collections.sort(newKey);
                chatrooms.put(newKey, chatroom);
            }
        }
        deleteChatrooms(prevUsername);
    }
}
