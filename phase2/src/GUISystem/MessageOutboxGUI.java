package GUISystem;

import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.geometry.*;
import MessagingSystem.*;
import java.util.*;

public class MessageOutboxGUI extends Application implements MessageOutboxUI.OView {

    @Override
    public void start(Stage primaryStage) {
        HBox outboxCanvas = new HBox();

        Scene scene = new Scene(outboxCanvas, 500, 400);
        primaryStage.setTitle("Messages - Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(500);
        primaryStage.setMaxHeight(600);
        primaryStage.setMinWidth(500);
        primaryStage.setMaxWidth(500);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }
}
