import java.io.*;
import java.util.ArrayList;

/**
 * SaveEvents is a gateway class that is used to save a list of the available talks at the conference to a .ser file for
 * use in other login sessions.
 *
 * @author Andy, Nithilan
 * @version %I%, %G%
 */
public class SaveEvents {
    private String filepath;

    /**
     * Initializes a newly created SaveEvents object to save inside the specified file
     *
     * @param filepath      the path of the file to save the events
     */
    public SaveEvents(String filepath) {
        this.filepath = filepath;

    }

    /**
     * Returns true if and only if the method was able to successfully save the list of events to the given file path.
     *
     * @param evList        a list of Event objects to be serialized.
     * @return              true iff the save was successful
     */
    public boolean saveEvents(ArrayList<Event> evList) {
        try {
            FileOutputStream streamOut = new FileOutputStream(this.filepath);
            ObjectOutputStream objectOS = new ObjectOutputStream(streamOut);
            objectOS.writeObject(evList);
            objectOS.close();
            streamOut.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error saving events to file");
        }
        return false;
    }

}
