import java.util.ArrayList;

/**
 * (please describe)
 *
 * @author Tao,
 * @version %I%, %G%
 */
public class FriendsPresenter {
    /**
     * Indicates that the user does not exist
     *
     * @return      (please describe)
     */
    public String userNotFound() {
        return "This user does not exist. Please enter a valid user.";
    }

    /**
     * Informs the user that they cannot add themselves
     *
     * @return      (please describe)
     */
    public String noAddYourself() {return "Can't add yourself as a friend";}

    /**
     * Informs that the user is already friends with the other user
     *
     * @return      (please describe)
     */
    public String alreadyFriends() {return "Already friends with this user";}

    /**
     * Confirms that the user's desired friend has been added
     *
     * @param username      (please describe)
     * @return              (please describe)
     */
    public String userAdded(String username) {
        return username + " has been successfully added to your friends list.";
    }

    /**
     * Confirms that the other user has been removed from the user's friend's list
     *
     * @param username      (please describe)
     * @return              (please describe)
     */
    public String userRemoved(String username) {
        return username + " has been removed from your friends list.";
    }

    /**
     * Inform the user that they cannot remove a non friend
     *
     * @return              (please describe)
     */
    public String notYourFriend() {return "Can't remove non friends from friends list";}

    /**
     * Displays the string that asks the user the enter the username of the other user
     *
     * @return              (please describe)
     */
    public String enterUser() {
        return "Please enter the username";
    }

    /**
     * Displays the string that asks the user for if they want to add or remove a friend
     *
     * @return      (please describe)
     */
    public String addOrRemove() {
        return "Would you like to add or remove a friend? Enter 1 to add, 2 to remove. Enter $q to exit";
    }

    /**
     * Displays the friends of the user
     *
     * @param user      (please describe)
     */
    public void viewFriends(User user) {
        ArrayList<String> friends = user.getFriends();
        System.out.println("\nFriends");
        System.out.println("__________\n");
        if (friends.isEmpty()) {
            System.out.println("You got no friends hehe");
        }
        else {
            for (String friend : friends) {
                System.out.println(friend);
            }
        }
        System.out.println("\n");
    }
}
