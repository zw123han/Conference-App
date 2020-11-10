import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadEvents {
    // Put into readUsers
//    private ArrayList<ArrayList<String>> namesTypes; // Each inner list is [name, type]
//    private ArrayList<Event> events;
    private String filepath;

    public ReadEvents(String filepath) {
        // Reads a txt/ser file then stores it
        this.filepath = filepath;

    }

    public List<Event> readEvents() {
        List<Event> events = new ArrayList<>();
        try {
            FileInputStream streamIn = new FileInputStream(this.filepath);
            ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
            events.addAll((List<Event>) objectinputstream.readObject());
            objectinputstream.close();
            streamIn.close();
            return events;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }


//    public ArrayList<Event> getEvents() {
//        return events;
//    }
//
//    public ArrayList<ArrayList<String>> getNamesTypes() {
//        return namesTypes;
//    }
//
//    // Maybe we should move the method below to another class.
//    // This method is used in ConferenceSimulator.
//    public ArrayList<String> findName(String name) {
//        for (ArrayList<String> nameType : namesTypes) {
//            if (nameType.get(0).equals(name)) {
//                return nameType;
//            }
//
//        }
//        return null;
//    }

}
