package UserSystem;

import java.util.Arrays;
import java.util.Base64;

/**
 * Use case which enforces user credentials rules in the registrar.
 *
 * @author Ziwen
 */
public class CredentialsUseCase {
    private Registrar registrar;

    /**
     * Initializes a new CredentialsUseCase
     *
     * @param registrar     Registrar which stores users.
     */
    public CredentialsUseCase(Registrar registrar){
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
        if (name.length()<1|username.length()<1|username.contains("@")|username.contains(" ")){
            return false;
        }
        if (password.length()<1|password.contains(" ")){
            return false;
        }
        if(registrar.userExisting(username)){
            return false;
        }
        registrar.createUser(name, username, Base64.getEncoder().encodeToString(password.getBytes()), type);
        return true;
    }

    /**
     * Resets the password of a user.
     *
     * @param username          The username of an existing user.
     * @param currentPassword   The current password of the user.
     * @param newPassword       The new password of the user.
     * @return                  True or false. Returns true if and only if password is successfully reset.
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


