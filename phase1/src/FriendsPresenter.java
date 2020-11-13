import java.util.ArrayList;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class FriendsPresenter {
    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String userNotFound() {
        return "This user does not exist. Please enter a valid user.";
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String noAddYourself() {return "Can't add yourself as a friend";}

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String alreadyFriends() {return "Already friends with this user";}

    /**
     * (please describe)
     *
     * @param username      (please describe)
     * @return              (please describe)
     */
    public String userAdded(String username) {
        return username + " has been successfully added to your friends list.";
    }

    /**
     * (please describe)
     *
     * @param username      (please describe)
     * @return              (please describe)
     */
    public String userRemoved(String username) {
        return username + " has been removed from your friends list.";
    }

    /**
     * (please describe)
     *
     * @return              (please describe)
     */
    public String notYourFriend() {return "Can't remove non friends from friends list";}

    /**
     * (please describe)
     *
     * @return              (please describe)
     */
    public String enterUser() {
        return "Please enter the username";
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String addOrRemove() {
        return "Would you like to add or remove a friend? Enter 1 to add, 2 to remove. Enter $q to exit";
    }

    /**
     * (please describe)
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
