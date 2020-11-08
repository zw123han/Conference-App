import java.util.Scanner;

public class LoginPresenter {

    private Scanner sc;

    public LoginPresenter() {
        this.sc = new Scanner(System.in);
    }

    public String[] promptLogin() {
        String[] credentials = new String[4];

        System.out.println("Welcome to this Tech Conference app. Please enter your username and password.");
        System.out.println("Enter username");
        String username = sc.nextLine();
        System.out.println("Enter password");
        String password = sc.nextLine();
        System.out.println("Enter name");
        String name = sc.nextLine();
        System.out.println("Enter type of user");
        String type = sc.nextLine();

        credentials[0] = username;
        credentials[1] = password;
        credentials[2] = name;
        credentials[3] = type;
        return credentials;
    }

    public void failedLogin() {
        System.out.println("We're sorry, ");
    }
    public void successfulLogin(){
        System.out.println("Successfully logged in");
    }
    public void userCreated() {
        System.out.println("Account created");
    }
}