import java.lang.reflect.Array;
import java.util.ArrayList;

public class Chatroom {
    private ArrayList<User> users;
    private ArrayList<Message> history;

    public Chatroom(ArrayList<User> users){
        this.users = Array.sort(users);
        this.history = new ArrayList<Message>();
    }

    public void sendMessage(Message message){
        history.add(message);
    }
}
