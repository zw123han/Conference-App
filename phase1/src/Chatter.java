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
        chatrooms.put(usernames, new Chatroom(usernames));
    }
    public void send(ArrayList<String> users, Message message) {
        if (!chatrooms.containsKey(users)) {
            getChatroom(users).sendMessage(message);
        }
    }
}
