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

    private boolean loginAble(User user, String password){
        if (!(userExists(user.getUserName()))) {
            return false;
        }
        return Arrays.equals(Base64.getEncoder().encode(password.getBytes()), user.getPassword().getBytes());
    }

    /**
     * (please describe)
     *
     * @param username      (please describe)
     * @return              (please describe)
     */
    public boolean userExists(String username) {
        return registrar.userExisting(username);
    }

    /**
     * (please describe)
     *
     * @param username      (please describe)
     * @param password      (please describe)
     * @return              (please describe)
     */
    public User attemptLogin(String username, String password){
        if (!userExists(username)){
            return null;
        }
        User user = registrar.getUserByUserName(username);
        if (loginAble(user, password)) {
            return user;
        }
        return null;
    }

}
