import java.io.FileReader;
import java.io.Reader;

public class ChatPull {
    ChatroomManager chatroomManager = new ChatroomManager();

    public void readChatlog(){
        Reader reader = new FileReader("chatlog.txt")

    }

    public ChatroomManager getChatroomManager(){
        return chatroomManager;
    }
}
