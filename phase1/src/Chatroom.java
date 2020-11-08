import java.lang.reflect.Array;
import java.util.*;

public class Chatroom {
    private ArrayList<String> usernames;
    private ArrayList<Message> history;

    public Chatroom(ArrayList<String> usernames){
        Collections.sort(usernames);
        this.usernames = usernames;
        this.history = new ArrayList<Message>();
    }

    public void sendMessage(Message message){
        history.add(message);
    }
}
