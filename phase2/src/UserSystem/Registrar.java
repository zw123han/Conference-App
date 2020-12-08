package UserSystem;

import java.util.ArrayList;
import java.lang.*;

import DatabaseSystem.*;
import com.sun.org.apache.xpath.internal.operations.Or;

/**
 * Registrar is the use case class for all interactions with User objects. It contains an array list of User objects
 * which represents all of the users that have created an account for the tech conference, and it contains an instance
 * of User which represents the current user that is using the conference app. Each user has a unique username which
 * this class uses to implement various methods.
 *
 * @author Jesse
 */
public class Registrar implements Savable {

    private ArrayList<User> users;
    private User currentUser;

    /**
     * Constructor used when loading from a file
     *
     * @param users     the list of users that were saved in a previous session
     */
    public Registrar(ArrayList<User> users) {
        this.users = users;
        this.currentUser = null;
    }

    /**
     * Constructor used when not loading from a file
     */
    public Registrar() {
        this.users = new ArrayList<>();
    }

    @Override
    public String getCollectionName() {
        return "registrar";
    }

    @Override
    public ConversionStrategy getConversionStrategy() {
        return new RegistrarConverter();
    }

    @Override
    public ParserStrategy getDocumentParserStrategy() {
        return new ParseToRegistrar();
    }

    /**
     * Returns true if the type of user specified can be created and false if it cannot be. If the specified user can
     * be created, creates a new User object and adds it to the list of users attending the conference.
     *
     * @param name          name of the user
     * @param userName      username for the user
     * @param password      password for the user
     * @param type          type of the user
     * @return              true if the type is valid and false if the type is unrecognized
     */
    public boolean createUser(String name, String userName, String password, String type) {
        User user;
        switch (type.toLowerCase()) {
            case "attendee":
                user = new Attendee(name, userName, password);
                break;
            case "organizer":
                user = new Organizer(name, userName, password);
                break;
            case "speaker":
                user = new Speaker(name, userName, password);
                break;
            case "administrator":
                user = new Administrator(name, userName, password);
                break;
            default:
                return false;
        }
        users.add(user);
        return true;
    }

    /**
     * Deletes a user from the registrar and from all its friends.
     *
     * @param username The username of the user to be removed.
     * @return True if and only if the user exists and was removed.
     */
    public boolean deleteUser(String username){
        for(User user: this.users){
            if(user.getUserName().equals(username)){
                for(User user1: this.users){
                    user1.removeFriend(username);
                }
                this.users.remove(user);
                return true;
            }
        }
        return false;
    }

    /**
     * Changes the type of a user with username to that of newType.
     *
     * @param username The username of the user to be promoted or demoted.
     * @param newType The new type of the user.
     * @return True if and only if the user's type is changed to a new type given by newType.
     */
    public boolean updateUserType(String username, String newType){
        for(User user: this.users){
            if(user.getUserName().equals(username)&&!user.getUserType().equals(newType)){
                if(newType.equals("attendee")){
                    Attendee newUser = (Attendee) user;
                    users.remove(user);
                    users.add(newUser);
                    return true;
                }
                else if(newType.equals("speaker")){
                    Speaker newUser = (Speaker) user;
                    users.remove(user);
                    users.add(newUser);
                    return true;
                }
                else if(newType.equals("organizer")){
                    Organizer newUser = (Organizer) user;
                    users.remove(user);
                    users.add(newUser);
                    return true;
                }
                else if(newType.equals("administrator")){
                    Administrator newUser = (Administrator) user;
                    users.remove(user);
                    users.add(newUser);
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }
    /**
     * Returns the User object attending the conference that has the given username. Returns null if there is no user
     * attending the conference with the given username
     *
     * @param userName      the username to be searched for
     * @return              the User object with the given username
     */
    public User getUserByUserName(String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Returns the class of user with a given username.
     *
     * @param userName      the username to be searched for
     * @return              a string representing the name of the class for the given username
     */
    public String getUserType(String userName) {
        User user = getUserByUserName(userName);
        return user.getClass().getName();
    }

    /**
     * Returns the list of users attending the conference.
     *
     * @return              the list of users attending the conference
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Returns a list of users attending the conference with a specific type (Attendee/Organizer/Speaker).
     *
     * @param type          the type of users to be returned
     * @return              a list of attendees/organizers/speakers
     */
    public ArrayList<User> getUsersByType(String type) {
        ArrayList<User> users = new ArrayList<>();
        for (User user : this.users) {
            if (this.getUserType(user.getUserName()).equals("UserSystem."+type)) {
                users.add(user);
            }
        }
        return users;
    }

    /**
     * Returns true if and only if there is a user attending the conference with the given username.
     *
     * @param username      the username to be searched for
     * @return              True of false.
     */
    public boolean userExisting(String username){
        return getUserByUserName(username) != null;
    }

    /**
     * Returns true if and only if the user with the given username is an organizer.
     *
     * @param username      the username of the user
     * @return              true or false
     */
    public boolean isOrganizer(String username) {
        if (userExisting(username)) {
            return getUserByUserName(username) instanceof Organizer;
        }
        return false;
    }

    /**
     * Returns true if and only if the user with the given username is an administrator.
     *
     * @param username      the username of the user
     * @return              true or false
     */
    public boolean isAdmin(String username) {
        if (userExisting(username)) {
            return getUserByUserName(username) instanceof Administrator;
        }
        return false;
    }

    /**
     * Returns true if and only if the user with the given username is a speaker.
     *
     * @param username      the username of the user
     * @return              true or false
     */
    public boolean isSpeaker(String username) {
        if (userExisting(username)) {
            return getUserByUserName(username) instanceof Speaker;
        }
        return false;
    }

    /**
     * Returns true if and only if the user with the given username is an attendee.
     *
     * @param username      the username of the user
     * @return              true or false
     */
    public boolean isAttendee(String username) {
        if (userExisting(username)) {
            return getUserByUserName(username) instanceof Attendee;
        }
        return false;
    }

    /**
     * Returns an array list of the usernames for the given user's friends.
     *
     * @param username      the username of the user
     * @return              a list of usernames
     */
    public ArrayList<String> getUserFriends(String username) {
        if (userExisting(username)) {
            return getUserByUserName(username).getFriends();
        }
        return new ArrayList<>();
    }

    /**
     * Returns true if and only if the two users are friends.
     *
     * @param username      the username of the user
     * @param friend        the username of the friend
     * @return              true or false
     */
    public boolean isFriend(String username, String friend) {
        return getUserByUserName(username).getFriends().contains(friend);
    }

    /**
     * Returns an array list of event ids that represent the given speaker's list of talks.
     *
     * @param username      the username of the speaker
     * @return              a list of talks the speaker is giving
     */
    public ArrayList<Long> getSpeakerTalks(String username) {
        if (isSpeaker(username)) {
            Speaker speaker = (Speaker) getUserByUserName(username);
            return speaker.getTalks();
        }
        return new ArrayList<>();
    }

    /**
     * Returns the name of the user associated with the given username. Returns an empty string if there is no
     * associated user.
     *
     * @param username      the username of the user
     * @return              the name of the user
     */
    public String getNameByUsername(String username) {
        if (userExisting(username)) {
            return getUserByUserName(username).getName();
        }
        return "";
    }

    /**
     * This method will return the current user logged in (currently using) registrar.
     *
     * @return          The current user logged into registrar.
     */
    public User getCurrentUser(){
        return this.currentUser;
    }

    /**
     * This method will set a user as the current user logged into (currently using) registrar.
     *
     * @param username The username of a user to set as the current user.
     */
    public void setCurrentUser(String username){
        this.currentUser = getUserByUserName(username);
    }

    /**
     * This method will set the current user logged into registrar as null.
     *
     */
    public void emptyCurrentUser(){
        this.currentUser = null;
    }
}