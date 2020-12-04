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

public class MessageMenu extends Application implements MessageInboxUI.IView {
    private MessageInboxUI mi;
    private VBox chatroomOptions;
    private VBox messageDisplay;
    private Label chatroomCanvasTitle;
    private Label messageCanvasTitle;

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
        chatroomCanvasTitle = new Label("Chats");
        chatroomCanvasTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 10));
        chatroomCanvasTitle.setTextFill(Color.WHITE);
        Background chatroomCanvasBackground = new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), new Insets(10)));
        chatroomCanvas.setBackground(chatroomCanvasBackground);
        chatroomCanvasTitle.setPrefWidth(150);

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
        messageCanvasTitle = new Label("Select a chat");
        messageCanvasTitle.setFont(Font.font("Open Sans", FontWeight.BOLD, 10));

        // CHILD #2: SCROLLABLE MESSAGE HISTORY
        ScrollPane messagesScrollable = new ScrollPane();
        messageDisplay = new VBox();
        messageDisplay.setPadding(new Insets(20, 20, 20, 20));
        messageDisplay.setPrefWidth(350);
        messagesScrollable.setContent(messageDisplay);
        messagesScrollable.setVvalue(1.0);


        // PRELIMINARY VIEW UPDATES
        mi.updateMessageCanvasView();

        // SCENE AND STAGE
        Scene scene = new Scene(messageInboxCanvas, 500, 500);
        primaryStage.setTitle("Messages - Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setMessageCanvasTitle(String numberUnread) {
        messageCanvasTitle.setText("Chats (Unread: " + numberUnread + ")"); // include the # of unread messages
    }

    public void setChatroomCanvasTitle(String newTitle) {
        chatroomCanvasTitle.setText(newTitle);
    }

    public void setChatroomOption(ArrayList<String> option) {
        HBox chatroomOptionContainer = new HBox(5);
        chatroomOptionContainer.setAlignment(Pos.BOTTOM_LEFT);
        chatroomOptionContainer.setPrefSize(350, 50);
        chatroomOptionContainer.setPadding(new Insets(20, 20, 20, 20));

        Label displayName = new Label(option.get(0));
        displayName.setFont(Font.font("Open Sans", FontWeight.BOLD, 10));
        Label username = new Label(option.get(1));
        username.setFont(Font.font("Open Sans", 10));
        username.setTextFill(Color.GREY);
        Label unread = new Label();
        if (option.get(2).equals("0")) {
            unread.setText(option.get(2));
            unread.setFont(Font.font("Open Sans", 6));
            CornerRadii corn = new CornerRadii(10);
            Background background = new Background(new BackgroundFill(Color.RED, corn, new Insets(2)));
            unread.setBackground(background);
        }
        chatroomOptionContainer.getChildren().addAll(displayName, username, unread);

        Button chat = new Button();
        chat.setMaxSize(350, 50);
        chat.setGraphic(chatroomOptionContainer);

        chatroomOptions.getChildren().add(chat);
    }

    public void setMessageText() {
        // write code
    }

    public void display(Stage primaryStage) {
        start(primaryStage);
    }

}
