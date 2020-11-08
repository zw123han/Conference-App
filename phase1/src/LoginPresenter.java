public class LoginPresenter {

    private Scanner sc;

    public LoginPresenter() {
        this.sc = new Scanner(system.in);
    }

    public String promptLogin() {
        String[] credentials = new Array[2];

        System.out.println("Welcome to this Tech Conference app. Please enter your username and password.");
        System.out.println("Enter username");
        String username = sc.nextLine();
        System.out.println("Enter password");
        String password = sc.nextLine();

        credentials[0] = username;
        credentials[1] = password;

        return credentials;
    }

    public String failedLogin() {
        System.out.println("We're sorry, ")
    }

}