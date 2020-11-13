/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class LoginPresenter {
    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String notLoggedInOptions(){
        return "Press Y to login, or any other key to exit";
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String inquireAccount(){
        return "Press C to create a new account, press L to login to an existing account, or any other key to exit.";
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String confirmMakeAccount(){
        return "Press Y to create an account, or any other key to exit.";
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String confirmChangePassword(){
        return "Are you sure you want to change your password? Y/N. Default N.";
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String confirmLogout(){
        return "Are you sure you want to logout? Y/N. Default N.";
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String promptName(){return "Enter your real name";}

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String promptAccountUsername(){ return "Enter username:"; }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String promptAccountPassword(){
        return "Enter password:";}

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String promptOldPassword(){
        return "Enter current password:";}

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String promptNewPassword(){
        return "Enter new password:";}

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String promptAccountLogin(){
        return "Welcome to the conference! Please enter your username and password to continue.";
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String passwordUpdated(){
        return "Password updated.";
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String loggedOff(){
        return "Successfully logged out.";
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String failedLogin() {
        return "We're sorry, your credentials are incorrect. Please make sure you are not entering empty credentials.";
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String usernameTaken(){
        return "We're sorry, you cannot use those credentials.";
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String successfulLogin(){
        return "Successfully logged in.";
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String successfulAccountCreation(){
        return "Account successfully created. Please login to continue.";
    }

    /**
     * (please describe)
     *
     * @return      (please describe)
     */
    public String failedLogoff(){return "You are already logged off.";};
}