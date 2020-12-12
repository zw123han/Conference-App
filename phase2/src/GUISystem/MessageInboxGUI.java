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

/**
 * The View (GUI) for the Message Inbox.
 * Displays GUI elements for the message inbox and passes user requests on to the Message Inbox Presenter.
 *
 * @author Chrisee
 */
public class MessageInboxGUI extends Application implements MessageInboxPresenter.MessageInboxView {
    private MessageInboxPresenter messageInboxPresenter;
    private MessageOutboxGUI messageOutboxGUI;
    private UserMenuGetter userMenuGetter;
    private String recipient = "";

    // GUI elements to be updated by the presenter
    private VBox chatroomOptions;
    private VBox messageDisplay;
    private Label chatroomCanvasTitle;
    private Label messageCanvasTitle;
    private TextArea messageBox;
    private Button sendMessage;
    private ToggleButton pinnedMessages;
    private ScrollPane messagesScrollable;

    /**
     * Initiates a new MessageInboxGUI
     *
     * @param messageInboxPresenter        MessageInboxController
     * @param messageOutboxGUI             MessageOutboxGUI
     */
    public void setInboxElements(MessageInboxPresenter messageInboxPresenter, MessageOutboxGUI messageOutboxGUI) {
        this.messageInboxPresenter = messageInboxPresenter;
        this.messageOutboxGUI = messageOutboxGUI;
    }

    /**
     * Sets username of the presenter-controller system and the message outbox
     * to that of the currently logged in user.
     *
     * @param currentUser       username of the current user
     */
    public void setLogin(String currentUser) {
        messageInboxPresenter.setLoggedInUser(currentUser);
        messageOutboxGUI.setLogin(currentUser);
    }

    /**
     * Sets the UserMenuGetter. The UserMenuGetter contains a method that allows the user
     * to navigate back to the main home menu.
     *
     * @param userMenuGetter       UserMenuGetter
     */
    public void setUserMenuGetter(UserMenuGetter userMenuGetter) {
        this.userMenuGetter = userMenuGetter;
    }

    /**
     * Starts the Message Inbox GUI.
     *
     * @param primaryStage       the Stage
     */
    @Override
    public void start(Stage primaryStage) {
        // MESSAGE INBOX CONTAINER
        HBox inboxCanvas = new HBox();
        inboxCanvas.setAlignment(Pos.CENTER_LEFT);
        inboxCanvas.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        // CHATROOM OPTIONS CONTAINER
        VBox chatroomCanvas = new VBox();
        chatroomCanvas.setAlignment(Pos.TOP_CENTER);
        // CHILD #1: TITLE BAR
        HBox chatroomBar = new HBox();
        chatroomBar.setAlignment(Pos.CENTER_LEFT);
        chatroomBar.setPrefSize(180, 40);
        // Go back button
        Button goBack = squareButtonConstructor(Color.CRIMSON, "×");
        goBack.setOnAction(e -> userMenuGetter.goBack(primaryStage));
        // Chatroom Title
        chatroomCanvasTitle = new Label("Inbox");
        chatroomCanvasTitle.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 16));
        chatroomCanvasTitle.setTextFill(Color.WHITE);
        Background chatroomCanvasBackground = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
        chatroomCanvas.setBackground(chatroomCanvasBackground);
        chatroomCanvasTitle.setPadding(new Insets(10, 12, 10, 12));
        chatroomCanvasTitle.setPrefSize(140, 40);
        // Putting everything into chatroomBar
        chatroomBar.getChildren().addAll(goBack, chatroomCanvasTitle);
        // CHILD #2: SCROLLABLE CHATROOM OPTIONS
        ScrollPane chatroomOptionsScrollable = new ScrollPane();
        chatroomOptionsScrollable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chatroomOptionsScrollable.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chatroomOptions = new VBox();
        chatroomOptionsScrollable.setPrefSize(180, 510);
        chatroomOptionsScrollable.setContent(chatroomOptions);
        chatroomOptionsScrollable.setStyle("-fx-background-color:white;");
        // CHILD #3: SEARCH BAR
        VBox userSearch = new VBox();
        userSearch.setPrefSize(180, 20);
        userSearch.setPadding(new Insets(5));
        TextField searchBar = new TextField("Search user...");
        searchBar.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-regular.ttf"), 12));
        searchBar.setStyle("-fx-text-fill: #888");
        searchBar.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        searchBar.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                searchBar.setText("");
                searchBar.setStyle("-fx-text-fill: black");
            } else {
                searchBar.setText("Search user...");
                searchBar.setStyle("-fx-text-fill: #888");
                messageInboxPresenter.loadChatroomCanvasView();
            }
        });
        searchBar.setOnKeyReleased(e -> messageInboxPresenter.updateChatroomCanvasView(searchBar.getText()));
        userSearch.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        userSearch.getChildren().add(searchBar);
        // PUTTING EVERYTHING INTO CHATROOM CANVAS
        chatroomCanvas.getChildren().addAll(chatroomBar, userSearch, chatroomOptionsScrollable);

        // CHAT MESSAGES CONTAINER
        VBox messageCanvas = new VBox();
        messageCanvas.setAlignment(Pos.TOP_CENTER);
        // CHILD #1: TITLE BAR
        HBox messageBar = new HBox();
        messageBar.setAlignment(Pos.CENTER_RIGHT);
        messageBar.setPrefSize(320, 40);
        // Message Canvas Title
        messageCanvasTitle = new Label("Select a chat");
        messageCanvasTitle.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 16));
        messageCanvasTitle.setPadding(new Insets(10));
        messageCanvasTitle.setPrefSize(210, 40);
        // Pin Message
        pinnedMessages = new ToggleButton("Pins");
        pinnedMessages.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        pinnedMessages.setPadding(new Insets(8));
        pinnedMessages.setPrefHeight(45);
        pinnedMessages.setPrefWidth(65);
        pinnedMessages.setBackground(new Background(new BackgroundFill(Color.CRIMSON, CornerRadii.EMPTY, Insets.EMPTY)));
        pinnedMessages.setBorder(new Border(new BorderStroke(Color.CRIMSON, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));
        pinnedMessages.setTextFill(Color.WHITE);
        pinnedMessages.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                pinnedMessages.setBackground(new Background(new BackgroundFill(Color.DARKRED, CornerRadii.EMPTY, Insets.EMPTY)));
                pinnedMessages.setBorder(new Border(new BorderStroke(Color.DARKRED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));
                messageInboxPresenter.loadPinnedView(recipient);
                sendMessage.setDisable(true);
                messageBox.setText("You are currently viewing pinned messages.");
                messageBox.setDisable(true);
            } else {
                pinnedMessages.setBackground(new Background(new BackgroundFill(Color.CRIMSON, CornerRadii.EMPTY, Insets.EMPTY)));
                pinnedMessages.setBorder(new Border(new BorderStroke(Color.CRIMSON, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));
                messageInboxPresenter.loadMessageCanvasView(recipient);
                sendMessage.setDisable(false);
                messageBox.setText("");
                messageBox.setDisable(false);
            }
        });
        // Putting everything into messageBar
        messageBar.getChildren().addAll(messageCanvasTitle, pinnedMessages);
        pinnedMessages.setDisable(true);
        Button newMessage = squareButtonConstructor(Color.ROYALBLUE, "+");
        newMessage.setOnAction(e -> {
            Stage outboxWindow = new Stage();
            messageOutboxGUI.start(outboxWindow);
            messageInboxPresenter.loadChatroomCanvasView();
            messageInboxPresenter.loadMessageCanvasView(recipient);
        });
        StackPane block = new StackPane();
        block.setPrefSize(40, 40);
        if (messageInboxPresenter.canGroupMessage()) {
            // adds New Message Button if user has permission
            messageBar.getChildren().add(newMessage);
        } else {
            // Adds a block the same size as the new message button otherwise
            messageBar.getChildren().add(1, block);
        }
        // CHILD #2: SCROLLABLE MESSAGE HISTORY
        messagesScrollable = new ScrollPane();
        messageDisplay = new VBox();
        messagesScrollable.setPrefSize(320, 425);
        messagesScrollable.setContent(messageDisplay);
        messagesScrollable.setVvalue(1.0);
        messagesScrollable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        messagesScrollable.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        messagesScrollable.setStyle("-fx-background-color:white;");
        // CHILD #3: MESSAGE TEXT AREA
        HBox textFieldBar = new HBox(5);
        textFieldBar.setPrefSize(320, 120);
        textFieldBar.setAlignment(Pos.BOTTOM_LEFT);
        textFieldBar.setPadding(new Insets(5));
        // Message Box (Field)
        messageBox = new TextArea("Select a chat to send a message.");
        messageBox.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-regular.ttf"), 12));
        messageBox.setPadding(new Insets(4));
        messageBox.setPrefSize(260, 120);
        messageBox.setWrapText(true);
        messageBox.setDisable(true);
        // Send Message Button
        sendMessage = new Button("SEND");
        sendMessage.setPrefSize(50, 30);
        sendMessage.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 10));
        sendMessage.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(4), Insets.EMPTY)));
        sendMessage.setTextFill(Color.WHITE);
        sendMessage.setPadding(new Insets(8));
        sendMessage.setDisable(true);
        sendMessage.setOnAction(e -> {
            String temp = messageBox.getText();
            messageBox.setText("");
            messageInboxPresenter.sendMessage(temp, recipient);
        });
        // Putting everything into MessageBar
        textFieldBar.getChildren().addAll(messageBox, sendMessage);
        // PUTTING EVERYTHING INTO MESSAGECANVAS
        messageCanvas.getChildren().addAll(messageBar, messagesScrollable, textFieldBar);

        // PRELIMINARY VIEW LOADING
        messageInboxPresenter.loadChatroomCanvasView();

        // SCENE AND STAGE
        inboxCanvas.getChildren().addAll(chatroomCanvas, messageCanvas);
        Scene scene = new Scene(inboxCanvas);
        primaryStage.setTitle("Messages");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Sets the title of the inbox to include the number of total unread messages.
     *
     * @param numberUnread  String of number of total unread messages
     */
    public void setChatroomCanvasTitle(String numberUnread) {
        chatroomCanvasTitle.setText("Inbox (" + numberUnread + ")");
    }

    /**
     * Clears all chatrooms from chatroom canvas display.
     */
    public void clearChatroomOptions() {
        chatroomOptions.getChildren().clear();
    }

    /**
     * Adds options containing chatroom data to the chatroom options area in the chatroom canvas.
     * Each set of chatroom data is stored in an ArrayList in the following form:
     * [Display name of the other user, username of the other user, number of unread messages]
     *
     * @param chatroomData  an Arraylist of chatroom data
     */
    public void setChatroomOption(ArrayList<String> chatroomData) {
        // Every chatroom option is stored in the form of a button
        Button chatroomOption = new Button();
        chatroomOption.setPrefSize(178, 20);
        chatroomOption.setId(chatroomData.get(1));
        chatroomOption.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        chatroomOption.setOnAction(e -> {
            pinnedMessages.setSelected(false);
            sendMessage.setDisable(false);
            messageBox.setText("");
            messageBox.setDisable(false);
            this.recipient = chatroomOption.getId();
            chatroomOption.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
            chatroomOption.setGraphic(chatroomOptionConstructor(chatroomData.get(0), chatroomData.get(1), "0"));
            messageInboxPresenter.markAllUnread(recipient);
            messageInboxPresenter.loadMessageCanvasView(recipient);
            // Sets only the current button background to grey -- everything else is reset to white
            for (Object obj : chatroomOptions.getChildren().toArray()) {
                if (obj instanceof Button && !obj.equals(chatroomOption)) {
                    Button b = (Button) obj;
                    b.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        });
        // Sets the text displayed in the option
        chatroomOption.setGraphic(chatroomOptionConstructor(chatroomData.get(0), chatroomData.get(1), chatroomData.get(2)));
        chatroomOption.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, Color.LIGHTGRAY, Color.LIGHTGRAY, Color.LIGHTGRAY,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
        chatroomOptions.getChildren().add(chatroomOption);
    }

    /**
     * Sets the title of the message canvas to be the recipient's display name.
     *
     * @param recipientName  String of the display name of the recipient
     */
    public void setMessageCanvasTitle(String recipientName) {
        messageCanvasTitle.setText(recipientName);
        pinnedMessages.setDisable(false);
    }

    /**
     * Clears all messages from message canvas display.
     */
    public void clearMessages() {
        messageDisplay.getChildren().clear();
    }

    /**
     * Adds a message containing message data to the message display area of the message canvas.
     * The message data is stored in an Arraylist in the following form:
     * [Sender username, local date time, filtered message, message id]
     *
     * @param messageData  an Arraylist of message data
     */
    public void setMessageArea(ArrayList<String> messageData) {
        messageDisplay.getChildren().add(constructMessageBox(messageData));
        messageDisplay.heightProperty().addListener(observable -> messagesScrollable.setVvalue(1D));
    }

    /**
     * Displays the MessageInboxGUI in the stage.
     *
     * @param primaryStage  the Stage
     */
    public void display(Stage primaryStage) {
        start(primaryStage);
    }

    // ===================
    //   PRIVATE HELPERS
    // ===================

    // Makes a single-character square button with buttonColor and symbol.
    private Button squareButtonConstructor(Color buttonColor, String symbol) {
        Button button = new Button(symbol);
        button.setPrefSize(40, 40);
        button.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-extrabold.ttf"), 16));
        button.setBackground(new Background(new BackgroundFill(buttonColor, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setBorder(new Border(new BorderStroke(buttonColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));
        button.setTextFill(Color.WHITE);
        return button;
    }

    // Makes a chatroom option box for the chatroomOptions vbox, e.g.:
    // Donna Haraway (12)
    // (@donna_haraway)
    private VBox chatroomOptionConstructor(String name, String usr, String unrd) {
        VBox chatroomOptionContainer = new VBox(2);
        chatroomOptionContainer.setAlignment(Pos.BOTTOM_LEFT);
        chatroomOptionContainer.setPrefSize(150, 60);
        chatroomOptionContainer.setPadding(new Insets(5));

        HBox nameContainer = new HBox(4);
        nameContainer.setAlignment(Pos.BOTTOM_LEFT);
        Label displayName = new Label(name);
        displayName.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        Label unread = new Label("(" + unrd + ")");
        if (unrd.equals("0")) {
            unread.setText("");
        }
        unread.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-regular.ttf"), 12));
        unread.setTextFill(Color.CRIMSON);
        unread.setPrefHeight(16);
        nameContainer.getChildren().addAll(displayName, unread);

        Label username = new Label("(@" + usr + ")");
        username.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-regular.ttf"), 12));
        username.setTextFill(Color.GREY);
        chatroomOptionContainer.getChildren().addAll(nameContainer, username);

        return chatroomOptionContainer;
    }

    // Makes a message for the messageDisplay vbox, e.g.:
    // Sender name  dd/mm/yyyy hh:mm
    // Lorem Ipsum is simply dummy text of the printing and
    // typesetting industry.Lorem Ipsum has been the industry's
    // standard dummy text ever since the 1500s, when an unknown
    // printer took a galley of type and scrambled it to make a
    // type specimen book.
    // delete  pin message  mark as read
    private VBox constructMessageBox(ArrayList<String> messageData) {
        VBox messageContainer = new VBox();
        messageContainer.setPadding(new Insets(10, 10, 10, 10));

        HBox senderData = new HBox(6);
        senderData.setAlignment(Pos.TOP_LEFT);
        Label sender = new Label(messageInboxPresenter.getDisplayName(messageData.get(0)));
        sender.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        Label date = new Label(messageData.get(1));
        date.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-regular.ttf"), 12));
        date.setTextFill(Color.GREY);
        senderData.getChildren().addAll(sender, date);

        Label message = new Label(messageData.get(2));
        message.setPrefWidth(280);
        message.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-regular.ttf"), 12));
        message.setWrapText(true);

        HBox messageOptions = new HBox(6);

        if (messageInboxPresenter.canDelete(messageData.get(0))) {
            Hyperlink delete = new Hyperlink("Delete");
            delete.setId(messageData.get(3));
            delete.setPadding(Insets.EMPTY);
            delete.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 10));
            delete.setTextFill(Color.CRIMSON);
            delete.setOnAction(e -> {
                messageInboxPresenter.removeMessage(delete.getId(), recipient);
                if (sendMessage.isDisable()) {
                    sendMessage.setDisable(false);
                    messageBox.setText("");
                    messageBox.setDisable(false);
                }
            });
            messageOptions.getChildren().add(delete);
        }

        Hyperlink pin = new Hyperlink("Pin Message");
        pin.setId(messageData.get(3));
        if (messageInboxPresenter.isPinned(pin.getId(), recipient)) {
            pin.setText("Unpin Message");
        }
        pin.setPadding(Insets.EMPTY);
        pin.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 10));
        pin.setTextFill(Color.BLACK);
        pin.setOnAction(e -> {
            if (pin.getText().equals("Pin Message")) {
                pin.setText("Unpin Message");
            } else {
                pin.setText("Pin Message");
            }
            messageInboxPresenter.pinUnpinMessage(pin.getId(), recipient);
        });
        messageOptions.getChildren().add(pin);

        if (messageInboxPresenter.canMarkReadUnread(messageData.get(0))) {
            Hyperlink readUnread = new Hyperlink("Mark Read");
            readUnread.setId(messageData.get(3));
            if (messageInboxPresenter.isRead(readUnread.getId(), recipient)) {
                readUnread.setText("Mark Unread");
            }
            readUnread.setPadding(Insets.EMPTY);
            readUnread.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 10));
            readUnread.setTextFill(Color.BLACK);
            readUnread.setOnAction(e -> {
                if (readUnread.getText().equals("Mark Read")) {
                    readUnread.setText("Mark Unread");
                } else {
                    readUnread.setText("Mark Read");
                }
                messageInboxPresenter.markReadUnread(readUnread.getId(), recipient);
            });
            messageOptions.getChildren().add(readUnread);
        }

        messageContainer.getChildren().addAll(senderData, message, messageOptions);
        return messageContainer;
    }

}