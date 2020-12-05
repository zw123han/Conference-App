package GUISystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FriendsMenuGUI extends Application {
    Scene homeScene;
    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene (vbox, 800, 800);

        Button goBack = new Button("Back");
        goBack.setOnAction(e -> {

        });
        primaryStage.setTitle("Friends");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
