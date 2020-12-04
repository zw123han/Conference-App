package MessagingSystem;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;


public class StoreProfanitiesList {
    private String filepath;

    public StoreProfanitiesList(String filepath) {
        this.filepath = filepath;
    }

    public void storeProfanities(HashMap<String, String> profanities){
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(profanities);
            objectOut.close();
            fileOut.close();
        }catch(IOException e){
            System.out.println("Profanities list saving failed.");
        }
    }
}
