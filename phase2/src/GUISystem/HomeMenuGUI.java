package GUISystem;

import LoginSystem.LoginOptionsFacade;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;


public class HomeMenuGUI extends Application implements MenuInteractor, LoginInteractor{ // TODO: doesn't account for different users yet
    private LoginOptionsFacade loginOptionsFacade;
    private MenuGetter menuGetter;
    private MessageInboxGUI messageMenu;

    Stage window;
    Button logoutButton;
    @Override
    public void start(Stage primaryStage){
        try {
        Parent adminMenu = FXMLLoader.load(getClass().getResource("Menu2.fxml"));
//        Parent orgMenu =
//        Parent otherMenu =
        primaryStage.setTitle("Home Menu");
        HBox thing = new HBox(10);
        Button logoutButton = new Button("Logout");
        logoutButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                loginOptionsFacade.logout();
                goLogin(primaryStage);
            }
        });
        thing.getChildren().add(logoutButton);

        primaryStage.setScene(new Scene(adminMenu));
        primaryStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void eventDisplay(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("EventMenu.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void friendsDisplay(ActionEvent event) {

    }

    @FXML
    public void messageDisplay(ActionEvent event) {
//        messageMenu = new MessageInboxGUI();
//        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//        messageMenu.display(window);
    }

    @FXML
    void passwordDisplay(ActionEvent event) {

    }
//
//    @FXML
//    void logout(ActionEvent event) {
//        loginOptionsFacade.logout();
//        Node source = (Node) event.getSource();
//        Stage primaryStage = (Stage) source.getScene().getWindow();
//        goLogin(primaryStage);
//    }

    @FXML
    void manageAccountMenuDisplay(ActionEvent event) {
        System.out.println("this ain't finished yet");
    }
    @FXML
    void manageEventMenuDisplay(ActionEvent event) {
        System.out.println("neither is this");

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
