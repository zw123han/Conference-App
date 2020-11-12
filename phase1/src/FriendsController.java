import java.util.ArrayList;
import java.util.Scanner;

public class FriendsController {
    private FriendsManager friendsManager;
    private FriendsPresenter friendsPresenter;
    private Registrar registrar;
    private Scanner sc = new Scanner(System.in);

    public FriendsController(Registrar registrar, FriendsPresenter friendsPresenter) {
        this.friendsManager = new FriendsManager(registrar);
        this.friendsPresenter = friendsPresenter;
    }
   public void addFriends(User user) {
        friendsPresenter.enterUser();
        String other_user = sc.nextLine();
        if (friendsManager.addFriend(user, other_user)) {
            System.out.println(friendsPresenter.userAdded());
        }
        if (!(friendsManager.addFriend(user, other_user)))
            if (!user.hasFriend(other_user)) {
                System.out.println(friendsPresenter.alreadyFriends());
            } else {
                System.out.println(friendsPresenter.userNotFound());
            }
    }
    public void removeFriends(User user) {
        friendsPresenter.enterUser();
        String other_user = sc.nextLine();
        if (friendsManager.removeFriend(user, other_user)) {
            System.out.println(friendsPresenter.notyourFriend());
        } else {
            System.out.println(friendsPresenter.userRemoved());
        }
    }

}
