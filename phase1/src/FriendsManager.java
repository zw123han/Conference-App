/**
 * (please describe)
 *
 * @author Tao
 */

public class FriendsManager {
    private Registrar reg;

    /**
     * Instantiates a FriendsManager object
     *
     * @param reg       (please describe)
     */
    public FriendsManager(Registrar reg) {
        this.reg = reg;
    }

    /**
     * Adds the the user with username "username" to "user"'s friends list
     *
     * @param user          (please describe)
     * @param username      (please describe)
     * @return              True or false.
     */
    public boolean addFriend(User user, String username) {
        if (!user.hasFriend(username) && reg.userExisting(username) && !username.equals(user.getUserName())) {
            user.addFriend(username);
            User other = reg.getUserByUserName(username);
            other.addFriend(user.getUserName());
            return true;
        }
        return false;
    }

    /**
     * Removes the the user with username "username" from "user"'s friends list
     *
     * @param user          (please describe)
     * @param username      (please describe)
     * @return              True or false.
     */
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