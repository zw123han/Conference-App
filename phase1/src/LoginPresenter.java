/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class LoginPresenter {
    public String notLoggedInOptions(){
        return "Press Y to login, or any other key to exit";
    }
    public String inquireAccount(){
        return "Press C to create a new account, press L to login to an existing account, or any other key to exit.";
    }
    public String confirmMakeAccount(){
        return "Press Y to create an account, or any other key to exit.";
    }
    public String confirmChangePassword(){
        return "Are you sure you want to change your password? Y/N. Default N.";
    }
    public String confirmLogout(){
        return "Are you sure you want to logout? Y/N. Default N.";
    }
    public String promptName(){return "Enter your real name";}
    public String promptAccountUsername(){ return "Enter username:"; }
    public String promptAccountPassword(){
        return "Enter password:";}
    public String promptOldPassword(){
        return "Enter current password:";}
    public String promptNewPassword(){
        return "Enter new password:";}
    public String promptAccountLogin(){
        return "Welcome to the conference! Please enter your username and password to continue.";
    }
    public String passwordUpdated(){
        return "Password updated.";
    }
    public String loggedOff(){
        return "Successfully logged out.";
    }
    public String failedLogin() {
        return "We're sorry, your credentials are incorrect. Please make sure you are not entering empty credentials.";
    }
    public String usernameTaken(){
        return "We're sorry, you cannot use those credentials.";
    }
    public String successfulLogin(){
        return "Successfully logged in.";
    }
    public String successfulAccountCreation(){
        return "Account successfully created. Please login to continue.";
    }
    public String failedLogoff(){return "You are already logged off.";};
}