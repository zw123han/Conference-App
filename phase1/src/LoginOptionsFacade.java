import java.util.Base64;
import java.util.Scanner;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class LoginOptionsFacade {
    private CredentialsUseCase credentialsUseCase;
    private Login login;
    private User user;

    /**
     * (please describe)
     *
     * @param registrar     (please describe)
     */
    public LoginOptionsFacade(Registrar registrar){
        this.credentialsUseCase = new CredentialsUseCase(registrar);
        this.login = new Login(registrar);
        this.user = null;
    }

    /**
     * (please describe)
     *
     * @param name          (please describe)
     * @param username      (please describe)
     * @param password      (please describe)
     * @param type          (please describe)
     * @return              True or false.
     */
    public boolean createUser(String name, String username, String password, String type){
        return credentialsUseCase.createUser(name, username, password, type);
    }

    /**
     * (please describe)
     *
     * @param username              (please describe)
     * @param currentPassword       (please describe)
     * @param newPassword           (please describe)
     * @return                      True or false.
     */
    public boolean resetPassword(String username, String currentPassword, String newPassword) {
        return credentialsUseCase.resetPassword(username, currentPassword, newPassword);
    }

    /**
     * (please describe)
     *
     * @param username      (please describe)
     * @param password      (please describe)
     * @return              True or false.
     */
    public boolean login(String username, String password){
        this.user = login.attemptLogin(username, password);
        return this.user != null;
    }

    /**
     * (please describe)
     *
     * @param username      (please describe)
     * @return              True or false.
     */
    public boolean userExists(String username) {
        return login.userExists(username);
    }

    /**
     * (please describe)
     *
     * @return      True or false.
     */
    public boolean logout(){
        if (this.user==null){
            return false;
        }
        this.user = null;
        return true;
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public User getUser(){
        return this.user;}
}
