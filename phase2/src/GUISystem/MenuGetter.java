package GUISystem;

import javafx.stage.Stage;

public interface MenuGetter {
    /**
     * Interface which gives methods to swap between login, home, and account creation menus.
     *
     * @author Ziwen
     */

    /**
     * Starts a login menu.
     *
     * @param primaryStage The primaryStage of the application.
     */
    public void goLogin(Stage primaryStage);

    /**
     * Starts a home menu.
     *
     * @param primaryStage The primaryStage of the application.
     */
    public void goHome(Stage primaryStage);

    /**
     * Starts an account creation menu.
     * @param primaryStage The primaryStage of the application.
     */
    public void goCreateAccount(Stage primaryStage);
}
