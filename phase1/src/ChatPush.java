import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class ChatPush {
    public void clearChat(){

    }

    public void storeChat(ChatroomManager chatroomManager){
        try {
            clearChat();
            FileOutputStream fileOut = new FileOutputStream("chatlog.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(chatroomManager);
            objectOut.close();
        }catch(Exception e){

        }
    }
}
