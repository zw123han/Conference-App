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

    /**
     * Constructor used when loading from a file
     *
     * @param users     (please describe)
     */
    public Registrar(ArrayList<User> users) {
        this.users = users;
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
        if (type.toLowerCase().equals("attendee")) {
            user = new Attendee(name, userName, password);
        } else if (type.toLowerCase().equals("organizer")) {
            user = new Organizer(name, userName, password);
        } else if (type.toLowerCase().equals("speaker")){
            user = new Speaker(name, userName, password);
        } else {
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
}