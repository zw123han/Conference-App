import java.util.Arrays;
import java.util.Base64;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class Login {
    private Registrar registrar;

    /**
     * (please describe)
     *
     * @param registrar     (please describe)
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
     * (please describe)
     *
     * @param username      (please describe)
     * @param password      (please describe)
     * @return              (please describe)
     */
    public boolean attemptLogin(String username, String password){
        if (loginAble(username, password)) {
            registrar.setCurrentUser(username);
            return true;
        }
        return false;
    }

    /**
     * (please describe)
     *
     * @return      True or false.
     */
    public boolean attemptLogout(){
        if (registrar.getCurrentUser()==null){
            return false;
        }
        registrar.emptyCurrentUser();
        return true;
    }
    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public User getCurrentUser(){
        return registrar.getCurrentUser();
    }

}
