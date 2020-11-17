import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * ReadUsers is a gateway class that reads from a .ser file.
 *
 * @author Jesse
 */
public class ReadUsers {
    private String filepath;

    /**
     * Initializes a newly created ReadUsers object to read from the given file.
     *
     * @param filepath      the path of the file to be read from
     */
    public ReadUsers(String filepath) {
        // Reads a txt/ser file then stores it
        this.filepath = filepath;

    }

    /**
     * Returns a list of User objects from the specified file.
     *
     * @return      a list of User objects from the specified file
     */
    public ArrayList<User> read() {
        ArrayList<User> users = new ArrayList<>();
        boolean isEmpty = false;
        try {
            FileInputStream fileIn = new FileInputStream(this.filepath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            while (!isEmpty) {
                User user = (User) in.readObject();
                if (user == null) {
                    isEmpty = true;
                } else {
                    users.add(user);
                }
            }
            in.close();
            fileIn.close();
        } catch (IOException e) {
            System.out.println("Error reading users from file");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
        return users;
    }
}
