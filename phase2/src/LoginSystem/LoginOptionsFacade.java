package LoginSystem;

import EventSystem.Event;
import EventSystem.EventManager;
import MessagingSystem.ChatroomManager;
import UserSystem.CredentialsUseCase;
import UserSystem.Registrar;
import UserSystem.User;

/**
 * Facade controller which gives various options relating to managing stored users.
 *
 * @author Ziwen
 */
public class LoginOptionsFacade {
    private CredentialsUseCase credentialsUseCase;
    private Login login;
    private Registrar registrar;
    private EventManager eventManager;
    private ChatroomManager chatroomManager;

    /**
     * Initializes a new LoginSystem.LoginOptionsFacade.
     *
     * @param registrar     Registrar use case which stores users.
     * @param eventManager  EventManager which stores events.
     * @param chatroomManager ChatroomManager which stores chat rooms.
     */
    public LoginOptionsFacade(Registrar registrar, EventManager eventManager, ChatroomManager chatroomManager){
        this.credentialsUseCase = new CredentialsUseCase(registrar);
        this.login = new Login(registrar);
        this.registrar = registrar;
        this.eventManager = eventManager;
        this.chatroomManager = chatroomManager;
    }

    /**
     * Updates the type of user everywhere, and removes them as speakers if they were a speaker type.
     * Users cannot update their own type.
     *
     * @param username The username of the user to be updated.
     * @param newType The newType to be assigned to that user.
     * @return True if and only if the type of the user is updated to a different type newType.
     */
    public boolean updateUserType(String username, String newType){
        if(this.getUser().getUserName().equals(username)){
            return false;
        }
        if(registrar.updateUserType(username, newType)){
            eventManager.removeSpeaker(username);
            return true;
        }
        return false;
    }

    /**
     * Updates the username of a user.
     *
     * @param username The old username of the user.
     * @param newUsername The new username of the user.
     * @return True if and only if a user with the username was changed to have newUsername.
     */
    public boolean updateUsername(String username, String newUsername){
        if(credentialsUseCase.updateUsername(username, newUsername)){
            eventManager.updateUsername(username, newUsername);
            chatroomManager.updateChatroomUsername(username, newUsername);
            return true;
        }
        return false;
    }

    /**
     * Sets the name of a user to a new name.
     *
     * @param username The username of the user.
     * @param name The new name of the user.
     * @return True if and only if the name was successfully changed.
     */
    public boolean updateName(String username, String name){
        return registrar.updateName(username, name);
    }

    /**
     * Deletes a user completely from the system. Users cannot delete themselves.
     *
     * @param username The username of the user to be deleted.
     * @return True if and only if a user with the username was deleted.
     */
    public boolean deleteUser(String username){
        if(this.getUser().getUserName().equals(username)){
            return false;
        }
        if(registrar.deleteUser(username)){
            eventManager.deleteUser(username);
            chatroomManager.deleteChatrooms(username);
            return true;
        }
        return false;
    }

    /**
     * Creates a new user in the registrar.
     *
     * @param name              Name of the user.
     * @param username          Username of the user.
     * @param password          Password of the user.
     * @param type              Type of user.
     * @return                  True or false. Returns true if and only if a new user is created with valid credentials.
     */
    public boolean createUser(String name, String username, String password, String type){
        return credentialsUseCase.createUser(name, username, password, type);
    }

    /**
     * Resets the password of a user.
     *
     * @param username          The username of an existing user.
     * @param currentPassword   The current password of the user.
     * @param newPassword       The new password of the user.
     * @return                  True or false. Returns true if and only if password is successfully reset.
     */
    public boolean resetPassword(String username, String currentPassword, String newPassword) {
        return credentialsUseCase.resetPassword(username, currentPassword, newPassword);
    }

    /**
     * Attempts to log a user into the registrar given credentials.
     *
     * @param username      The username of the user to be logged in.
     * @param password      The password of the user to be logged in.
     * @return              True or false. Returns true if and only if user is successfully logged in.
     */
    public boolean login(String username, String password){
        return login.attemptLogin(username, password);
    }


    /**
     * Attempts to log a user out of the registrar.
     *
     * @return      True or false. Returns true if and only if a user is successfully logged out.
     */
    public boolean logout(){
        return login.attemptLogout();
    }

    /**
     * Returns the current user logged in.
     *
     * @return      The current user logged into registrar, or null if there is none.
     */
    public User getUser(){
        return login.getCurrentUser();}
    /**
     * Returns the registrar.
     *
     * @return      The registrar use case which contains all the users.
     */
    public Registrar getRegistrar(){
        return this.registrar;
    }
}
