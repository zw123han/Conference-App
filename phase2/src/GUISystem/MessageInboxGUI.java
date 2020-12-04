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

public class MessageInboxGUI extends Application implements MessageInboxUI.IView {
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
        HBox inboxCanvas = new HBox();
        inboxCanvas.setAlignment(Pos.CENTER_LEFT);

        // CHATROOM OPTIONS CONTAINER
        VBox chatroomCanvas = new VBox();
        chatroomCanvas.setAlignment(Pos.TOP_CENTER);
        // CHILD #1: TITLE BAR
        HBox chatroomBar = new HBox(10);
        chatroomBar.setAlignment(Pos.CENTER_LEFT);
        chatroomBar.setPrefSize(180, 40);
        // New Message Button
        Button newMessage = new Button("+");
        newMessage.setPrefSize(20, 20);
        newMessage.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-extrabold.ttf"), 12));
        newMessage.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(0))));
        newMessage.setPadding(new Insets(0, 6, 0, 6));
        // Chatroom Title
        chatroomCanvasTitle = new Label("Inbox");
        chatroomCanvasTitle.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 16));
        chatroomCanvasTitle.setTextFill(Color.WHITE);
        Background chatroomCanvasBackground = new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), new Insets(0)));
        chatroomCanvas.setBackground(chatroomCanvasBackground);
        chatroomCanvasTitle.setPadding(new Insets(10));
        chatroomCanvasTitle.setPrefSize(140, 40);
        // Putting everything into chatroomBar
        chatroomBar.getChildren().addAll(chatroomCanvasTitle,newMessage);
        // CHILD #2: SCROLLABLE CHATROOM OPTIONS
        ScrollPane chatroomOptionsScrollable = new ScrollPane();
        chatroomOptions = new VBox();
        chatroomOptions.setPadding(new Insets(10));
        chatroomOptionsScrollable.setPrefSize(180, 540);
        chatroomOptionsScrollable.setContent(chatroomOptions);
        // PUTTING EVERYTHING INTO CHATROOM CANVAS
        chatroomCanvas.getChildren().addAll(chatroomBar, chatroomOptionsScrollable);

        // CHAT MESSAGES CONTAINER
        VBox messageCanvas = new VBox();
        messageCanvas.setAlignment(Pos.TOP_CENTER);
        // CHILD #1: TITLE BAR
        messageCanvasTitle = new Label("Select a chat");
        messageCanvasTitle.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 16));
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
        messageBar.setPrefSize(320, 160);
        messageBar.setAlignment(Pos.BOTTOM_LEFT);
        messageBar.setPadding(new Insets(5));
        // Message Box (Field)
        messageBox = new TextArea();
        messageBox.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-regular.ttf"), 12));
        messageBox.setPadding(new Insets(4));
        messageBox.setPrefSize(260, 160);
        messageBox.setWrapText(true);
        // Send Message Button
        Button sendMessage = new Button("SEND");
        sendMessage.setPrefSize(50, 30);
        sendMessage.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 10));
        sendMessage.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(10), new Insets(0))));
        sendMessage.setTextFill(Color.WHITE);
        sendMessage.setPadding(new Insets(8));
        sendMessage.setOnAction(e -> sendMessage(messageBox));
        // Putting everything into MessageBar
        messageBar.getChildren().addAll(messageBox, sendMessage);
        // PUTTING EVERYTHING INTO MESSAGECANVAS
        messageCanvas.getChildren().addAll(messageCanvasTitle, messagesScrollable, messageBar);

        // PRELIMINARY VIEW LOADING
        //mi.loadChatroomCanvasView();

        // SCENE AND STAGE
        inboxCanvas.getChildren().addAll(chatroomCanvas, messageCanvas);
        Scene scene = new Scene(inboxCanvas, 500, 600);
        primaryStage.setTitle("Messages - Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(500);
        primaryStage.setMaxHeight(600);
        primaryStage.setMinWidth(500);
        primaryStage.setMaxWidth(500);
        primaryStage.show();
    }

    public void setChatroomCanvasTitle(String numberUnread) {
        chatroomCanvasTitle.setText("Inbox (" + numberUnread + ")");
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
        displayName.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        Label username = new Label(option.get(1));
        username.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-regular.ttf"), 12));
        username.setTextFill(Color.GREY);
        Label unread = new Label();
        if (!option.get(2).equals("0")) {
            unread.setText(option.get(2));
            unread.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-extrabold.ttf"), 10));
            CornerRadii corn = new CornerRadii(10);
            Background background = new Background(new BackgroundFill(Color.BLACK, corn, new Insets(0, 6, 0, 6)));
            unread.setPrefHeight(16);
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
        sender.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        Label date = new Label(messageData.get(1));
        date.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-regular.ttf"), 12));
        date.setTextFill(Color.GREY);
        senderData.getChildren().addAll(sender, date);

        Label message = new Label(messageData.get(2));
        message.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-regular.ttf"), 12));
        message.setWrapText(true);

        messageContainer.getChildren().addAll(senderData, message);
        messageContainer.setId(messageData.get(3));

        messageDisplay.getChildren().add(messageContainer);
    }

    public void sendMessage(TextArea messageBox) {
        messageBox.setText("");
        mi.sendMessage(messageBox.getText(), recipient);
    }

    public void openOutbox() {
        MessageOutboxGUI outbox = new MessageOutboxGUI();
        Stage outboxWindow = new Stage();
        outbox.start(outboxWindow);
    }

    public void display(Stage primaryStage) {
        start(primaryStage);
    }
}