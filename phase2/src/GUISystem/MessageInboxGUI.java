package GUISystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import MessagingSystem.*;

import java.io.IOException;
import java.util.*;

public class MessageInboxGUI extends Application implements MessageInboxPresenter.IView {
    private MessageInboxPresenter mi;
    private VBox chatroomOptions;
    private VBox messageDisplay;
    private Label chatroomCanvasTitle;
    private Label messageCanvasTitle;
    private TextArea messageBox;
    private String recipient;
    private HBox messageBar;

    public void setLogin(MessageInboxPresenter mi) {
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
        HBox chatroomBar = new HBox();
        chatroomBar.setAlignment(Pos.CENTER_LEFT);
        chatroomBar.setPrefSize(180, 40);
        // Go back button
        Button goBack = squareButtonConstructor(Color.CRIMSON, "×");
        goBack.setOnAction(e -> {
            try {
                goBack(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        // Chatroom Title
        chatroomCanvasTitle = new Label("Inbox");
        chatroomCanvasTitle.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 16));
        chatroomCanvasTitle.setTextFill(Color.WHITE);
        Background chatroomCanvasBackground = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
        chatroomCanvas.setBackground(chatroomCanvasBackground);
        chatroomCanvasTitle.setPadding(new Insets(10, 12, 10, 12));
        chatroomCanvasTitle.setPrefSize(140, 40);
        // Putting everything into chatroomBar
        chatroomBar.getChildren().addAll(goBack, chatroomCanvasTitle);
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
        messageBar = new HBox();
        messageBar.setAlignment(Pos.CENTER_RIGHT);
        messageBar.setPrefSize(320, 40);
        // Message Canvas Title
        messageCanvasTitle = new Label("Select a chat");
        messageCanvasTitle.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 16));
        messageCanvasTitle.setPadding(new Insets(10));
        messageCanvasTitle.setPrefSize(320, 40);
        // New Message Button
        Button newMessage = squareButtonConstructor(Color.ROYALBLUE, "+");
        newMessage.setOnAction(e -> {
            MessageOutboxGUI outbox = new MessageOutboxGUI();
            Stage outboxWindow = new Stage();
            outbox.start(outboxWindow);
        });
        // Putting everything into messageBar
        messageBar.getChildren().addAll(messageCanvasTitle, newMessage);
        // CHILD #2: SCROLLABLE MESSAGE HISTORY
        ScrollPane messagesScrollable = new ScrollPane();
        messageDisplay = new VBox(10);
        messagesScrollable.setPrefSize(320, 500);
        messagesScrollable.setContent(messageDisplay);
        messagesScrollable.setVvalue(1.0);
        // CHILD #3: MESSAGE TEXT AREA
        HBox textFieldBar = new HBox(5);
        textFieldBar.setPrefSize(320, 160);
        textFieldBar.setAlignment(Pos.BOTTOM_LEFT);
        textFieldBar.setPadding(new Insets(5));
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
        sendMessage.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(4), Insets.EMPTY)));
        sendMessage.setTextFill(Color.WHITE);
        sendMessage.setPadding(new Insets(8));
        sendMessage.setOnAction(e -> {
            mi.sendMessage(messageBox.getText(), recipient);
            messageBox.setText("");
        });
        // Putting everything into MessageBar
        textFieldBar.getChildren().addAll(messageBox, sendMessage);
        // PUTTING EVERYTHING INTO MESSAGECANVAS
        messageCanvas.getChildren().addAll(messageBar, messagesScrollable, textFieldBar);

        // PRELIMINARY VIEW LOADING
        //mi.loadChatroomCanvasView();

        // SCENE AND STAGE
        inboxCanvas.getChildren().addAll(chatroomCanvas, messageCanvas);
        Scene scene = new Scene(inboxCanvas, 500, 600);
        primaryStage.setTitle("Messages - Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(500);
        primaryStage.setMaxHeight(600);
        primaryStage.setHeight(600);
        primaryStage.setMinWidth(500);
        primaryStage.setMaxWidth(500);
        primaryStage.setWidth(500);
        primaryStage.show();
    }

    private Button squareButtonConstructor(Color buttonColor, String symbol) {
        Button button = new Button(symbol);
        button.setPrefSize(40, 40);
        button.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-extrabold.ttf"), 16));
        button.setBackground(new Background(new BackgroundFill(buttonColor, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setBorder(new Border(new BorderStroke(buttonColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));
        button.setTextFill(Color.WHITE);
        return button;
    }

    public void setChatroomCanvasTitle(String numberUnread) {
        chatroomCanvasTitle.setText("Inbox (" + numberUnread + ")");
    }

    public void setChatroomOption(ArrayList<String> option) {
        Button chat = new Button();
        chat.setPrefSize(150, 50);
        chat.setId(option.get(1));
        chat.setOnAction(e -> {
            this.recipient = chat.getId();
            mi.loadMessageCanvasView(recipient);
        });

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
            Background background = new Background(new BackgroundFill(Color.BLACK, new CornerRadii(10), new Insets(0, 6, 0, 6)));
            unread.setPrefHeight(16);
            unread.setBackground(background);
        }
        chatroomOptionContainer.getChildren().addAll(displayName, username, unread);

        chat.setGraphic(chatroomOptionContainer);
        chatroomOptions.getChildren().add(chat);
    }

    public void setMessageCanvasTitle(String recipientName) {
        messageCanvasTitle.setText(recipientName);
        Button pinnedMessages = squareButtonConstructor(Color.GOLDENROD, "¶");
        messageBar.getChildren().add(1, pinnedMessages);
        //pinnedMessages.setOnAction(e -> );
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

    @FXML //TODO: doesn't account for user types yet
    private void goBack(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Menu1.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setHeight(500);
        window.setWidth(500);
        window.show();
    }

    public void display(Stage primaryStage) {
        start(primaryStage);
    }
}