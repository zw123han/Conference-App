public class ConferenceSimulator {

    private Registrar registrar;
    private LoginPresenter loginPresenter;
//    private LoginController loginController;
    private CredentialsController credentialsController;
    private Login login;

    public ConferenceSimulator() {
        this.registrar = new Registrar();
        this.loginPresenter = new LoginPresenter();
//        this.loginController = new LoginController();
        this.login = new Login(registrar);
        this.credentialsController = new CredentialsController(registrar);
    }

    public void run(){
        LoginOptionsFacade facade = new LoginOptionsFacade(credentialsController, login, registrar);

        String[] loginInput = loginPresenter.promptLogin();
        String name = loginInput[0];
        String username = loginInput[1];
        String password = loginInput[2];
        String type = loginInput[3];

        if (!(facade.userExists(username, password))) {
            facade.createUser(name, username, password, type);
            loginPresenter.userCreated();
        }
        if (!(facade.login(username, password))) {
            loginPresenter.failedLogin();
        }



    }

}
