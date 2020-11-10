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
        if(sc.nextLine().equals("Y")){
            return true;
            }
        return false;
    }

    private boolean getExistingAccount(){
        System.out.println(loginPresenter.inquireExistingAccount());
        String response = sc.nextLine();
        return response.equals("Y");
    }

    private ArrayList<String> checkNameExists(UserReader reader, String name){
        return reader.getName(name);
    }

    private void loginOrCreateAccount(ArrayList<String> nameType){
        if (getExistingAccount()){
            System.out.println(loginPresenter.promptAccountLogin(nameType.get(0)));
            System.out.println(loginPresenter.promptAccountUsername());
            String username = sc.nextLine();
            System.out.println(loginPresenter.promptAccountPassword());
            String password = sc.nextLine();
            if(loginOptionsFacade.login(username, password)){
                System.out.println(loginPresenter.successfulLogin());
            }
            else{
                System.out.println(loginPresenter.failedLogin());
            }

        }
        else{
            System.out.println(loginPresenter.promptAccountCreation(nameType.get(0)));
            System.out.println(loginPresenter.promptAccountUsername());
            String username = sc.nextLine();
            System.out.println(loginPresenter.promptAccountPassword());
            String password = sc.nextLine();
            if (loginOptionsFacade.createUser(nameType.get(0), username, password, nameType.get(1))) {
                System.out.println(loginPresenter.userCreated());
            }
            else{
                System.out.println(loginPresenter.usernameTaken());
            }

        }

    }

    private void changePassword(User user){
        System.out.println(loginPresenter.promptAccountPassword());
        String currentPassword = sc.nextLine();
        System.out.println("Enter new password:")
        String newPassword = sc.nextLine();
        if(loginOptionsFacade.resetPassword(user.getUserName(), currentPassword,newPassword)){
            System.out.println(loginPresenter.passwordUpdated());
        }
        else{
            System.out.println(loginPresenter.failedLogin());
        }
    }
    private void logout(){
        loginOptionsFacade.logout();
        System.out.println(loginPresenter.loggedOff());
    }

    public void loginOptions(ShowHomeScreen showHomeScreen, UserReader reader) {
        User user = loginOptionsFacade.getUser();
        if (user == null) {
            if (promptLogin()) {
                System.out.println(loginPresenter.inquireName());
                String name = sc.nextLine();
                ArrayList<String> nameType = checkNameExists(reader, name);
                if (nameType == null){
                 System.out.println(loginPresenter.noSuchPerson());
                }
                else{
                    loginOrCreateAccount(nameType);
                }
            }
            showHomeScreen.showHomeScreen();
        }
        else{
            // This can all go into the home screen.
            System.out.println(loginPresenter.loggedInOptions());
            String choice = sc.nextLine();
            if(choice.equals("L")){
                logout();
            }
            else if(choice.equals("C")){
                changePassword(user);
            }
            else{
            showHomeScreen.displayOptions();}

        }

    }
}
