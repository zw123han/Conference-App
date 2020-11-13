import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class ChatPush {

    /**
     * (please describe)
     *
     * @param chatroomManager
     */
    public void storeChat(ChatroomManager chatroomManager){
        try {
            FileOutputStream fileOut = new FileOutputStream("phase1/src/chatlog.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(chatroomManager);
            objectOut.close();
            fileOut.close();
            System.out.println("Successfully saved chat.");
        }catch(IOException e){
            System.out.println("Chat saving failed.");
        }
    }
}
