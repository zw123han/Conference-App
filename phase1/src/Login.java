import java.util.Arrays;
import java.util.Base64;
public class Login {
    private Registrar registrar;

    public Login(Registrar registrar){
        this.registrar = registrar;
    }

    private boolean loginAble(User user, String password){
        if (!(userExists(user.getName(), password))) {
            return false;
        }
        return Arrays.equals(password.getBytes(),Base64.getDecoder().decode(user.getPassword()));
    }

    public boolean userExists(String username, String password) {
        return registrar.userExisting(username);
    }

    public User attemptLogin(String username, String password){
        if (!userExists(username, password)){
            return null;
        }
        User user = registrar.getUserByUserName(username);
        if (loginAble(user, password)) {
            return user;
        }
        return null;
    }

}
