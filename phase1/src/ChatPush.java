import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class ChatPush {

    public void storeChat(ChatroomManager chatroomManager){
        try {
            FileOutputStream fileOut = new FileOutputStream("chatlog.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(chatroomManager);
            objectOut.close();
            fileOut.close();
            System.out.println("Successfully saved chat.");
        }catch(Exception e){
            System.out.println("Chat saving failed.");
        }
    }
}
