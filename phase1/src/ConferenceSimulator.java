import java.util.*;

public class ConferenceSimulator {

    private Registrar registrar;
    private LoginPresenter loginPresenter;
//    private LoginController loginController;
    private UserOptionsPresenter userOptionsPresenter;

    public ConferenceSimulator() {
        this.registrar = new Registrar();
        this.loginPresenter = new LoginPresenter();
//        this.loginController = new LoginController();

    }

    public void run(){
        // Must get filepath
        // Maybe we can put the login code into a new class
        LoginOptionsFacade facade = new LoginOptionsFacade(registrar);
        ReadEvents reader = new ReadEvents("filepath");
        LoginUI ui = new LoginUI(facade);
        //ui.loginOptions(showHomeScreen, reader);




        //userOptionsPresenter.displayOptions(facade); // this could go inside show homescreen

        // Don't forget to check if user is logged out from facade
        //reader.storeEvents("filepath");

    }
    // Put these into separate gateways, and use UserOptionsPresenter as a facade for these gateways
    private void showHomeScreen(){
        //prints required homescreen depending on user.

    }

    private void showEventsScreen(){
        //shows all events User is part of.

    }

    private void showMessageScreen(User user){ // TODO: update once user storage is determined
        ChatMenuPresenter chatMenuPresenter = new ChatMenuPresenter();
        chatMenuPresenter.menuDisplay();
        chatMenuPresenter.commandPrompt("prompt");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        while (!choice.equals("$q")) {
            if (choice.equals("1")) {
                OutboxController oc = new OutboxController(user);
                oc.promptChatChoice();
            }
            else if (choice.equals("2")) {
                InboxController ic = new InboxController(user);
                ic.promptChatChoice();
            } else {
                chatMenuPresenter.invalidCommand("prompt");
                chatMenuPresenter.exitMessage();
                choice = sc.nextLine();
            }
        }
    }

    private void showCreateEventsScreen(){
        //shows screen to create and update events. Only for organizer.

    }

}
