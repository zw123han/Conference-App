import java.util.ArrayList;
import java.util.HashMap;

public class Chatter {
    private HashMap<ArrayList<User>, Chatroom> chatrooms;

    public Chatter(){
        this.chatrooms = new HashMap<ArrayList<User>, Chatroom>();
    }

    public Chatroom getChatroom(ArrayList<User> users){
        if (!chatrooms.containsKey(users)){
            createChatroom(users);
        }
        return chatrooms.get(users);
    }

    public void createChatroom(ArrayList<User> users){
        chatrooms.put(users, new Chatroom(users));
    }
}
