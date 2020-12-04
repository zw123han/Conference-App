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
    private TextArea messageBox;
    private String recipient;

    public MessageMenu(MessageInboxUI mi) {
        this.mi = mi;
    }

    public void setMessagePresenter(MessageInboxUI mi) {
        this.mi = mi;
    }

    public void setLogin(String username) {
        mi.setLoggedInUser(username);
    }

    @Override
    public void start(Stage primaryStage) {
        // MESSAGE INBOX CONTAINER
        HBox InboxCanvas = new HBox();
        InboxCanvas.setAlignment(Pos.CENTER_LEFT);

        // CHATROOM OPTIONS CONTAINER
        VBox chatroomCanvas = new VBox();
        chatroomCanvas.setAlignment(Pos.CENTER);
        // CHILD #1: TITLE BAR
        chatroomCanvasTitle = new Label("Chats");
        chatroomCanvasTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
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
        messageCanvasTitle.setFont(Font.font("Open Sans", FontWeight.BOLD, 24));
        messageCanvasTitle.setPrefWidth(350);
        // CHILD #2: SCROLLABLE MESSAGE HISTORY
        ScrollPane messagesScrollable = new ScrollPane();
        messageDisplay = new VBox(10);
        messageDisplay.setPadding(new Insets(20, 20, 20, 20));
        messageDisplay.setPrefWidth(350);
        messagesScrollable.setContent(messageDisplay);
        messagesScrollable.setVvalue(1.0);
        // CHILD #3: MESSAGE TEXT AREA
        HBox messageBar = new HBox();
        messageBar.setPrefWidth(350);
        messageBar.setAlignment(Pos.BOTTOM_LEFT);
        messageBox = new TextArea();
        messageBox.setFont(Font.font("Open Sans", 16));
        messageBox.setPadding(new Insets(4));
        messageBox.prefHeight(40);
        messageBox.maxHeight(150);
        messageBox.setWrapText(true);
        Button sendMessage = new Button("SEND");
        sendMessage.prefHeight(40);
        sendMessage.setFont(Font.font("Open Sans", FontWeight.BOLD, 16));
        CornerRadii corn = new CornerRadii(10);
        Background background = new Background(new BackgroundFill(Color.RED, corn, new Insets(4)));
        sendMessage.setBackground(background);
        sendMessage.setOnAction(e -> sendmessage(messageBox.getText()));
        messageBar.getChildren().addAll(messageBox, sendMessage);

        // PRELIMINARY VIEW LOADING
        mi.loadChatroomCanvasView();

        // SCENE AND STAGE
        InboxCanvas.getChildren().addAll(chatroomCanvas, messageCanvas);
        Scene scene = new Scene(InboxCanvas, 500, 800);
        primaryStage.setTitle("Messages - Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(500);
        primaryStage.setMaxHeight(800);
        primaryStage.setMinWidth(500);
        primaryStage.setMaxWidth(500);
        primaryStage.show();
    }

    public void setChatroomCanvasTitle(String numberUnread) {
        chatroomCanvasTitle.setText("Chats (Unread: " + numberUnread + ")");
    }

    public void setChatroomOption(ArrayList<String> option) {
        HBox chatroomOptionContainer = new HBox(10);
        chatroomOptionContainer.setAlignment(Pos.BOTTOM_LEFT);
        chatroomOptionContainer.setPrefSize(150, 60);
        chatroomOptionContainer.setPadding(new Insets(25, 22, 22, 25));

        Label displayName = new Label(option.get(0));
        displayName.setFont(Font.font("Open Sans", FontWeight.BOLD, 16));
        Label username = new Label(option.get(1));
        username.setFont(Font.font("Open Sans", 10));
        username.setTextFill(Color.GREY);
        Label unread = new Label();
        if (option.get(2).equals("0")) {
            unread.setText(option.get(2));
            unread.setFont(Font.font("Open Sans", 16));
            CornerRadii corn = new CornerRadii(10);
            Background background = new Background(new BackgroundFill(Color.RED, corn, new Insets(2)));
            unread.setBackground(background);
        }
        chatroomOptionContainer.getChildren().addAll(displayName, username, unread);

        Button chat = new Button();
        chat.setMaxSize(150, 50);
        chat.setGraphic(chatroomOptionContainer);
        chat.setId(option.get(1));
        chat.setOnAction(e -> onSelectChat(chat.getId()));
        chatroomOptions.getChildren().add(chat);
    }

    private void onSelectChat(String id) {
        this.recipient = id;
        mi.loadMessageCanvasView(recipient);
    }

    public void setMessageCanvasTitle(String recipientName) {
        messageCanvasTitle.setText(recipientName);
    }

    public void setMessageArea(ArrayList<String> messageData) {
        HBox messageContainer = new HBox(10);
        CornerRadii corn = new CornerRadii(10);
        Background background = new Background(new BackgroundFill(Color.ALICEBLUE, corn, new Insets(20)));
        messageContainer.setBackground(background);

        VBox senderData = new VBox();
        senderData.setAlignment(Pos.TOP_LEFT);
        Label sender = new Label(messageData.get(0));
        sender.setFont(Font.font("Open Sans", FontWeight.BOLD, 16));
        Label date = new Label(messageData.get(1));
        date.setFont(Font.font("Open Sans", 16));
        date.setTextFill(Color.GREY);
        senderData.getChildren().addAll(sender, date);

        Label message = new Label(messageData.get(2));
        message.setFont(Font.font("Open Sans", 16));
        message.setWrapText(true);

        messageContainer.getChildren().addAll(senderData, message);
        messageContainer.setId(messageData.get(3));

        messageDisplay.getChildren().add(messageContainer);
    }

    public void sendmessage(String message) {
        mi.sendMessage(message, recipient);
    }

    public void display(Stage primaryStage) {
        start(primaryStage);
    }

}
