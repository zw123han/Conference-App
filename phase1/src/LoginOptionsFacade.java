import java.util.Base64;

public class LoginOptionsFacade {
    private CredentialsController credentialsController;
    private Login login;
    private User user;

    public LoginOptionsFacade(Registrar registrar){
        this.credentialsController = new CredentialsController(registrar);
        this.login = new Login(registrar);
        this.user = null;
    }

    public boolean createUser(String name, String username, String password, String type){
        return credentialsController.createUser(name, username, password, type);
    }

    public boolean resetPassword(String username, String currentPassword, String newPassword) {
        return credentialsController.resetPassword(username, currentPassword, newPassword);
    }

    public boolean login(String username, String password){
        this.user = login.attemptLogin(username, password);
        return true;
    }

    public boolean userExists(String username, String password) {
        return login.userExists(username);
    }

    public boolean logout(){
        this.user = null;
        return true;
    }

    public User getUser() throws EmptyUserException{
        if (this.user == null){
            throw new EmptyUserException();
        }
        return this.user;
    }
}
