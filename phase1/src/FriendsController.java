import java.util.ArrayList;
import java.util.Scanner;

public class FriendsController {
    private FriendsManager friendsManager;
    private FriendsPresenter friendsPresenter;
    private Scanner sc = new Scanner(System.in);

    public FriendsController(Registrar registrar, FriendsPresenter friendsPresenter) {
        this.friendsManager = new FriendsManager(registrar);
        this.friendsPresenter = friendsPresenter;
    }
   public void addFriends(User user) {
        System.out.println(friendsPresenter.enterUser());
        String other_user = sc.nextLine();
        if (friendsManager.addFriend(user, other_user)) {
            System.out.println(friendsPresenter.userAdded());
        }
        if (!(friendsManager.addFriend(user, other_user)))
            if (!user.hasFriend(other_user)) {
                System.out.println(friendsPresenter.alreadyFriends());
            } else if (other_user.equals(user.getUserName())) {
                System.out.println(friendsPresenter.NoAddYourself());
            } else {
                System.out.println(friendsPresenter.userNotFound());
            }

    }
    public void removeFriends(User user) {
        System.out.println(friendsPresenter.enterUser());
        String other_user = sc.nextLine();
        if (friendsManager.removeFriend(user, other_user)) {
            System.out.println(friendsPresenter.userRemoved());
        } else {
            System.out.println(friendsPresenter.notYourFriend());
        }
    }

}
