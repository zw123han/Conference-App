package GUISystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminMenu {

    private EventMenu eventMenu;
    private MessageMenu messageMenu;
    private FriendsMenu friendsMenu;
    private ManageAccountMenu manageAccountMenu;
    private ManageEventMenu manageEventMenu;
    private PasswordMenu passwordMenu;

    Stage window;

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
        friendsMenu.display();
    }

    @FXML
    public void messageDisplay(ActionEvent event) {
        messageMenu.display(window);
    }
    @FXML
    void passwordDisplay(ActionEvent event) {
        passwordMenu.display();
    }

    @FXML
    void manageAccountMenuDisplay(ActionEvent event) {
        manageAccountMenu.display();
    }
    @FXML
    void manageEventMenuDisplay(ActionEvent event) {
        manageEventMenu.display();
    }
}
