import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class StoreUsers {

    private String filepath;

    /**
     * (please describe)
     *
     * @param filepath      (please describe)
     */
    public StoreUsers(String filepath) {
        this.filepath = filepath;
    }

    /**
     * (please describe)
     *
     * @param userList      (please describe)
     */
    public boolean store(ArrayList<User> userList) {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.filepath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (User user : userList) {
                out.writeObject(user);
            }
            out.writeObject(null); //marks the end of the ser file
            out.close();
            fileOut.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error saving users to file");
        }
        return false;
    }

}
