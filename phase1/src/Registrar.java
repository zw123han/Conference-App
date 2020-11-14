import java.util.ArrayList;
import java.lang.*;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class Registrar {

    private ArrayList<User> users;
    private User currentUser;

    /**
     * Constructor used when loading from a file
     *
     * @param users     (please describe)
     */
    public Registrar(ArrayList<User> users) {
        this.users = users;
        this.currentUser = null;
    }

    /**
     * Constructor used when not loading from a file
     */
    public Registrar() {
        this.users = new ArrayList<>();
    }

    /**
     * (please describe)
     *
     * @param name          (please describe)
     * @param userName      (please describe)
     * @param password      (please describe)
     * @param type          (please describe)
     * @return              (please describe)
     */
    public boolean createUser(String name, String userName, String password, String type) {
        User user;
        switch (type.toLowerCase()) {
            case "attendee":
                user = new Attendee(name, userName, password);
                break;
            case "organizer":
                user = new Organizer(name, userName, password);
                break;
            case "speaker":
                user = new Speaker(name, userName, password);
                break;
            default:
                return false;
        }
        users.add(user);
        return true;
    }

    /**
     * (please describe)
     *
     * @param userName      (please describe)
     * @return              (please describe)
     */
    public User getUserByUserName(String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    /**
     * (please describe)
     *
     * @param userName      (please describe)
     * @return              (please describe)
     */
    public String getUserType(String userName) {
        User user = getUserByUserName(userName);
        return user.getClass().getName();
    }

    /**
     * (please describe)
     *
     * @return              (please describe)
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * (please describe)
     *
     * @param type          (please describe)
     * @return              (please describe)
     */
    public ArrayList<User> getUsersByType(String type) {
        ArrayList<User> users = new ArrayList<>();
        for (User user : this.users) {
            if (this.getUserType(user.getUserName()).equals(type)) {
                users.add(user);
            }
        }
        return users;
    }

    /**
     * (please describe)
     *
     * @param username      (please describe)
     * @return              True of false.
     */
    public boolean userExisting(String username){
        return getUserByUserName(username) != null;
    }

    /**
     * (please describe)
     *
     * @return              (please describe)
     */
    public ArrayList<String> getUserNames(){
        ArrayList<String> allUsernames = new ArrayList<>();
        for (User user : users){
            allUsernames.add(user.getUserName());
        }
        return allUsernames;
    }

    public boolean isOrganizer(String username) {
        if (userExisting(username)) {
            return getUserByUserName(username) instanceof Organizer;
        }
        return false;
    }

    public boolean isSpeaker(String username) {
        if (userExisting(username)) {
            return getUserByUserName(username) instanceof Speaker;
        }
        return false;
    }

    public boolean isAttendee(String username) {
        if (userExisting(username)) {
            return getUserByUserName(username) instanceof Attendee;
        }
        return false;
    }

    public ArrayList<String> getUserFriends(String username) {
        if (userExisting(username)) {
            return getUserByUserName(username).getFriends();
        }
        return new ArrayList<>();
    }

    public boolean isFriend(String username, String friend) {
        return getUserByUserName(username).getFriends().contains(friend);
    }

    public ArrayList<Long> getSpeakerTalks(String username) {
        if (isSpeaker(username)) {
            Speaker speaker = (Speaker) getUserByUserName(username);
            return speaker.getTalks();
        }
        return new ArrayList<>();
    }

    public String getNameByUsername(String username) {
        if (userExisting(username)) {
            return getUserByUserName(username).getName();
        }
        return "";
    }

    public User getCurrentUser(){
        return this.currentUser;
    }

    public void setCurrentUser(String username){
        this.currentUser = getUserByUserName(username);
    }

    public void emptyCurrentUser(){
        this.currentUser = null;
    }
}