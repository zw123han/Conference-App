import java.util.*;

public class Chatroom {
    private ArrayList<Message> history;

    public Chatroom(){;;
        this.history = new ArrayList<Message>();
    }

    public void sendMessage(Message message){
        history.add(message);
    }

    public ArrayList<Message> getHistory(){
        return history;
    }
}
