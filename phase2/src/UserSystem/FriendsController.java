package UserSystem;

import java.util.ArrayList;

/**
 * Controller that manages a user's friends list.
 *
 * @author Tao
 */
public class FriendsController {
    private FriendsManager friendsManager;
    private FriendsPresenter friendsPresenter;
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
        if (friendsManager.addFriend(user, other_user)) {
            friendInterface.createPopUp(friendsPresenter.userAdded(other_user));
        }
        else {
            if (user.hasFriend(other_user)) {
                friendInterface.createPopUp(friendsPresenter.alreadyFriends());
            } else if (other_user.equals(user.getUserName())) {
                friendInterface.createPopUp(friendsPresenter.noAddYourself());
            } else {
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
        if (friendsManager.removeFriend(user, other_user)) {
            friendInterface.createPopUp(friendsPresenter.userRemoved(other_user));
        } else {
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
        if (friends.isEmpty()) {
            friendInterface.loadFriends("");
        }
        else {
            for (String friend : friends) {
                friendInterface.loadFriends(friend);
            }
        }
    }

    /**
     * Creates interface for UI
     */
    public interface FriendInterface {
        void loadFriends(String friend);
        void createPopUp(String message);
    }
}
