import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is an abstract class that will be used to represent all users. It will spawn subclasses.
 *
 * @author Fred, Jesse
 */
public abstract class User implements Serializable {
    private String name, userName, hashPassword;
    private ArrayList<Long> events;
    private ArrayList<String> friends;
    private boolean vip;

    /**
     * Constructor for the user class. Used to initialize variables.
     *
     * @param name              The user's name.
     * @param userName          The user's user name.
     * @param hashPassword      The user's password
     */
    public User(String name, String userName, String hashPassword){
        this.name = name;
        this.userName = userName;
        this.hashPassword = hashPassword;
        this.events = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    /**
     * A default constructor that was designed to be used to catch missing fields
     */
    public User(){
        System.out.println("Error. Missing: name, user name and password");
    }

    /**
     * Returns the user's name.
     *
     * @return The user's name.
     */
    public String getName(){ return name; }

    /**
     * Returns the user's username.
     *
     * @return The user's username.
     */

    public String getUserName(){ return userName; }

    /**
     * Returns the user's password.
     *
     * @return The user's password.
     */

    public String getPassword(){ return hashPassword; }

    /**
     * Sets a users password
     *
     * @param hashPassword      A string representing the user's password.
     */
    public void setPassword(String hashPassword){ this.hashPassword = hashPassword; }

    /**
     * Saves an event to a list of events for the user.
     *
     * @param evt       An event to of interest to the user.
     */
    public void addEvent(Long evt) { events.add(evt); }

    /**
     * Cancel events that the user no longer wishes to attend.
     *
     * @param evt       An event to be removed for the user.
     */
    public void removeEvent(Long evt) { events.remove(evt); }

    /**
     * Returns a list of desired events that the user would like to attend.
     *
     * @return      A list of events.
     */
    public ArrayList<Long> getEvents() {
        return events;
    }

    /**
     * Adds the names of users that this user would like to save as a friend.
     *
     * @param friend        The name of a user.
     */
    public void addFriend(String friend) { friends.add(friend); }

    /**
     * Removes a user that this user no longer wishes to retain as a friend.
     *
     * @param friend        The name of a user.
     */
    public void removeFriend(String friend) { friends.remove(friend); }

    /**
     * Returns a list of users that this user considers friends.
     *
     * @return      A list of saved friends.
     */
    public ArrayList<String> getFriends() { return friends; }

    /**
     * Checks to see if this user has a user saved as a friend.
     *
     * @param friend        The name of a user.
     * @return              True or false.
     */
    public boolean hasFriend(String friend) {
        return friends.contains(friend);
    }

    /**
     * Returns the VIP status of the user.
     *
     * @return True or False.
     */
    public boolean getVipStatus(){
        return vip;
    }

    /**
     * Sets the VIP status of the user. Will be either true or false.
     *
     * @param vipStatus     Status of the VIP field to set.
     */
    public void setVipStatus(boolean vipStatus){
        this.vip = vipStatus;
    }
}

