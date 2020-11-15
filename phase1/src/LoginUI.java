import java.util.Scanner;

/**
 * A user interface which exchanges inputs and outputs with a person to login/manage their account.
 *
 * @author Ziwen
 * @version %I%, %G%
 */
public class LoginUI {
    private LoginPresenter loginPresenter;
    private LoginOptionsFacade loginOptionsFacade;
    private Scanner sc;

    /**
     * Initializes a new LoginUI.
     *
     * @param loginOptionsFacade        An instance of a LoginOptionsFacade.
     */
    public LoginUI(LoginOptionsFacade loginOptionsFacade){
        loginPresenter = new LoginPresenter();
        this.loginOptionsFacade = loginOptionsFacade;
        this.sc = new Scanner(System.in);
    }

    private boolean promptLogin(){
        System.out.println(loginPresenter.notLoggedInOptions());
        return sc.nextLine().toLowerCase().equals("y");
    }
    private boolean confirmLogout(){
        System.out.println(loginPresenter.confirmLogout());
        return sc.nextLine().toLowerCase().equals("y");
    }
    private boolean confirmChangePassword(){
        System.out.println(loginPresenter.confirmChangePassword());
        return sc.nextLine().toLowerCase().equals("y");
    }

    private boolean confirmCreateAccount(){
        System.out.println(loginPresenter.confirmMakeAccount());
        return sc.nextLine().toLowerCase().equals("y");
    }
    private void createAccount(){
        if(confirmCreateAccount()){
            System.out.println(loginPresenter.promptName());
            String name = sc.nextLine();
            System.out.println(loginPresenter.promptAccountUsername());
            String username = sc.nextLine();
            System.out.println(loginPresenter.promptAccountPassword());
            String password = sc.nextLine();
            if(loginOptionsFacade.createUser(name, username, password, "attendee")){
                System.out.println(loginPresenter.successfulAccountCreation());
                loginToExisting();
            }
            else{
                System.out.println(loginPresenter.credentialsUnusable());
            }
        }
    }
    private void loginToExisting(){
        if (promptLogin()){
            System.out.println(loginPresenter.promptAccountLogin());
            System.out.println(loginPresenter.promptAccountUsername());
            String username = sc.nextLine();
            System.out.println(loginPresenter.promptAccountPassword());
            String password = sc.nextLine();
            if (loginOptionsFacade.login(username, password)){
                System.out.println(loginPresenter.successfulLogin());
            }
            else{
                System.out.println(loginPresenter.failedLogin());
            }
        }
    }

    /**
     * Gives a person options to login or create a new account, depending on selection.
     */
    public void login(){
        System.out.println(loginPresenter.inquireAccount());
        String response = sc.nextLine();
        if (response.toLowerCase().equals("c")){
            createAccount();
        }
        else if (response.toLowerCase().equals("l")){
            loginToExisting();
        }
    }

    /**
     * Gives a person an option to log out of their account.
     */
    public void logout() {
        if (confirmLogout()) {
            if (loginOptionsFacade.logout()){

            System.out.println(loginPresenter.loggedOff());}
            else{
                System.out.println(loginPresenter.failedLogoff());
            }
        }
    }

    /**
     * Gives a person an option to change the password associated with an account.
     *
     * @param username          The account that the password is to be changed in.
     */
    public void changePassword(String username) {
        if (confirmChangePassword()) {
            System.out.println(loginPresenter.promptOldPassword());
            String currentPassword = sc.nextLine();
            System.out.println(loginPresenter.promptNewPassword());
            String newPassword = sc.nextLine();
            if (loginOptionsFacade.resetPassword(username, currentPassword, newPassword)) {
                System.out.println(loginPresenter.passwordUpdated());
            }
            else{
                System.out.println(loginPresenter.failedLogin());
            }
        }
    }
}
