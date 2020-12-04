package GUISystem;

import javafx.scene.*;
import javafx.scene.text.*;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.geometry.*;
import MessagingSystem.*;

public class MessageMenu extends Application implements MessageInboxUI.IView {
    private MessageInboxUI mi;
    private VBox chatroomOptions;
    private VBox messageDisplay;
    private Text chatroomCanvasTitle;
    private Text messageCanvasTitle;


    public MessageMenu(MessageInboxUI mi) {
        this.mi = mi;
    }

    public void setLogin(String username) {
        mi.setLoggedInUser(username);
    }

    @Override
    public void start(Stage primaryStage) {

        // MESSAGE CONTAINER
        HBox messageInboxCanvas = new HBox();
        messageInboxCanvas.setAlignment(Pos.CENTER_LEFT);

        // CHATROOM OPTIONS CONTAINER
        VBox chatroomCanvas = new VBox();
        chatroomCanvas.setAlignment(Pos.CENTER);

        // CHILD #1: TITLE BAR
        chatroomCanvasTitle = new Text("Chats");
        chatroomCanvasTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 10));

        // CHILD #2: SCROLLABLE CHATROOM OPTIONS
        ScrollPane chatroomOptionsScrollable = new ScrollPane();
        chatroomOptions = new VBox();
        chatroomOptions.setPadding(new Insets(20, 20, 20, 20));
        chatroomOptions.setPrefWidth(150);
        chatroomOptionsScrollable.setContent(chatroomOptions);

        chatroomCanvas.getChildren().addAll(chatroomCanvasTitle, chatroomOptionsScrollable);


        // CHAT MESSAGES CONTAINER
        VBox messageCanvas = new VBox();
        messageCanvas.setAlignment(Pos.CENTER);

        // CHILD #1: TITLE BAR
        messageCanvasTitle = new Text("Select a chat");
        messageCanvasTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 10));

        // CHILD #2: SCROLLABLE MESSAGE HISTORY
        ScrollPane messagesScrollable = new ScrollPane();
        messageDisplay = new VBox();
        messageDisplay.setPadding(new Insets(20, 20, 20, 20));
        messageDisplay.setPrefWidth(350);
        messagesScrollable.setContent(messageDisplay);
        messagesScrollable.setVvalue(1.0);

        Scene scene = new Scene(messageInboxCanvas, 500, 500);

        primaryStage.setTitle("Messages - Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setMessageCanvasTitle(String newTitle) {
        messageCanvasTitle.setText(newTitle);
    }

    public void setChatroomCanvasTitle(String newTitle) {
        chatroomCanvasTitle.setText(newTitle);
    }

    public void setChatroomOptions() {
        // write code
    }

    public void setMessageText() {
        // write code
    }

    public void display(Stage primaryStage) {
        start(primaryStage);
    }

}
