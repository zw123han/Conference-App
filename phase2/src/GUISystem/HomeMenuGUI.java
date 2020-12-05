package GUISystem;

import LoginSystem.LoginOptionsFacade;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;


public class HomeMenuGUI extends Application implements MenuInteractor, LoginInteractor, UserMenuGetter {
    private LoginOptionsFacade loginOptionsFacade;
    private MenuGetter menuGetter;
    private MessageInboxGUI messageMenu;

    @Override
    public void start(Stage primaryStage){
        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene (vbox, 800, 800);

        Text title = new Text ("Hello "+loginOptionsFacade.getUser().getName());
        title.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"),30));


        Button eventButton = new Button("Events");
        eventButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        eventButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Go to event menu
            }
        });

        Button friendsButton = new Button("Friends");
        friendsButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        friendsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Go to friends menu
            }
        });

        Button changePasswordButton = new Button("Change password");
        changePasswordButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        changePasswordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Go to password menu
            }
        });

        Button messagingButton = new Button("Messages");
        messagingButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        messagingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                messageMenu.setLogin(loginOptionsFacade.getUser().getUserName());
                messageMenu.display(primaryStage);
            }
        });

        Button manageAccountButton = new Button("Manage Accounts");
        manageAccountButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        manageAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Go to manage account menu
            }
        });

        Button manageEventsButton = new Button("Manage Events");
        manageEventsButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        manageEventsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Go to manage events menu
            }
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginOptionsFacade.logout();
                goLogin(primaryStage);
            }
        });


        vbox.getChildren().addAll(title,eventButton, friendsButton, changePasswordButton, messagingButton);
        if (loginOptionsFacade.getUser().getUserType().equals("admin")|loginOptionsFacade.getUser().getUserType().equals("organizer")){
            vbox.getChildren().addAll(manageAccountButton, manageEventsButton);
        }
        vbox.getChildren().add(logoutButton);


        primaryStage.setTitle("Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.show();
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

    public void setMessageMenu(MessageInboxGUI messageMenu) {
        this.messageMenu = messageMenu;
    }

    public void goBack(Stage primaryStage) {
        start(primaryStage);
    }

}
