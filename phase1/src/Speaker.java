import java.util.ArrayList;

public class Speaker extends User {

    private ArrayList<String> events = null;

    private ArrayList<String> talks = null;

    public ArrayList<String> getEvents(){ return events; } //return list of events this user is attending.

    public void setEvents(String event){ events.add(event); }

    public ArrayList<String> getTalks(){ return talks; } // return list of talks this user is giving

    public void setTalks(String talk){ talks.add(talk); }


}
