package GUISystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.security.util.Password;


public class HomeMenuGUI {
    private EventMenu eventMenu;
    private MessageMenu messageMenu;
    private FriendsMenu friendsMenu;
    private ManageAccountMenu manageAccountMenu;
    private ManageEventMenu manageEventMenu;
    private PasswordMenu passwordMenu;

    public HomeMenuGUI(EventMenu eventMenu, MessageMenu messageMenu, FriendsMenu friendsMenu,
                       ManageAccountMenu manageAccountMenu, ManageEventMenu manageEventMenu, PasswordMenu passwordMenu) {
        this.eventMenu = eventMenu;
        this.messageMenu = messageMenu;
        this.friendsMenu = friendsMenu;
        this.manageAccountMenu = manageAccountMenu;
        this.manageEventMenu = manageEventMenu;
        this.passwordMenu = passwordMenu;
    }

    Stage window;
    Scene homeScene, messageScene, eventScene, manageEventScene, friendScene, passwordScene, manageAccountScene;

    public void display() { // TODO: this is just a general template, will need to add functionality
        window.setTitle("Home Menu");

        // buttons
        Button eventButton = new Button("Events");
        Button messageButton = new Button("Messages");
        Button manageEventButton = new Button("Manage Events");
        Button friendButton = new Button("Friends");
        Button passwordButton = new Button("Change Password");
        Button manageAccountButton = new Button("Manage Account");

        eventButton.setOnAction(e -> eventMenu.display());
        messageButton.setOnAction(e -> messageMenu.display());
        manageEventButton.setOnAction(e -> manageEventMenu.display());
        friendButton.setOnAction(e -> friendsMenu.display());
        passwordButton.setOnAction(e -> passwordMenu.display());
        manageAccountButton.setOnAction(e -> manageAccountMenu.display());

        // layout construction
        VBox layout = new VBox(20);
        layout.getChildren().addAll(eventButton, messageButton, manageEventButton, friendButton, passwordButton, manageAccountButton);
        homeScene = new Scene(layout, 200, 500);
    }
}
