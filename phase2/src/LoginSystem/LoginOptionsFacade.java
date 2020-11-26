package LoginSystem;

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

    /**
     * Initializes a new LoginSystem.LoginOptionsFacade.
     *
     * @param registrar     Registrar use case which stores users.
     */
    public LoginOptionsFacade(Registrar registrar){
        this.credentialsUseCase = new CredentialsUseCase(registrar);
        this.login = new Login(registrar);
        this.registrar = registrar;
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
