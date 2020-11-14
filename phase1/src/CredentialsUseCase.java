import java.util.Arrays;
import java.util.Base64;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class CredentialsUseCase {
    private Registrar registrar;

    /**
     * (please describe)
     *
     * @param registrar     (please describe)
     */
    public CredentialsUseCase(Registrar registrar){
        this.registrar = registrar;
    }

    /**
     * (please describe)
     *
     * @param name              (please describe)
     * @param username          (please describe)
     * @param password          (please describe)
     * @param type              (please describe)
     * @return                  True of false.
     */
    public boolean createUser(String name, String username, String password, String type){
        if (name.length()<1|username.length()<1|type.length()<1|username.contains("@")|username.contains(" ")){
            return false;
        }
        if(registrar.userExisting(username)){
            return false;
        }
        registrar.createUser(name, username, Base64.getEncoder().encodeToString(password.getBytes()), type);
        return true;
    }

    /**
     * (please describe)
     *
     * @param username          (please describe)
     * @param currentPassword   (please describe)
     * @param newPassword       (please describe)
     * @return                  (please describe)
     */
    public boolean resetPassword(String username, String currentPassword, String newPassword){
        if (newPassword.length() < 1|currentPassword.length()<1){
            return false;
        }
        User user = registrar.getUserByUserName(username);
        if (user == null){
            return false;
        }
        if (Arrays.equals(currentPassword.getBytes(),Base64.getDecoder().decode(user.getPassword()))){
            user.setPassword(Base64.getEncoder().encodeToString(newPassword.getBytes()));
            return true;

        }
        return false;
    }
}


