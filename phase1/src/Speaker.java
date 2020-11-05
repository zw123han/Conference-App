import java.util.ArrayList;

public class Speaker extends User {

    private ArrayList<Integer> talks;

    public ArrayList<Integer> getTalks(){ return talks; } // return list of talks this user is giving

    public void setTalks(Event talk){ talks.add(talk); }

}
