package GUISystem;

import javafx.scene.*;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.stage.*;
import MessagingSystem.*;

public class MessageOutboxGUI extends Application implements MessageOutboxPresenter.OView {
    private MessageOutboxPresenter mo;

    public void setOutboxElements(MessageOutboxPresenter mo) {
        this.mo = mo;
    }

    public void setLogin(String username) {
        mo.setLoggedInUser(username);
    }

    @Override
    public void start(Stage primaryStage) {
        HBox outboxCanvas = new HBox();

        Scene scene = new Scene(outboxCanvas, 500, 420);
        primaryStage.setTitle("Messages - Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(420);
        primaryStage.setMaxHeight(420);
        primaryStage.setMinWidth(500);
        primaryStage.setMaxWidth(500);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }
}
