import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class StoreUsers {

    private Registrar registrar;

    public StoreUsers(Registrar registrar) {
        this.registrar = registrar;
    }

    public void store() {
        try {
            FileOutputStream fileOut = new FileOutputStream("phase1/src/userData.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (User user : registrar.getUsers()) {
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
