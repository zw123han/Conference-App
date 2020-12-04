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

    public void setLogin(MessageInboxUI mi) {
        this.mi = mi;
    }

    @Override
    public void start(Stage primaryStage) {
        // MESSAGE INBOX CONTAINER
        HBox InboxCanvas = new HBox();
        InboxCanvas.setAlignment(Pos.CENTER_LEFT);

        // CHATROOM OPTIONS CONTAINER
        VBox chatroomCanvas = new VBox();
        chatroomCanvas.setAlignment(Pos.TOP_CENTER);
        // CHILD #1: TITLE BAR
        chatroomCanvasTitle = new Label("Chats");
        chatroomCanvasTitle.setFont(Font.loadFont(getClass().getResourceAsStream("/os/os-bold.ttf"), 16));
        chatroomCanvasTitle.setTextFill(Color.WHITE);
        Background chatroomCanvasBackground = new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), new Insets(0)));
        chatroomCanvas.setBackground(chatroomCanvasBackground);
        chatroomCanvasTitle.setPadding(new Insets(10));
        chatroomCanvasTitle.setPrefSize(180, 40);
        // CHILD #2: SCROLLABLE CHATROOM OPTIONS
        ScrollPane chatroomOptionsScrollable = new ScrollPane();
        chatroomOptions = new VBox();
        chatroomOptions.setPadding(new Insets(10));
        chatroomOptionsScrollable.setPrefSize(180, 540);
        chatroomOptionsScrollable.setContent(chatroomOptions);
        chatroomCanvas.getChildren().addAll(chatroomCanvasTitle, chatroomOptionsScrollable);

        // CHAT MESSAGES CONTAINER
        VBox messageCanvas = new VBox();
        messageCanvas.setAlignment(Pos.TOP_CENTER);
        // CHILD #1: TITLE BAR
        messageCanvasTitle = new Label("Select a chat");
        messageCanvasTitle.setFont(Font.loadFont(getClass().getResourceAsStream("/os/os-bold.ttf"), 16));
        messageCanvasTitle.setPadding(new Insets(10));
        messageCanvasTitle.setPrefSize(320, 40);
        // CHILD #2: SCROLLABLE MESSAGE HISTORY
        ScrollPane messagesScrollable = new ScrollPane();
        messageDisplay = new VBox(10);
        messagesScrollable.setPrefSize(320, 500);
        messagesScrollable.setContent(messageDisplay);
        messagesScrollable.setVvalue(1.0);
        // CHILD #3: MESSAGE TEXT AREA
        HBox messageBar = new HBox(5);
        messageBar.setPrefSize(320, 100);
        messageBar.setAlignment(Pos.BOTTOM_LEFT);
        messageBar.setPadding(new Insets(5));
        // Message Box (Field)
        messageBox = new TextArea();
        messageBox.setFont(Font.loadFont(getClass().getResourceAsStream("/os/os-regular.ttf"), 12));
        messageBox.setPadding(new Insets(4));
        messageBox.setPrefSize(260, 40);
        messageBox.setWrapText(true);

        // Send Message Button
        Button sendMessage = new Button("SEND");
        sendMessage.setPrefSize(50, 30);
        sendMessage.setFont(Font.loadFont(getClass().getResourceAsStream("/os/os-bold.ttf"), 10));
        CornerRadii corn = new CornerRadii(10);
        Background background = new Background(new BackgroundFill(Color.BLACK, corn, new Insets(0)));
        sendMessage.setBackground(background);
        sendMessage.setTextFill(Color.WHITE);
        sendMessage.setPadding(new Insets(8));
        sendMessage.setOnAction(e -> sendMessage(messageBox.getText()));
        messageBar.getChildren().addAll(messageBox, sendMessage);

        messageCanvas.getChildren().addAll(messageCanvasTitle, messagesScrollable, messageBar);

        // PRELIMINARY VIEW LOADING
        //mi.loadChatroomCanvasView();

        // SCENE AND STAGE
        InboxCanvas.getChildren().addAll(chatroomCanvas, messageCanvas);
        Scene scene = new Scene(InboxCanvas, 500, 600);
        primaryStage.setTitle("Messages - Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(500);
        primaryStage.setMaxHeight(600);
        primaryStage.setMinWidth(500);
        primaryStage.setMaxWidth(500);
        primaryStage.show();
    }

    public void setChatroomCanvasTitle(String numberUnread) {
        chatroomCanvasTitle.setText("Chats (Unread: " + numberUnread + ")");
    }

    public void setChatroomOption(ArrayList<String> option) {
        Button chat = new Button();
        chat.setPrefSize(150, 50);
        chat.setId(option.get(1));
        chat.setOnAction(e -> onSelectChat(chat.getId()));

        HBox chatroomOptionContainer = new HBox(5);
        chatroomOptionContainer.setAlignment(Pos.BOTTOM_LEFT);
        chatroomOptionContainer.setPrefSize(150, 60);
        chatroomOptionContainer.setPadding(new Insets(25, 22, 22, 25));

        Label displayName = new Label(option.get(0));
        displayName.setFont(Font.loadFont(getClass().getResourceAsStream("/os/os-bold.ttf"), 12));
        Label username = new Label(option.get(1));
        username.setFont(Font.loadFont(getClass().getResourceAsStream("/os/os-regular.ttf"), 12));
        username.setTextFill(Color.GREY);
        Label unread = new Label();
        if (!option.get(2).equals("0")) {
            unread.setText(option.get(2));
            unread.setFont(Font.loadFont(getClass().getResourceAsStream("/os/os-regular.ttf"), 12));
            CornerRadii corn = new CornerRadii(10);
            Background background = new Background(new BackgroundFill(Color.BLACK, corn, new Insets(2)));
            unread.setBackground(background);
        }
        chatroomOptionContainer.getChildren().addAll(displayName, username, unread);

        chat.setGraphic(chatroomOptionContainer);
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

    public void sendMessage(String message) {
        mi.sendMessage(message, recipient);
    }

    public void display(Stage primaryStage) {
        //start(primaryStage);
    }
}
