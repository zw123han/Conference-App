import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ChatPull {
    ChatroomManager chatroomManager;

    public ChatroomManager readChatlog(){
        ChatroomManager chatroomManager = new ChatroomManager();
        try {
            FileInputStream fileIn = new FileInputStream("chatlog.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            chatroomManager = (ChatroomManager) objectIn.readObject();
            System.out.println("Successfully read chat.");

        }catch(Exception ex){
            System.out.println("Chat read failed.");
        }
        return chatroomManager;
    }

}
