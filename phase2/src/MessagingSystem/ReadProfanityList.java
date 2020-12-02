package MessagingSystem;

import java.io.*;
import java.util.HashMap;

/**
 * For reading from a .ser file containing the saved HashMap of profanities and their replacement.
 *
 * @author Chrisee
 */
public class ReadProfanityList {
    private String filepath;

    /**
     * Initializes a new ReadProfanityList object.
     *
     * @param filepath      the filepath of the .ser file
     */
    public ReadProfanityList(String filepath){
        this.filepath = filepath;
    }

    /**
     * Reads a file and returns it as a Hashmap of profanities and their replacement.
     *
     * @return      the hashmap of profanities
     */
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
