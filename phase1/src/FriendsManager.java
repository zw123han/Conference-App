import java.util.*;

public class FriendsManager {
    private User user;
    private ArrayList<String> allUsers;

    public FriendsManager(User user, ArrayList<String> allUsers) {
        this.user = user;
        this.allUsers = allUsers;
    }

    public boolean addFriend(String username) {
        if (!user.hasFriend(username) && allUsers.contains(username)) {
            user.addFriend(username);
            return true;
        }
        return false;
    }

    public boolean removeFriend(String username) {
        if (user.hasFriend(username)) {
            user.removeFriend(username);
            return true;
        }
        return false;
    }
}
