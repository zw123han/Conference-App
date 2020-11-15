import java.util.Scanner;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class LoginUI {
    private LoginPresenter loginPresenter;
    private LoginOptionsFacade loginOptionsFacade;
    private Scanner sc;

    /**
     * (please describe)
     *
     * @param loginOptionsFacade        (please describe)
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
                System.out.println(loginPresenter.usernameTaken());
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
     * (please describe)
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
     * (please describe)
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
     * (please describe)
     *
     * @param username          (please describe)
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
