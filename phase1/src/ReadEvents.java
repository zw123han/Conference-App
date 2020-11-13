import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class ReadEvents {
    private String filepath;

    /**
     * (please describe)
     *
     * @param filepath      (please describe)
     */
    public ReadEvents(String filepath) {
        this.filepath = filepath;

    }

    /**
     * (please describe)
     *
     * @return      (please describe)
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
