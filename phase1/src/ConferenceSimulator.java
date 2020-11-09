import java.util.ArrayList;

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
        String name = loginPresenter.getName();
        ArrayList<String> nameType = reader.findName(name); // A list of two elements, name and type from a gateway.

        if(nameType == null){
            loginPresenter.noSuchPerson();
        }

        if (!loginPresenter.inquireExistingAccount()) {
            String[] loginInput = loginPresenter.promptAccountCreation(name);
            String username = loginInput[0];
            String password = loginInput[1];
            String type = nameType.get(1);

            if (facade.createUser(name, username, password, type)) {
                loginPresenter.userCreated();
            }
            loginPresenter.usernameTaken();
        }
        else{
            String[] loginInput = loginPresenter.promptLogin();
            String username = loginInput[0];
            String password = loginInput[1];
            if (!(facade.login(username, password))) {
                loginPresenter.failedLogin();
            } else {
                loginPresenter.successfulLogin();
                this.showHomeScreen();
            };
        }

        //userOptionsPresenter.displayOptions(facade); // this could go inside show homescreen

        // Don't forget to check if user is logged out from facade
        reader.storeEvents("filepath");

    }

    private void showHomeScreen(){
        //prints required homescreen depending on user.

    }

    private void showEventsScreen(){
        //shows all events User is part of.

    }

    private void showMessageScreen(){
        //shows messenger screen for user.

    }

    private void showCreateEventsScreen(){
        //shows screen to create and update events. Only for organizer.

    }

}
