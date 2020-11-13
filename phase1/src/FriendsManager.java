import java.util.*;
/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */

public class FriendsManager {
    private Registrar reg;

    public FriendsManager(Registrar reg) {
        this.reg = reg;
    }

    public boolean addFriend(User user, String username) {
        if (!user.hasFriend(username) && reg.userExisting(username) && !username.equals(user.getUserName())) {
            user.addFriend(username);
            User other = reg.getUserByUserName(username);
            other.addFriend(user.getUserName());
            return true;
        }
        return false;
    }

    public boolean removeFriend(User user, String username) {
        if (user.hasFriend(username)) {
            user.removeFriend(username);
            User other = reg.getUserByUserName(username);
            other.removeFriend(user.getUserName());
            return true;
        }
        return false;
    }
}