import java.util.Scanner;

/**
 * (please describe)
 *
 * @author Tao
 */
public class FriendsController {
    private FriendsManager friendsManager;
    private FriendsPresenter friendsPresenter;
    private Scanner sc = new Scanner(System.in);

    /**
     * Instantiates FriendsController
     *
     * @param registrar             (please describe)
     * @param friendsPresenter      (please describe)
     */
    public FriendsController(Registrar registrar, FriendsPresenter friendsPresenter) {
        this.friendsManager = new FriendsManager(registrar);
        this.friendsPresenter = friendsPresenter;
    }

    /**
     * Prompts the user the input the username of the user they wish to add
     *
     * @param user      (please describe)
     */
   public void addFriends(User user) {
        System.out.println(friendsPresenter.enterUser());
        String other_user = sc.nextLine();
        if (friendsManager.addFriend(user, other_user)) {
            System.out.println(friendsPresenter.userAdded(other_user));
        }
        else {
            if (user.hasFriend(other_user)) {
                System.out.println(friendsPresenter.alreadyFriends());
            } else if (other_user.equals(user.getUserName())) {
                System.out.println(friendsPresenter.noAddYourself());
            } else {
                System.out.println(friendsPresenter.userNotFound());
            }
        }
    }

    /**
     * Prompts the user the enter the username of the friend they wish to remove
     *
     * @param user      (please describe)
     */
    public void removeFriends(User user) {
        System.out.println(friendsPresenter.enterUser());
        String other_user = sc.nextLine();
        if (friendsManager.removeFriend(user, other_user)) {
            System.out.println(friendsPresenter.userRemoved(other_user));
        } else {
            System.out.println(friendsPresenter.notYourFriend());
        }
    }

}
