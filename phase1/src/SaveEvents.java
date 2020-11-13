import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class SaveEvents {
    private String filepath;

    /**
     * (please describe)
     *
     * @param filepath      (please describe)
     */
    public SaveEvents(String filepath) {
        this.filepath = filepath;

    }

    /**
     * (please describe)
     *
     * @param evList        (please describe)
     */
    public void saveEvents(ArrayList<Event> evList) {
        try {
            FileOutputStream streamOut = new FileOutputStream(this.filepath);
            ObjectOutputStream objectOS = new ObjectOutputStream(streamOut);
            objectOS.writeObject(evList);
            objectOS.close();
            streamOut.close();
        } catch (IOException e) {
            System.out.println("Error saving to file. Pathway does not exist.");
        }
    }

}
