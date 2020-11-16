/**
 * Use case class that stores and removes the names of users to another user's friends list.
 *
 * @author Tao
 */

public class FriendsManager {
    private Registrar reg;

    /**
     * Instantiates a FriendsManager object
     *
     * @param reg       an instance of registrar, which will be used to manage users
     */
    public FriendsManager(Registrar reg) {
        this.reg = reg;
    }

    /**
     * Adds the the user with username "username" to "user"'s friends list
     *
     * @param user          represents a user whose friends list will be modified
     * @param username      represents the username who will be added to a friends list
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
     * @param user          represents a user whose friends list will be modified
     * @param username      represents the username who will be removed from a friends list
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