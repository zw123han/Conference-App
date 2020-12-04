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


public class HomeMenuGUI { // TODO: doesn't account for different users yet
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

    public void eventDisplay() {
        eventMenu.display();
    }
    public void friendsDisplay() {
        friendsMenu.display();
    }

    public void messageDisplay() {
        messageMenu.display(window);
    }

    public void passwordDisplay() {
        passwordMenu.display();
    }

    public void manageAccountMenuDisplay() {
        manageAccountMenu.display();
    }

    public void manageEventMenuDisplay() {
        manageEventMenu.display();
    }
}
