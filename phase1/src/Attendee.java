import java.util.ArrayList;

public class Attendee extends User {

    private ArrayList<String> friends = null;

    public ArrayList<String> getFriends() { return friends; }

    public void setFriends(String friend){ friends.add(friend); }

    }
