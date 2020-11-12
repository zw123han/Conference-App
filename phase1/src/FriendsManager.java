import java.util.*;

public class FriendsManager {
    private ArrayList<String> allUsers; // TODO: implement getUserNames() in Registrar

    public FriendsManager(Registrar registrar) {
        this.allUsers = registrar.getUserNames();
    }

    public boolean addFriend(User user, String other_user) {
        if (!user.hasFriend(other_user) && allUsers.contains(other_user)) {
            user.addFriend(other_user);
            return true;
        }
        return false;
    }

    public boolean removeFriend(User user, String other_user) {
        if (user.hasFriend(other_user)) {
            user.removeFriend(other_user);
            return true;
        }
        return false;
    }
}
