package GUISystem;

import javafx.stage.Stage;

/**
 * Allows returning to a top level menu.
 *
 * @author Chrisee
 */
public interface UserMenuGetter {

    /**
     * Returns to a top level menu.
     *
     * @param primaryStage The primaryStage of the application.
     */
    void goBack(Stage primaryStage);

}
