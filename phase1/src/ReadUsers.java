import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ReadUsers {

    public ArrayList<User> read() {
        ArrayList<User> users = new ArrayList<>();
        boolean isEmpty = false;
        try {
            FileInputStream fileIn = new FileInputStream("phase1/src/userData.ser");
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
            System.out.println("Error reading from file");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
            return null;
        }
        return users;
    }
}
