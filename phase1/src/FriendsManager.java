import java.util.*;

public class FriendsManager {
    private User user;
    private Registrar reg;

    public FriendsManager(User user, Registrar reg) {
        this.user = user;
        this.reg = reg;
    }

    public boolean addFriend(String username) {
        if (!user.hasFriend(username) && reg.userExisting(username) && !username.equals(user.getUserName())) {
            user.addFriend(username);
            User other = reg.getUserByUserName(username);
            other.addFriend(user.getUserName());
            return true;
        }
        return false;
    }

    public boolean removeFriend(String username) {
        if (user.hasFriend(username)) {
            user.removeFriend(username);
            User other = reg.getUserByUserName(username);
            other.removeFriend(user.getUserName());
            return true;
        }
        return false;
    }
}
