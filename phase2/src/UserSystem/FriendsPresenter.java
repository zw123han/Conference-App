package UserSystem;

/**
 * Contains the text display for interacting with the user for managing a users friend's list.
 *
 * @author Tao
 */
public class FriendsPresenter {
    /**
     * Indicates that the user does not exist
     *
     * @return      a string message
     */
    public String userNotFound() {
        return "This user does not exist. Please enter a valid user.";
    }

    /**
     * Informs the user that they cannot add themselves
     *
     * @return      a string message
     */
    public String noAddYourself() {return "Can't add yourself as a friend";}

    /**
     * Informs that the user is already friends with the other user
     *
     * @return      a string message
     */
    public String alreadyFriends() {return "Already friends with this user";}

    /**
     * Confirms that the user's desired friend has been added
     *
     * @param username      the username of a friend to add
     * @return              a string message
     */
    public String userAdded(String username) {
        return username + " has been successfully added to your friends list.";
    }

    /**
     * Confirms that the other user has been removed from the user's friend's list
     *
     * @param username      the username of a friend to remove
     * @return              a string message
     */
    public String userRemoved(String username) {
        return username + " has been removed from your friends list.";
    }

    /**
     * Inform the user that they cannot remove a non friend
     *
     * @return              a string message
     */
    public String notYourFriend() {return "Can't remove non friends from friends list";}
}
