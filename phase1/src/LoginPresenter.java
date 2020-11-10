import java.util.Scanner;

public class LoginPresenter {

    private Scanner sc;

    public LoginPresenter() {
        this.sc = new Scanner(System.in);
    }

    public String getName(){
        System.out.println("Welcome to this Tech Conference. Please enter your name.");
        return sc.nextLine();
    }
    public boolean inquireExistingAccount(){
        System.out.println("Do you already have an account registered? (Y/N). Default N.");
        String response = sc.nextLine();
        return response.equals("Y");
    }

    public String[] promptAccountCreation(String name){
        String[] credentials = new String[2];
        System.out.println("Welcome to the conference "+name+". Please create an account to continue.");
        System.out.println("Enter a username:");
        String username = sc.nextLine();
        System.out.println("Enter a password:");
        String password = sc.nextLine();

        credentials[0] = username;
        credentials[1] = password;

        return credentials;
    }

    public String[] promptLogin() {
        String[] credentials = new String[2];

        System.out.println("Welcome back. Please enter your username and password to continue.");
        System.out.println("Enter username");
        String username = sc.nextLine();
        System.out.println("Enter password");
        String password = sc.nextLine();

        credentials[0] = username;
        credentials[1] = password;

        return credentials;
    }
    public void noSuchPerson() {
        System.out.println("We're sorry, you're not registered at this conference.");
    }
    public void usernameTaken() {
        System.out.println("That username is taken. Please try again.");
    }
    public void failedLogin() {
        System.out.println("We're sorry, credentials incorrect.");
    }
    public void successfulLogin(){
        System.out.println("Successfully logged in.");
    }
    public void userCreated() {
        System.out.println("Account created. Please log in.");
    }
}