import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * ReadEvents is a gateway class that reads from a .ser file.
 *
 * @author Andy, Nithilan
 */
public class ReadEvents {
    private String filepath;

    /**
     * Initializes a newly created ReadEvents object to read from the given file.
     *
     * @param filepath      the path of the file to be read from
     */
    public ReadEvents(String filepath) {
        this.filepath = filepath;

    }

    /**
     * Returns a list of Event objects from the specified file.
     *
     * @return      a list of User objects from the specified file
     */
    public ArrayList<Event> read() {
        ArrayList<Event> events = new ArrayList<>();
        try {
            FileInputStream streamIn = new FileInputStream(this.filepath);
            ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
            events.addAll((ArrayList<Event>) objectinputstream.readObject());
            objectinputstream.close();
            streamIn.close();
            return events;
        } catch (IOException e) {
            System.out.println("Error reading events from file");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
        return events;
    }

}
