package MessagingSystem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * For saving ChatroomManager to .ser file.
 *
 * @author Elliot
 */
public class StoreChat {
    private String filepath;

    /**
     * Initializes a new StoreChat object.
     *
     * @param filepath   the filepath of the .ser file
     */
    public StoreChat(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Saves ChatroomManager to .ser file
     *
     * @param chatroomManager   ChatroomManager
     */
    public void storeChat(ChatroomManager chatroomManager){
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(chatroomManager);
            objectOut.close();
            fileOut.close();
        }catch(IOException e){
            System.out.println("Chat saving failed.");
        }
    }
}
