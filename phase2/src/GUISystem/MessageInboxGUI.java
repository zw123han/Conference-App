package GUISystem;

import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import MessagingSystem.*;

import java.util.*;

public class MessageInboxGUI extends Application implements MessageInboxPresenter.IView {
    private MessageInboxPresenter mi;
    private MessageOutboxGUI mo;
    private UserMenuGetter mg;

    private VBox chatroomOptions;
    private VBox messageDisplay;
    private Label chatroomCanvasTitle;
    private Label messageCanvasTitle;
    private TextArea messageBox;
    private String recipient;
    private HBox messageBar;
    private Button sendMessage;
    private Button pinnedMessages;

    public void setInboxElements(MessageInboxPresenter mi, MessageOutboxGUI mo) {
        this.mi = mi;
        this.mo = mo;
    }

    public void setLogin(String username) {
        mi.setLoggedInUser(username);
        mo.setLogin(username);
    }

    public void setUserMenuGetter(UserMenuGetter userMenuGetter) {
        this.mg = userMenuGetter;
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
            mg.goBack(primaryStage);
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
        // Pin Message Button
        pinnedMessages = squareButtonConstructor(Color.GOLDENROD, "¶");
        pinnedMessages.setDisable(true);
        //pinnedMessages.setOnAction(e -> );
        // New Message Button
        Button newMessage = squareButtonConstructor(Color.ROYALBLUE, "+");
        newMessage.setOnAction(e -> {
            Stage outboxWindow = new Stage();
            mo.start(outboxWindow);
        });
        // Putting everything into messageBar
        messageBar.getChildren().addAll(messageCanvasTitle, pinnedMessages, newMessage);
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
        messageBox.setDisable(true);
        // Send Message Button
        sendMessage = new Button("SEND");
        sendMessage.setPrefSize(50, 30);
        sendMessage.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 10));
        sendMessage.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(4), Insets.EMPTY)));
        sendMessage.setTextFill(Color.WHITE);
        sendMessage.setPadding(new Insets(8));
        sendMessage.setDisable(true);
        sendMessage.setOnAction(e -> {
            mi.sendMessage(messageBox.getText(), recipient);
            messageBox.setText("");
        });
        // Putting everything into MessageBar
        textFieldBar.getChildren().addAll(messageBox, sendMessage);
        // PUTTING EVERYTHING INTO MESSAGECANVAS
        messageCanvas.getChildren().addAll(messageBar, messagesScrollable, textFieldBar);

        // PRELIMINARY VIEW LOADING
        mi.loadChatroomCanvasView();

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
        // Makes a new square button.
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

    private VBox chatroomOptionConstructor(String name, String usr, String unrd) {
        // Outer container box
        VBox chatroomOptionContainer = new VBox(2);
        chatroomOptionContainer.setAlignment(Pos.BOTTOM_LEFT);
        chatroomOptionContainer.setPrefSize(150, 60);
        chatroomOptionContainer.setPadding(new Insets(5));
        // Display Name (12)
        // @username
        HBox nameContainer = new HBox(2);
        nameContainer.setAlignment(Pos.BOTTOM_LEFT);
        Label displayName = new Label(name);
        displayName.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        Label username = new Label("(@" + usr + ")");
        username.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-regular.ttf"), 12));
        username.setTextFill(Color.GREY);
        Label unread = new Label(unrd);
        if (unrd.equals("0")) {
            unread.setText("");
        }
        unread.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-extrabold.ttf"), 10));
        unread.setTextFill(Color.CRIMSON);
        unread.setPrefHeight(16);
        nameContainer.getChildren().addAll(displayName, unread);

        chatroomOptionContainer.getChildren().addAll(nameContainer, username);
        return chatroomOptionContainer;
    }

    public void setChatroomOption(ArrayList<String> option) {
        // Every chat option is stored in a button
        Button chat = new Button();
        chat.setPrefSize(178, 20);
        chat.setId(option.get(1));
        chat.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        chat.setOnAction(e -> {
            sendMessage.setDisable(false);
            messageBox.setDisable(false);
            this.recipient = chat.getId();
            chat.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
            chat.setGraphic(chatroomOptionConstructor(option.get(0), option.get(1), ""));
            mi.loadMessageCanvasView(option.get(0), recipient);
            // Sets only the current button background to grey -- everything else is reset to white
            for (Object obj : chatroomOptions.getChildren().toArray()) {
                if (obj instanceof Button && !obj.equals(chat)) {
                    Button b = (Button) obj;
                    b.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        });

        chat.setGraphic(chatroomOptionConstructor(option.get(0), option.get(1), option.get(2)));
        chatroomOptions.getChildren().add(chat);
    }

    public void setMessageCanvasTitle(String recipientName) {
        messageCanvasTitle.setText(recipientName);
        pinnedMessages.setDisable(false);
    }

    public void setMessageArea(ArrayList<String> messageData) {
        // TODO: Test and update after outbox is completed
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

    public void display(Stage primaryStage) {
        start(primaryStage);
    }
}