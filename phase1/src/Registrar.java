import java.util.ArrayList;
import java.lang.*;

public class Registrar {

//    private ArrayList<Attendee> attendees;
//    private ArrayList<Organizer> organizers;
//    private ArrayList<Speaker> speakers;
    private ArrayList<User> users;

    //Constructor used when loading from a file
    public Registrar(ArrayList<User> users) {
        this.users = users;
    }

    //Constructor used when not loading from a file
    public Registrar() {
        this.users = new ArrayList<User>();
    }

    public boolean createUser(String name, String userName, String password, String type) {
        User user;
        if (type.toLowerCase() == "attendee") {
            user = new Attendee(name, userName, password);
            //attendees.add(new Attendee(name, userName, password));
        } else if (type.toLowerCase() == "organizer") {
            user = new Organizer(name, userName, password);
            //organizers.add(new Organizer(name, userName, password));
        } else if (type.toLowerCase() == "speaker"){
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
            if (user.getUserName()==userName) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

//    public ArrayList<User> getUsersByType(String type) {
//        if (type.toLowerCase() == "attendee") {
//            return attendees;
//        } else if (type.toLowerCase() == "organizer") {
//            return organizers;
//        } else if (type.toLowerCase() == "speaker") {
//            return speakers;
//        }
//        return null
//    }

    public boolean userExisting(String username){
        return getUserByUserName(username) != null;
    }

}