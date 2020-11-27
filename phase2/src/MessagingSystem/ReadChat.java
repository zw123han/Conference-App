package MessagingSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * For reading from a .ser file containing the saved ChatroomManager.
 *
 * @author Elliot
 */
public class ReadChat {

    /**
     * Reads a file and returns it as a ChatroomManager
     *
     * @return      ChatroomManager read from .ser file
     */
    public ChatroomManager readChatlog(){
        ChatroomManager chatroomManager = new ChatroomManager();
        try {
            FileInputStream fileIn = new FileInputStream("phase2/src/MessagingSystem/chatlog.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            chatroomManager = (ChatroomManager) objectIn.readObject();
            fileIn.close();
            objectIn.close();
        }catch(IOException e){
            System.out.println("Chat read failed.");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
        return chatroomManager;
    }

}
