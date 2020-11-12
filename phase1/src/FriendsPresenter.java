import java.util.ArrayList;

public class FriendsPresenter {
    public String userNotFound() {
        return "This user does not exist. Please enter a valid user.";
    }
    public String NoAddYourself() {return "Can't add yourself as a friend";}
    public String alreadyFriends() {return "Already friends with this user";}
    public String userAdded() {
        return "User has been successfully added to your friends list.";
    }
    public String userRemoved() {
        return "User has been removed from your friends list.";
    }
    public String notYourFriend() {return "Can't remove non friends from friends list";}
    public String enterUser() {
        return "Please enter the username";
    }
    public String AddOrRemove() {
        return "Would you like to add or remove a friend? Enter 1 to add, 2 to remove. Enter $q to exit";
    }

    public void viewFriends(User user) {
        ArrayList<String> friends = user.getFriends();
        System.out.println("Friends");
        System.out.println("__________\n");
        if (friends.isEmpty()) {
            System.out.println("You got no friends hehe");
        }
        else {
            for (String friend : friends) {
                System.out.println(friend);
            }
        }
    }
}
