import java.util.Arrays;
import java.util.Base64;
public class Login {
    private Registrar registrar;

    public Login(Registrar registrar){
        this.registrar = registrar;
    }

    private boolean loginAble(User user, String password){
        if (!registrar.userExisting(user.getName())){
            return false;
        }
        return Arrays.equals(password.getBytes(),Base64.getDecoder().decode(user.getPassword()));
    }

    public User attemptLogin(String username, String password){
        User user = registrar.getUserByUserName(username);
        if (loginAble(user, password)) {
            return user;
        }
        System.out.println("Username not found or incorrect password");
        return null;
    }
}
