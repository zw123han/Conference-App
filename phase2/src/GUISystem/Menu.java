package GUISystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Menu {

    private MessageInboxGUI messageMenu;

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

    }

    @FXML
    public void messageDisplay(ActionEvent event) {

        messageMenu.display(window);
    }

    @FXML
    void passwordDisplay(ActionEvent event) {

    }

    @FXML
    void manageAccountMenuDisplay(ActionEvent event) {

    }

    @FXML
    void manageEventMenuDisplay(ActionEvent event) {

    }
    @FXML
    void logout(ActionEvent event) {

    }
}
