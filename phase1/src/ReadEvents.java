import java.util.ArrayList;

public class ReadEvents {
    // Put into readUsers
    private ArrayList<ArrayList<String>> namesTypes; // Each inner list is [name, type]
    private ArrayList<Event> events;

    public ReadEvents(String filepath){
        // Reads a txt/ser file then stores it
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
