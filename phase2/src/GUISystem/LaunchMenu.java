package GUISystem;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A class which encapsulates the javafx application to be launched.
 *
 * @author Ziwen
 */
public class LaunchMenu extends Application {

    private static MenuFacade menuFacade;

    /**
     * Starts the application.
     *
     * @param primaryStage The primaryStage of the application.
     */
    @Override
    public void start(Stage primaryStage){
        menuFacade.start(primaryStage);
    }

    /**
     * Static method which sets an necessary information to launch this class.
     *
     * @param menuFacade An instance of MenuFacade.
     */
    public static void setMenuFacade(MenuFacade menuFacade){
        LaunchMenu.menuFacade = menuFacade;
    }
}
