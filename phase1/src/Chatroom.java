import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;

public class Chatroom {
    private ArrayList<String> usernames;
    private ArrayList<Message> history;

    public Chatroom(ArrayList<String> usernames){
        this.usernames = Array.sort(usernames);
        this.history = new ArrayList<Message>();
    }

    public void sendMessage(Message message){
        history.add(message);
    }
}
