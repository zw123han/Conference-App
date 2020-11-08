import java.util.ArrayList;

public class Registrar {

    private ArrayList<User> users;
    private int numUsers;

    public void createUser(String name, String userName, String password) {
        users.add(new User(name, userName, password));
        numUsers++;
    }

    public User getUserByUserName(String userName) {
        for (User user : users) {
            if (user.getUsername()==userName) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<User> getUsersByType(String type) {
        ArrayList<User> arr = new ArrayList<User>();
        for (User user : users) {
            if (user instanceof Class.forName(type)) {
                arr.add(user);
            }
        }
        return arr;
    }

    public boolean userExisting(String username){
        return getUserByUserName(username) != null;
    }

}