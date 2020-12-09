package UserSystem;

import java.util.ArrayList;
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
        return registrar.createUser(name, username, Base64.getEncoder().encodeToString(password.getBytes()), type);
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

    /**
     * Changes the username of a user.
     *
     * @param username The old username of the user.
     * @param newUsername The new username of the user.
     * @return True if and only if the username is successfully changed to newUsername.
     */
    public boolean updateUsername(String username, String newUsername){
        if(registrar.userExisting(newUsername)|newUsername.length()<1|newUsername.contains("@")){
            return false;
        }
        for(User user: registrar.getUsers()){
            ArrayList<String> friendsList = user.getFriends();
            if(friendsList.contains(username)){
                friendsList.remove(username);
                friendsList.add(newUsername);
            }
        }
        registrar.getUserByUserName(username).setUserName(newUsername);
        return true;
    }
}


