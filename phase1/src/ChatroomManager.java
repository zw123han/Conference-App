import java.io.Serializable;
import java.util.*;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class ChatroomManager implements Serializable {
    private HashMap<ArrayList<String>, Chatroom> chatrooms;

    /**
     * (please describe)
     */
    public ChatroomManager(){
        this.chatrooms = new HashMap<>();
    }

    /**
     * (please describe)
     *
     * @param usernames     (please describe)
     * @return              (please describe)
     */
    public Chatroom getChatroom(ArrayList<String> usernames){
        Collections.sort(usernames);
        if (!chatrooms.containsKey(usernames)){
            createChatroom(usernames);
        }
        return chatrooms.get(usernames);
    }

    /**
     * (please describe)
     *
     * @param user          (please describe)
     * @param recipient     (please describe)
     * @return              (please describe)
     */
    public Chatroom getChatroom(User user, String recipient){
        ArrayList<String> recipients = new ArrayList<>();
        recipients.add(user.getUserName());
        recipients.add(recipient);
        Collections.sort(recipients);
        if (!chatrooms.containsKey(recipients)){
            createChatroom(recipients);
        }
        return chatrooms.get(recipients);
    }

    /**
     * (please describe)
     *
     * @param user          (please describe)
     * @return              (please describe)
     */
    public HashMap<ArrayList<String>, Chatroom> getAllChatrooms(User user) {
        HashMap<ArrayList<String>, Chatroom> cms = new HashMap<>();
        for (ArrayList<String> key : chatrooms.keySet()) {
            if (key.contains(user.getUserName())) {
                cms.put(key, getChatroom(key));
            }
        }
        return cms;
    }

    /**
     * (please describe)
     *
     * @param usernames     (please describe)
     */
    public void createChatroom(ArrayList<String> usernames){
        chatrooms.put(usernames, new Chatroom());
    }

    /**
     * (please describe)
     *
     * @param users         (please describe)
     * @param message       (please describe)
     */
    public void sendOne(ArrayList<String> users, Message message) {
        getChatroom(users).sendMessage(message);
    }

    /**
     * (please describe)
     *
     * @param event         (please describe)
     * @param message       (please describe)
     */
    public void sendAll(Event event, Message message) {
        for (String user : event.getSignedUpUsers()) {
            ArrayList<String> recipients = new ArrayList<>();
            recipients.add(user);
            recipients.add(message.getSender());
            sendOne(recipients, message);
        }
    }

    /**
     * (please describe)
     *
     * @param usernames     (please describe)
     * @return              (please describe)
     */
    public boolean hasChatroom(ArrayList<String> usernames) {
        Collections.sort(usernames);
        return chatrooms.containsKey(usernames);
    }

    /**
     * (please describe)
     *
     * @param user          (please describe)
     * @param recipient     (please describe)
     * @return              (please describe)
     */
    public boolean hasChatroom(User user, String recipient) {
        ArrayList<String> recipients = new ArrayList<>();
        recipients.add(user.getUserName());
        recipients.add(recipient);
        Collections.sort(recipients);
        return chatrooms.containsKey(recipients);
    }
}
