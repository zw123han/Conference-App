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
    private Registrar registrar;

    /**
     * (please describe)
     *
     * @param registrar     (please describe)
     */
    public LoginOptionsFacade(Registrar registrar){
        this.credentialsUseCase = new CredentialsUseCase(registrar);
        this.login = new Login(registrar);
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
        return login.attemptLogin(username, password);
    }

    /**
     * (please describe)
     *
     * @return      True or false.
     */
    public boolean logout(){
        return login.attemptLogout();
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public User getUser(){
        return login.getCurrentUser();}

    public Registrar getRegistrar(){
        return this.registrar;
    }
}
