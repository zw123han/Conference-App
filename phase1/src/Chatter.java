import java.util.ArrayList;

public class Chatter {
    private Hashmap<ArrayList<User>, Chatroom> chatrooms;

    public Chatter(){
        this.chatrooms = new Hashmap<ArrayList<User>, Chatroom>();
    }

    public Chatroom getChatroom(ArrayList<User> users){
        if (chatroom.containsKey(users)){
            return chatrooms.get(users);
        }else{
            createChatroom(users);
            return chatrooms.get(users);
        }
    }

    public void createChatroom(ArrayList<User> users){
        chatrooms.put(users, new Chatroom(users.sort()));
    }

}
