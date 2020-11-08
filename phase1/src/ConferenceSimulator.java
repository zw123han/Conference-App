public class ConferenceSimulator {

    private Registrar registrar;
    private LoginPresenter loginPresenter;
    private LoginController loginController;

    public ConferenceSimulator() {
        this.registrar = new Registrar();
        this.loginPresenter = new LoginPresenter();
        this.loginController = new LoginController();
    }

    public void run(){
        //loginController.handleLogin(loginPresenter.promptLogin());


    }

}
