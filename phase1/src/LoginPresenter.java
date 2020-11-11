public class LoginPresenter {
    public String notLoggedInOptions(){
        return "You are not currently logged in. Press Y to login, or any other key to exit";
    }
    public String confirmChangePassword(){
        return "Are you sure you want to change your password? Y/N. Default N.";
    }
    public String confirmLogout(){
        return "Are you sure you want to logout? Y/N. Default N.";
    }
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
        return "We're sorry, your credentials are incorrect.";
    }
    public String successfulLogin(){
        return "Successfully logged in.";
    }

}