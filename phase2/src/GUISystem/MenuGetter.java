package GUISystem;

import javafx.stage.Stage;

/**
 * Interface which gives methods to swap between login, home, and account creation menus.
 *
 * @author Ziwen
 */
public interface MenuGetter {

    /**
     * Starts a login menu.
     *
     * @param primaryStage The primaryStage of the application.
     */
    void goLogin(Stage primaryStage);

    /**
     * Starts a home menu.
     *
     * @param primaryStage The primaryStage of the application.
     */
     void goHome(Stage primaryStage);

    /**
     * Starts an account creation menu.
     * @param primaryStage The primaryStage of the application.
     */
    void goCreateAccount(Stage primaryStage);
}
