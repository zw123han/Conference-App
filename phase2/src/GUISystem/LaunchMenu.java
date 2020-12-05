package GUISystem;

import javafx.application.Application;
import javafx.stage.Stage;

public class LaunchMenu extends Application {
    private static MenuFacade menuFacade;

    @Override
    public void start(Stage primaryStage){
        menuFacade.start(primaryStage);
    }

    public static void setMenuFacade(MenuFacade menuFacade){
        LaunchMenu.menuFacade = menuFacade;
    }
}
