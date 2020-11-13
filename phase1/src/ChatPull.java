import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class ChatPull {
    ChatroomManager chatroomManager;

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public ChatroomManager readChatlog(){
        ChatroomManager chatroomManager = new ChatroomManager();
        try {
            FileInputStream fileIn = new FileInputStream("phase1/src/chatlog.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            chatroomManager = (ChatroomManager) objectIn.readObject();
            fileIn.close();
            objectIn.close();
            System.out.println("Successfully read chat.");
        }catch(IOException e){
            System.out.println("Chat read failed.");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
        return chatroomManager;
    }

}
