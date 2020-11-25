import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * For saving ChatroomManager to .ser file.
 *
 * @author Elliot
 */
public class ChatPush {

    /**
     * Saves ChatroomManager to .ser file
     *
     * @param chatroomManager   ChatroomManager
     */
    public void storeChat(ChatroomManager chatroomManager){
        try {
            FileOutputStream fileOut = new FileOutputStream("phase2/src/chatlog.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(chatroomManager);
            objectOut.close();
            fileOut.close();
        }catch(IOException e){
            System.out.println("Chat saving failed.");
        }
    }
}
