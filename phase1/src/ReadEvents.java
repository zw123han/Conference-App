import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ReadEvents {
    private String filepath;

    public ReadEvents(String filepath) {
        this.filepath = filepath;

    }

    public ArrayList<Event> read() {
        ArrayList<Event> events = new ArrayList<>();
        try {
            FileInputStream streamIn = new FileInputStream(this.filepath);
            ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
            events.addAll((ArrayList<Event>) objectinputstream.readObject());
            objectinputstream.close();
            streamIn.close();
            return events;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

}
