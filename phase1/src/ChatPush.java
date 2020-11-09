import java.io.FileWriter;
import java.io.Writer;

public class ChatPush {
    public void clearChat(){
        Writer writer = new FileWriter("chatlog.txt");
    }

    public void storeChat(){
        Writer writer = new FileWriter("chatlog.txt");
        clearChat();
    }
}
