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
//

public class MenuFacade extends Application{
private HomeMenuGUI homeMenuGUI;
private static LoginGUI loginGUI;
private AccountCreationMenu accountCreationMenu;
private static LoginOptionsFacade loginOptionsFacade;

@Override
public void start(Stage primaryStage){
    setVariables();
    loginOrCreate(primaryStage);


}

private void loginOrCreate(Stage primaryStage){
        loginGUI.start(primaryStage);
        //accountCreationMenu.start(primaryStage);
}

// Prompt user to create an account or login
// Allow returning to default menu
// Display home menu somewhere...

    private void setVariables(){
        MenuFacade.loginGUI.setLogin(loginOptionsFacade);
    }

    public static void set(LoginOptionsFacade loginOptionsFacade){
        MenuFacade.loginGUI = new LoginGUI();
        MenuFacade.loginOptionsFacade = loginOptionsFacade;
    }
}
