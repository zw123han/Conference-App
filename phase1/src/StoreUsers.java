import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * StoreUsers is a gateway class that is used to save a list of the users attending the conference to a .ser file for
 * use in other login sessions.
 *
 * @author Jesse
 * @version %I%, %G%
 */
public class StoreUsers {

    private String filepath;

    /**
     * Initializes a newly created StoreUsers object to save inside the specified file
     *
     * @param filepath      the path of the file to save the users
     */
    public StoreUsers(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Returns true if and only if the method was able to successfully save the list of users to the given file path.
     *
     * @param userList      a list of User objects to serialized.
     * @return              true iff the save was successful
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
