package GUISystem;

import LoginSystem.LoginOptionsFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HomeMenuGUI extends Application implements MenuInteractor, LoginInteractor{ // TODO: doesn't account for different users yet
    private LoginOptionsFacade loginOptionsFacade;
    private MenuGetter menuGetter;

    @Override
    public void start(Stage primaryStage){
        try{Parent adminMenu = FXMLLoader.load(getClass().getResource("Menu2.fxml"));
//        Parent orgMenu =
//        Parent otherMenu =
        primaryStage.setTitle("Home Menu");
        primaryStage.setScene(new Scene(adminMenu));
        primaryStage.show();}
        catch(IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void setLogin(LoginOptionsFacade loginOptionsFacade) {
        this.loginOptionsFacade = loginOptionsFacade;
    }

    @Override
    public void setMenuGetter(MenuGetter menuGetter) {
        this.menuGetter = menuGetter;
    }

    private void goLogin(Stage primaryStage){
        menuGetter.getLoginGUI().start(primaryStage);
    }
}
