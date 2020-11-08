import java.util.*;

public class Chatter {
    private HashMap<ArrayList<String>, Chatroom> chatrooms;

    public Chatter(){
        this.chatrooms = new HashMap<ArrayList<String>, Chatroom>();
    }

    public Chatroom getChatroom(ArrayList<String> usernames){
        Collections.sort(usernames);
        if (!chatrooms.containsKey(usernames)){
            createChatroom(usernames);
        }
        return chatrooms.get(usernames);
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
}
