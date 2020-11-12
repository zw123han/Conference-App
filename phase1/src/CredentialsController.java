import java.util.Arrays;
import java.util.Base64;

public class CredentialsController {
    private Registrar registrar;

    public CredentialsController(Registrar registrar) {
        this.registrar = registrar;
    }

    public boolean createUser(String name, String username, String password, String type) {
        if (registrar.userExisting(username)) {
            return false;
        }
        registrar.createUser(name, username, Base64.getEncoder().encodeToString(password.getBytes()), type);
        return true;
    }

    public boolean resetPassword(String username, String currentPassword, String newPassword) {
        User user = registrar.getUserByUserName(username);
        if (user == null) {
            return false;
        }
        if (Arrays.equals(currentPassword.getBytes(), Base64.getDecoder().decode(user.getPassword()))) {
            user.setPassword(Base64.getEncoder().encodeToString(newPassword.getBytes()));
            return true;

        }
        return false;
    }
}


