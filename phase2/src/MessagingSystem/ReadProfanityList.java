package MessagingSystem;

import java.io.*;
import java.util.HashMap;

public class ReadProfanityList {
    private String filepath;

    public ReadProfanityList(String filepath){
        this.filepath = filepath;
    }

    public HashMap<String, String> readProfanities(){
        HashMap<String, String> profanities = new HashMap<>();
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            profanities = (HashMap<String, String>) objectIn.readObject();
            fileIn.close();
            objectIn.close();
        } catch (ClassNotFoundException e) {
                System.out.println("Class not found");
        } catch (IOException e) {
            System.out.println("Chat read failed.");
        }
        return profanities;
    }
}
