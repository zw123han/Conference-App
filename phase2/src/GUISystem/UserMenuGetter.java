package GUISystem;

import javafx.stage.Stage;

public interface UserMenuGetter {
    /**
     * Allows returning to a top level menu.
     *
     * @author Chrisee
     */


    /**
     * Returns to a top level menu.
     *
     * @param primaryStage The primaryStage of the application.
     */
    public void goBack(Stage primaryStage);

}
