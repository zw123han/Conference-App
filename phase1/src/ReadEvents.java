import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

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
