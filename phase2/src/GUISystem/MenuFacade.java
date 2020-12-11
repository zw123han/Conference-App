package GUISystem;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * An encapsulation menu which allows swapping between home, login, and account creation menus. Implements MenuGetter.
 *
 * @author Ziwen
 */
public class MenuFacade extends Application implements MenuGetter{

    private HomeMenuGUI homeMenuGUI;
    private LoginGUI loginGUI;
    private AccountCreationMenu accountCreationMenu;

    /**
     * Starts this menu.
     *
     * @param primaryStage The primaryStage of this application.
     */
    @Override
    public void start(Stage primaryStage){ login(primaryStage);}

    private void login(Stage primaryStage){ loginGUI.start(primaryStage);}

    /**
     * Sets the other menus in this menu.
     *
     * @param loginGUI An instance of the LoginGUI menu.
     * @param accountCreationMenu An instance of the AccountCreationMenu.
     * @param homeMenuGUI An instance of the HomeMenuGUI menu.
     */
    public void set(LoginGUI loginGUI, AccountCreationMenu accountCreationMenu, HomeMenuGUI homeMenuGUI){
        this.homeMenuGUI = homeMenuGUI;
        this.loginGUI = loginGUI;
        this.accountCreationMenu = accountCreationMenu;
        }

    /**
     * Starts the loginMenu.
     *
     * @param primaryStage The primaryStage of this application.
     */
    @Override
    public void goLogin(Stage primaryStage){
        this.loginGUI.start(primaryStage);
    }

    /**
     * Starts the homeMenu.
     *
     * @param primaryStage The primaryStage of this application.
     */
    @Override
    public void goHome(Stage primaryStage){
        this.homeMenuGUI.start(primaryStage);
    }

    /**
     * Starts the accountCreationMenu.
     *
     * @param primaryStage The primaryStage of this application.
     */
    @Override
    public void goCreateAccount(Stage primaryStage){
        this.accountCreationMenu.start(primaryStage);
    }
}
