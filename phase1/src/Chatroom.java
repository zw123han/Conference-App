import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;

public class Chatroom extends Observable {
    private ArrayList<User> users;
    private ArrayList<Message> history;

    public Chatroom(ArrayList<User> users){
        this.users = Array.sort(users);
        this.history = new ArrayList<Message>();
        for(User i : users){
            addObserver(i);
        }
    }

    public void sendMessage(Message message){
        history.add(message);
        setChanged();
    }
}
