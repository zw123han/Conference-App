import java.util.ArrayList;
import java.util.HashMap;

public class Chatter {
    private HashMap<ArrayList<String>, Chatroom> chatrooms;

    public Chatter(){
        this.chatrooms = new HashMap<ArrayList<String>, Chatroom>();
    }

    public Chatroom getChatroom(ArrayList<String> username){
        if (!chatrooms.containsKey(username)){
            createChatroom(username);
        }
        return chatrooms.get(usernames);
    }

    public void createChatroom(ArrayList<String> usernames){
        chatrooms.put(usernames, new Chatroom(usernames));
    }
}
