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

    public ChatroomManager(){
        this.chatrooms = new HashMap<>();
    }

    public Chatroom getChatroom(ArrayList<String> usernames){
        Collections.sort(usernames);
        if (!chatrooms.containsKey(usernames)){
            createChatroom(usernames);
        }
        return chatrooms.get(usernames);
    }

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

    public HashMap<ArrayList<String>, Chatroom> getAllChatrooms(User user) {
        HashMap<ArrayList<String>, Chatroom> cms = new HashMap<>();
        for (ArrayList<String> key : chatrooms.keySet()) {
            if (key.contains(user.getUserName())) {
                cms.put(key, getChatroom(key));
            }
        }
        return cms;
    }

    public void createChatroom(ArrayList<String> usernames){
        chatrooms.put(usernames, new Chatroom());
    }

    public void sendOne(ArrayList<String> users, Message message) {
        getChatroom(users).sendMessage(message);
    }

    public void sendAll(Event event, Message message) {
        for (String user : event.getSignedUpUsers()) {
            ArrayList<String> recipients = new ArrayList<>();
            recipients.add(user);
            recipients.add(message.getSender());
            sendOne(recipients, message);
        }
    }

    public boolean hasChatroom(ArrayList<String> usernames) {
        Collections.sort(usernames);
        return chatrooms.containsKey(usernames);
    }

    public boolean hasChatroom(User user, String recipient) {
        ArrayList<String> recipients = new ArrayList<>();
        recipients.add(user.getUserName());
        recipients.add(recipient);
        Collections.sort(recipients);
        return chatrooms.containsKey(recipients);
    }
}
