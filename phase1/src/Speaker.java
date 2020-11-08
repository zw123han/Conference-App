import java.util.ArrayList;

public class Speaker extends User {

    private ArrayList<Long> talks;

    public Speaker(String name, String userName, String password) {
        super(name, userName, password);
    }

    public ArrayList<Long> getTalks(){ return talks; } // return list of talks this user is giving

    public void setTalks(Long talkId){ talks.add(talkId); }

}
