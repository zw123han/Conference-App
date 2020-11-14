import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * For saving ChatroomManager to .ser file.
 *
 * @author Elliot
 * @version %I%, %G%
 */
public class ChatPush {

    /**
     * Saves ChatroomManager to .ser file
     *
     * @param chatroomManager   ChatroomManager
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
