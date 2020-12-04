package GUISystem;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.security.util.Password;
import LoginSystem.*;

// This entire class contains the home menu and login menus and allows easy swapping between them
// Considering making this an interface

public class MenuFacade extends Application{
private static HomeMenuGUI homeMenuGUI;
private static LoginGUI loginGUI;
private static AccountCreationMenu accountCreationMenu;

@Override
public void start(Stage primaryStage){
    login(primaryStage);

}
private void create(Stage primaryStage){
    MenuFacade.accountCreationMenu.start(primaryStage);
}

private void login(Stage primaryStage){
    MenuFacade.loginGUI.start(primaryStage);
}
private void home(Stage primaryStage){
    //MenuFacade.homeMenuGUI.start(primaryStage);
}

public static void set(LoginGUI loginGUI, AccountCreationMenu accountCreationMenu){
    MenuFacade.loginGUI = loginGUI;
    MenuFacade.accountCreationMenu = accountCreationMenu;
    }
public static HomeMenuGUI getHomeMenuGUI(){
    return homeMenuGUI;
}
public static LoginGUI getLoginGUI(){
    return loginGUI;
}
public static AccountCreationMenu getAccountCreationMenu(){
    return accountCreationMenu;
}
}
