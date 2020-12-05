package GUISystem;
import javafx.application.Application;
import javafx.stage.Stage;


// This entire class contains the home menu and login menus and allows easy swapping between them
// Considering making this an interface

public class MenuFacade extends Application implements MenuGetter{
private HomeMenuGUI homeMenuGUI;
private LoginGUI loginGUI;
private AccountCreationMenu accountCreationMenu;

@Override
public void start(Stage primaryStage){
    login(primaryStage);

}
//private void create(Stage primaryStage){
    //accountCreationMenu.start(primaryStage);
//}

private void login(Stage primaryStage){
    loginGUI.start(primaryStage);
}
//private void home(Stage primaryStage){
    //homeMenuGUI.start(primaryStage);
//}

public void set(LoginGUI loginGUI, AccountCreationMenu accountCreationMenu, HomeMenuGUI homeMenuGUI){
    this.homeMenuGUI = homeMenuGUI;
    this.loginGUI = loginGUI;
    this.accountCreationMenu = accountCreationMenu;
    }

@Override
public HomeMenuGUI getHomeMenuGUI(){
    return this.homeMenuGUI;
}
@Override
public LoginGUI getLoginGUI(){
    return this.loginGUI;
}
@Override
public AccountCreationMenu getAccountCreationMenu(){
    return this.accountCreationMenu;
}
}
