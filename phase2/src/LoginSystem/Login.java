package LoginSystem;

import UserSystem.Registrar;
import UserSystem.User;

import java.util.Arrays;
import java.util.Base64;

/**
 * Controller which logs users into the registrar.
 *
 * @author Ziwen
 */
public class Login {
    private Registrar registrar;

    /**
     * Initializes a new LoginSystem.Login.
     *
     * @param registrar     Registrar use case which stores users.
     */
    public Login(Registrar registrar){
        this.registrar = registrar;
    }

    private boolean loginAble(String username, String password){
        if (!(userExists(username))) {
            return false;
        }
        return Arrays.equals(Base64.getEncoder().encode(password.getBytes()), registrar.getUserByUserName(username).getPassword().getBytes());
    }

    private boolean userExists(String username) {
        return registrar.userExisting(username);
    }

    /**
     * Attempts to log a user into the registrar given credentials.
     *
     * @param username      The username of the user to be logged in.
     * @param password      The password of the user to be logged in.
     * @return              True or false. Returns true if and only if user is successfully logged in.
     */
    public boolean attemptLogin(String username, String password){
        if (loginAble(username, password)) {
            registrar.setCurrentUser(username);
            return true;
        }
        return false;
    }

    /**
     * Attempts to log a user out of the registrar.
     *
     * @return      True or false. Returns true if and only if a user is successfully logged out.
     */
    public boolean attemptLogout(){
        if (registrar.getCurrentUser()==null){
            return false;
        }
        registrar.emptyCurrentUser();
        return true;
    }

    /**
     * Returns the current user logged in.
     *
     * @return      The current user logged into registrar, or null if there is none.
     */
    public User getCurrentUser(){
        return registrar.getCurrentUser();
    }

}
