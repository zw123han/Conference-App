import java.io.Serializable;
import java.util.*;

public class ChatroomManager implements Serializable {
    private HashMap<ArrayList<String>, Chatroom> chatrooms;

    public ChatroomManager(){
        this.chatrooms = new HashMap<ArrayList<String>, Chatroom>();
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
