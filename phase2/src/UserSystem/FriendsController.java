package UserSystem;

import EventSystem.EventSignupPresenter;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controller that manages a user's friends list.
 *
 * @author Tao
 */
public class FriendsController {
    private FriendsManager friendsManager;
    private FriendsPresenter friendsPresenter;
    private Scanner sc = new Scanner(System.in);
    private FriendInterface friendInterface;

    public void setInterface(FriendsController.FriendInterface friendInterface) {
        this.friendInterface = friendInterface;
    }
    /**
     * Instantiates FriendsController
     *
     * @param registrar             registrar works to pass a user to the friendsManager class for operation
     * @param friendsPresenter      initiate an instance of the presenter class for interaction with a user
     */
    public FriendsController(Registrar registrar, FriendsPresenter friendsPresenter) {
        this.friendsManager = new FriendsManager(registrar);
        this.friendsPresenter = friendsPresenter;
    }

    /**
     * Prompts the user the input the username of the user they wish to add
     *
     * @param user      represents a user to be added to list of friends
     */
   public void addFriends(User user, String other_user) {
//        System.out.println(friendsPresenter.enterUser());
//        String other_user = sc.nextLine();
        if (friendsManager.addFriend(user, other_user)) {
//            System.out.println(friendsPresenter.userAdded(other_user));
            friendInterface.createPopUp(friendsPresenter.userAdded(other_user));
        }
        else {
            if (user.hasFriend(other_user)) {
//                System.out.println(friendsPresenter.alreadyFriends());
                friendInterface.createPopUp(friendsPresenter.alreadyFriends());
            } else if (other_user.equals(user.getUserName())) {
//                System.out.println(friendsPresenter.noAddYourself());
                friendInterface.createPopUp(friendsPresenter.noAddYourself());
            } else {
//                System.out.println(friendsPresenter.userNotFound());
                friendInterface.createPopUp(friendsPresenter.userNotFound());
            }
        }
    }

    /**
     * Prompts the user the enter the username of the friend they wish to remove
     *
     * @param user      represents a user to be removed from a list of friends
     */
    public void removeFriends(User user, String other_user) {
//        System.out.println(friendsPresenter.enterUser());
//        String other_user = sc.nextLine();
        if (friendsManager.removeFriend(user, other_user)) {
//            System.out.println(friendsPresenter.userRemoved(other_user));
            friendInterface.createPopUp(friendsPresenter.userRemoved(other_user));
        } else {
//            System.out.println(friendsPresenter.notYourFriend());
            friendInterface.createPopUp(friendsPresenter.notYourFriend());
        }
    }
    /**
     * Displays the friends of the user
     *
     * @param user      the user whose list will be displayed
     */
    public void viewFriends(User user) {
        ArrayList<String> friends = user.getFriends();
//        System.out.println("\nFriends");
//        System.out.println("__________\n");
        if (friends.isEmpty()) {
//            System.out.println("You got no friends hehe");
            friendInterface.loadFriends("");
        }
        else {
            for (String friend : friends) {
//                System.out.println(friend);
                friendInterface.loadFriends(friend);
            }

        }
//        System.out.println("\n");
    }

    public interface FriendInterface {
        public void loadFriends(String friend);
        public void createPopUp(String message);
    }
}
