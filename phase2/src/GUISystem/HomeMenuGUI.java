package GUISystem;

import DatabaseSystem.DatabaseInteractor;
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


public class HomeMenuGUI extends Application implements UserMenuGetter {
    private LoginOptionsFacade loginOptionsFacade;
    private MenuGetter menuGetter;
    private MessageInboxGUI messageMenu;
    private FriendsMenuGUI friendsMenu;
    private PasswordMenu passwordMenu;
    // For database saving
    private DatabaseInteractor databaseInteractor;
    // For local saving
    // private MainProgram.LocalSave localSave;
    private EventMenuGUI eventMenu;
    private ManageEventMenu manageEventMenu;
    private ManageAccountMenu manageAccountMenu;

    @Override
    public void start(Stage primaryStage) {
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
                eventMenu.setUser(loginOptionsFacade.getUser());
                eventMenu.start(primaryStage);
            }
        });

        Button friendsButton = new Button("Friends");
        friendsButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        friendsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                friendsMenu.setUser(loginOptionsFacade.getUser());
                friendsMenu.start(primaryStage);
            }
        });

        Button changePasswordButton = new Button("Change password");
        changePasswordButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        changePasswordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                passwordMenu.start(primaryStage);
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
                manageAccountMenu.setUser(loginOptionsFacade.getUser());
                manageAccountMenu.start(primaryStage);
            }
        });

        Button manageEventsButton = new Button("Manage Events");
        manageEventsButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        manageEventsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Go to manage events menu
                manageEventMenu.start(primaryStage);
            }
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginOptionsFacade.logout();
                // For database saving
                databaseInteractor.saveToDatabase();
                // For local saving
                // localSave.save();
                System.out.println("Save successful");
                menuGetter.goLogin(primaryStage);
            }
        });

        Button saveButton = new Button("Save changes");
        saveButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // For database saving
                databaseInteractor.saveToDatabase();
                // For local saving
                // localSave.save();
                System.out.println("Save successful");
            }
        });

        vbox.getChildren().addAll(title,eventButton, friendsButton, changePasswordButton, messagingButton);
        if (loginOptionsFacade.getUser().getUserType().equals("admin")|loginOptionsFacade.getUser().getUserType().equals("organizer")){
            vbox.getChildren().addAll(manageAccountButton, manageEventsButton);
        }
        vbox.getChildren().addAll(logoutButton, saveButton);


        primaryStage.setTitle("Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public void setLogin(LoginOptionsFacade loginOptionsFacade) {
        this.loginOptionsFacade = loginOptionsFacade;
    }
    public void setMenuGetter(MenuGetter menuGetter) {
        this.menuGetter = menuGetter;
    }
    public void setEventMenu(EventMenuGUI eventMenu) {this.eventMenu = eventMenu;}
    public void setManageEventMenu(ManageEventMenu manageEventMenu) {this.manageEventMenu = manageEventMenu;}
    public void setManageAccountMenu(ManageAccountMenu manageAccountMenu) {this.manageAccountMenu = manageAccountMenu;}
    public void setMessageMenu(MessageInboxGUI messageMenu) {
        this.messageMenu = messageMenu;
    }
    public void setFriendsMenu(FriendsMenuGUI friendsMenu) {
        this.friendsMenu = friendsMenu;
    }
    public void setPasswordMenu(PasswordMenu passwordMenu){
        this.passwordMenu = passwordMenu;
    }
    // For database saving
    public void setSave(DatabaseInteractor databaseInteractor){
        this.databaseInteractor = databaseInteractor;
    }
    // For local saving
    // public void steSave(MainProgram.LocalSave localSave){
        // this.localSave = localSave;
    // }
    @Override
    public void goBack(Stage primaryStage) {
        start(primaryStage);
    }

}
