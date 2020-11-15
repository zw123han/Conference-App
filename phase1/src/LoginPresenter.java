/**
 * Presenter which gives users information related to login and account management
 *
 * @author Ziwen
 * @version %I%, %G%
 */
public class LoginPresenter {
    /**
     * Gives a confirmation of the login process.
     *
     * @return      Confirmation that the person is in the process of logging in, and options to abort.
     */
    public String notLoggedInOptions(){
        return "Press Y to continue login, or any other key to cancel";
    }

    /**
     * Gives the options to a person to either log in or create a new account.
     *
     * @return      The different options and associated commands to either log in or create a new account.
     */
    public String inquireAccount(){
        return "Press C to create a new account, press L to login to an existing account, or any other key to cancel.";
    }

    /**
     * Gives a confirmation of the account creation process.
     *
     * @return      Confirmation that the person is in the process of account creation, and options to abort.
     */
    public String confirmMakeAccount(){
        return "Press Y to continue account creation, or any other key to cancel.";
    }

    /**
     * Gives a confirmation of the password changing process.
     *
     * @return      Confirmation that the person is in the process of resetting their password, and options to abort.
     */
    public String confirmChangePassword(){
        return "Are you sure you want to change your password? Y/N. Default N.";
    }

    /**
     * Gives a confirmation of the logout process.
     *
     * @return      Confirmation that the person is in the process of logging out, and options to abort.
     */
    public String confirmLogout(){
        return "Are you sure you want to logout? Y/N. Default N.";
    }

    /**
     * Prompts the person to enter their name.
     *
     * @return      A prompt for a person to enter the name associated with their account.
     */
    public String promptName(){return "Enter your real name";}

    /**
     * Prompts the person to enter their username.
     *
     * @return      A prompt for a person to enter the username associated with their account.
     */
    public String promptAccountUsername(){ return "Enter username:"; }

    /**
     * Prompts the person to enter their password.
     *
     * @return      A prompt for a person to enter the password associated with their account.
     */
    public String promptAccountPassword(){
        return "Enter password:";}

    /**
     * Prompts the person to enter their current password.
     *
     * @return      A prompt for a person to enter the current password associated with their account.
     */
    public String promptOldPassword(){
        return "Enter current password:";}

    /**
     * Prompts the person to enter a new password.
     *
     * @return      A prompt for a person to enter a new password to associate with their account.
     */
    public String promptNewPassword(){
        return "Enter new password:";}

    /**
     * Prompts a person to login.
     *
     * @return      A greeting and a prompt for the person to login to an existing account.
     */
    public String promptAccountLogin(){
        return "Welcome to the conference! Please enter your username and password to continue.";
    }

    /**
     * Confirmation of password update.
     *
     * @return      A confirmation that the password has been updated for a user.
     */
    public String passwordUpdated(){
        return "Password updated.";
    }

    /**
     * Confirmation of logout.
     *
     * @return      A confirmation that the user has logged out.
     */
    public String loggedOff(){
        return "Successfully logged out.";
    }

    /**
     * Indication that login has failed.
     *
     * @return      A message indicating that login was not possible due to incorrect/missing credentials.
     */
    public String failedLogin() {
        return "We're sorry, your credentials are incorrect. Please try again.";
    }

    /**
     * Indication that a username is already associated with another user.
     *
     * @return      A message indicating that a username is already taken.
     */
    public String usernameTaken(){
        return "We're sorry, you cannot use that username.";
    }

    /**
     * Confirmation of a successful login.
     *
     * @return      A confirmation that the user has logged in.
     */
    public String successfulLogin(){
        return "Successfully logged in.";
    }

    /**
     * Confirmation of a successful account creation.
     *
     * @return      A confirmation that a new account has been successfully created.
     */
    public String successfulAccountCreation(){
        return "Account successfully created. Please login to activate your account.";
    }

    /**
     * Indication that a user has already logged out.
     *
     * @return      An message that a user is already logged out.
     */
    public String failedLogoff(){return "You are already logged off.";}
}