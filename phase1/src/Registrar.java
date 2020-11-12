import java.util.ArrayList;
import java.lang.*;

public class Registrar {

    private ArrayList<User> users;

    //Constructor used when loading from a file
    public Registrar(ArrayList<User> users) {
        this.users = users;
    }

    //Constructor used when not loading from a file
    public Registrar() {
        this.users = new ArrayList<>();
    }

    public boolean createUser(String name, String userName, String password, String type) {
        User user;
        if (type.toLowerCase().equals("attendee")) {
            user = new Attendee(name, userName, password);
            //attendees.add(new Attendee(name, userName, password));
        } else if (type.toLowerCase().equals("organizer")) {
            user = new Organizer(name, userName, password);
            //organizers.add(new Organizer(name, userName, password));
        } else if (type.toLowerCase().equals("speaker")){
            user = new Speaker(name, userName, password);
            //speakers.add(new Speaker(name, userName, password));
        } else {
            return false;
        }
        users.add(user);
        return true;
    }

    public User getUserByUserName(String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public String getUserType(String userName) {
        User user = getUserByUserName(userName);
        return user.getClass().getName();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<User> getUsersByType(String type) {
        ArrayList<User> users = new ArrayList<>();
        for (User user : this.users) {
            if (this.getUserType(user.getUserName()).equals(type)) {
                users.add(user);
            }
        }
        return users;
    }

    public boolean userExisting(String username){
        return getUserByUserName(username) != null;
    }

}