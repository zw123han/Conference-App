package GUISystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HomeMenuGUI extends Application { // TODO: doesn't account for different users yet


    @Override
    public void start(Stage primaryStage){
        try{Parent adminMenu = FXMLLoader.load(getClass().getResource("Menu2.fxml"));
//        Parent orgMenu =
//        Parent otherMenu =
            primaryStage.setTitle("Home Menu");
            primaryStage.setScene(new Scene(adminMenu));
            primaryStage.show();}
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
