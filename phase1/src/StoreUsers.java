import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StoreUsers {

    private String filepath;

    public StoreUsers(String filepath) {
        this.filepath = filepath;
    }

    public void store(ArrayList<User> userList) {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.filepath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (User user : userList) {
                out.writeObject(user);
            }
            out.writeObject(null); //marks the end of the ser file
            out.close();
            fileOut.close();
            System.out.println("Save successful");
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
    }

}
