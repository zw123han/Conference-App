package GUISystem;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import MessagingSystem.*;

import java.util.HashMap;

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
        //Outbox Container
        VBox outboxCanvas = new VBox();
        outboxCanvas.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        //Outbox Title bar
        VBox outboxBar = new VBox();
        outboxBar.setAlignment(Pos.TOP_CENTER);
        outboxBar.setPrefSize(500, 420);

        Label outboxCanvasTitle = new Label("Send Group Message");
        outboxCanvasTitle.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 16));
        outboxCanvasTitle.setTextFill(Color.WHITE);
        Background outboxCanvasTitleBackground = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
        outboxCanvasTitle.setBackground(outboxCanvasTitleBackground);
        outboxCanvasTitle.setPadding(new Insets(10, 12, 10, 12));
        outboxCanvasTitle.setPrefSize(500, 20);

        //Options menu
        VBox optionsBar = new VBox();
        optionsBar.setAlignment(Pos.TOP_CENTER);
        optionsBar.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(4), Insets.EMPTY)));
        optionsBar.setPadding(new Insets(10, 12, 10, 12));
        optionsBar.setPrefSize(500, 20);

        Label optionsBarLabel = new Label("Send to ");
        optionsBarLabel.setAlignment(Pos.CENTER_LEFT);
        optionsBarLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-regular.ttf"), 12));

        ComboBox dropdownMenu = new ComboBox();
        HashMap<String, Long> events = mo.getAllEventInfo();
        if(mo.canSendToSpeakers()){
            dropdownMenu.getItems().add("All speakers");
        }
        for(String info : events.keySet()){
            dropdownMenu.getItems().add(info);
        }

        // Message Text Area
        HBox textFieldBar = new HBox(5);
        textFieldBar.setPrefSize(500, 380);
        textFieldBar.setAlignment(Pos.BOTTOM_CENTER);
        textFieldBar.setPadding(new Insets(5));
        // Message Box (Field)
        TextArea messageBox = new TextArea("");
        messageBox.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-regular.ttf"), 12));
        messageBox.setPadding(new Insets(4));
        messageBox.setPrefSize(400, 380);
        messageBox.setWrapText(true);
        messageBox.setDisable(false);
        // Send Message Button
        Button sendMessage = new Button("SEND");
        sendMessage.setPrefSize(50, 30);
        sendMessage.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 10));
        sendMessage.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(4), Insets.EMPTY)));
        sendMessage.setTextFill(Color.WHITE);
        sendMessage.setPadding(new Insets(8));
        sendMessage.setDisable(false);

        sendMessage.setOnAction(e -> {
            String tempChoice = dropdownMenu.getValue().toString();
            String tempMessage = messageBox.getText();
            if(!tempMessage.equals("") && !tempChoice.equals("")){
                messageBox.setText("");
                dropdownMenu.setValue(null);
                if(tempChoice.equals("All speakers")){
                    mo.sendtoSpeakers(tempMessage);
                }else {
                    mo.sendMessage(events.get(tempChoice), tempMessage);
                }
            }
        });

        outboxBar.getChildren().add(outboxCanvasTitle);
        optionsBar.getChildren().addAll(optionsBarLabel, dropdownMenu);
        textFieldBar.getChildren().addAll(messageBox, sendMessage);

        outboxCanvas.getChildren().addAll(outboxBar, optionsBar, textFieldBar);

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
