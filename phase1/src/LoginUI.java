import java.util.ArrayList;
import java.util.Scanner;
public class LoginUI {
    private LoginPresenter loginPresenter;
    private LoginOptionsFacade loginOptionsFacade;
    private Scanner sc;

    public LoginUI(LoginOptionsFacade loginOptionsFacade){
        loginPresenter = new LoginPresenter();
        this.loginOptionsFacade = loginOptionsFacade;
        this.sc = new Scanner(System.in);
    }

    private boolean promptLogin(){
        System.out.println(loginPresenter.notLoggedInOptions());
        return sc.nextLine().equals("Y");
    }
    private boolean confirmLogout(){
        System.out.println(loginPresenter.confirmLogout());
        return sc.nextLine().equals("Y");
    }
    private boolean confirmChangePassword(){
        System.out.println(loginPresenter.confirmChangePassword());
        return sc.nextLine().equals("Y");
    }

    private boolean confirmCreateAccount(){
        System.out.println(loginPresenter.confirmMakeAccount());
        return sc.nextLine().equals("Y");
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

    public void login(){
        System.out.println(loginPresenter.inquireAccount());
        String response = sc.nextLine();
        if (response.equals("C")){
            createAccount();
        }
        else if (response.equals("L")){
            loginToExisting();
        }
    }

    public void logout() {
        if (confirmLogout()) {
            if (loginOptionsFacade.logout()){

            System.out.println(loginPresenter.loggedOff());}
            else{
                System.out.println(loginPresenter.failedLogoff());
            }
        }
    }
    public void changePassword() {
        if (confirmChangePassword()) {
            System.out.println(loginPresenter.promptAccountUsername());
            String username = sc.nextLine();
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
