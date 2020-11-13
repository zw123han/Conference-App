import java.util.*;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class Speaker extends User {

    private ArrayList<Long> talks;

    public Speaker(String name, String userName, String password) {
        super(name, userName, password);
        this.talks = new ArrayList<Long>();
    }

    public ArrayList<Long> getTalks() {
        return talks;
    }

    public void addTalk(Long talkId) {
        talks.add(talkId);
    }

}
