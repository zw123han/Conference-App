import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class ChatPull {
    ChatroomManager chatroomManager;

    public ChatroomManager readChatlog(){
        ChatroomManager chatroomManager = new ChatroomManager();
        try {
            FileInputStream fileIn = new FileInputStream("phase1/src/chatlog.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            chatroomManager = (ChatroomManager) objectIn.readObject();
            fileIn.close();
            objectIn.close();
            System.out.println("Successfully read chat.");
        }catch(Exception ex){
            System.out.println("Chat read failed.");
        }
        return chatroomManager;
    }

}
