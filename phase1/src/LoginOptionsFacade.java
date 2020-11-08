import java.util.Base64;

public class LoginOptionsFacade {
    private CredentialsController credentialsController;
    private Login login;
    private User user;

    public LoginOptionsFacade(CredentialsController credentialsController, Login login, Registrar registrar){
        this.credentialsController = new CredentialsController(registrar);
        this.login = new Login(registrar);
        this.user = null;
    }

    public boolean createUser(String name, String username, String password){
        // Check if name is an actual attendee from gateway class file
        return credentialsController.createUser(name, username, password);
    }

    public boolean resetPassword(String username, String currentPassword, String newPassword) {
        return credentialsController.resetPassword(username, currentPassword, newPassword);
    }

    public void login(String username, String password){
        this.user = login.attemptLogin(username, password);
    }

    public boolean logout(){
        this.user = null;
        return true;
    }

    public User getUser(){
        return this.user;
    }
}
