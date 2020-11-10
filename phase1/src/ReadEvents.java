import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadEvents {
    private ArrayList<ArrayList<String>> namesTypes; // Each inner list is [name, type]
    private ArrayList<Event> events;

    public ReadEvents(String filepath){
        // Reads a txt/ser file then stores it
        try {
            FileInputStream streamIn = new FileInputStream(filepath);
            ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
            this.events.addAll((List<Event>) objectinputstream.readObject());
            objectinputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void storeEvents(String filepath, ArrayList<Event> events){
        // Stores all event info into a txt/ser file
        // Have a separate storage for a ser file.
        try {
            FileOutputStream out = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(out);
            objectOut.writeObject(events);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public ArrayList<Event> getEvents(){
        return events;
    }

    public ArrayList<ArrayList<String>> getNamesTypes() {
        return namesTypes;
    }

    // Maybe we should move the method below to another class.
    // This method is used in ConferenceSimulator.
    public ArrayList<String> findName(String name){
        for(ArrayList<String> nameType: namesTypes){
            if(nameType.get(0).equals(name)){
                return nameType;
            }

        }
        return null;
    }

}
