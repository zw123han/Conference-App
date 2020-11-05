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
        User[] arr = new User[numUsers];
        for (User user : users) {
            if (user instanceof Class.forName(type)) {
                arr.add(user);
            }
        }
        return arr;
    }

}