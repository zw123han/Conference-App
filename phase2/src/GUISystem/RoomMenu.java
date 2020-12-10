package GUISystem;

import EventSystem.EventCreatorPresenter;
import EventSystem.EventManager;
import LoginSystem.LoginOptionsFacade;
import RoomSystem.RoomPresenter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RoomMenu extends Application implements RoomPresenter.RoomInterface {

    private EventManager em;
    private RoomPresenter rp;
    private UserMenuGetter mg;
    private ListView allRooms;

    public void setRoomElements(RoomPresenter rp) {
        this.rp = rp;
    }
    public void setUserMenuGetter(UserMenuGetter userMenuGetter) {
        this.mg = userMenuGetter;
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20, 20, 20, 20));

        HBox topView = new HBox(5);
        VBox botView = new VBox();
        allRooms = new ListView();
        Label roomLabel = new Label("Rooms");
        botView.getChildren().addAll(roomLabel, allRooms);

        root.add(topView, 0,0);
        root.add(botView, 0, 1);

        Button addButton = new Button("Add Room");
        Button removeButton = new Button("Remove Room");
        Button goBack = new Button("Back");
        topView.getChildren().addAll(addButton, removeButton, goBack);


        //preliminary loading

        listRooms();

        addButton.setOnAction(e-> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Add Friend");

            VBox layout = new VBox();
            layout.setAlignment(Pos.CENTER);

            HBox hbox1 = new HBox();
            Label label1 = new Label("Room Name");
            TextField input1 = new TextField();
            HBox hbox2 = new HBox();
            Label label2 = new Label("Room Capacity");
            TextField input2 = new TextField();
            hbox1.getChildren().addAll(label1, input1);
            hbox2.getChildren().addAll(label2,input2);

            Button submitButton = new Button("Submit");
            Button closeButton = new Button("Close");

            layout.getChildren().addAll(hbox1, hbox2, submitButton, closeButton);

            submitButton.setOnAction(ae -> {
                if (isInt(input2)) {
                    int validInput = Integer.parseInt(input2.getText());
                    rp.createRoom(input1.getText(), validInput);
                    start(primaryStage);
                    allRooms.getItems().clear();
                    listRooms();
                }
            });
            closeButton.setOnAction(ae -> window.close());

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
        removeButton.setOnAction(e -> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Remove Room");

            VBox layout = new VBox();
            layout.setAlignment(Pos.CENTER);

            HBox hbox = new HBox();
            Label label = new Label("Enter Room");
            TextField input = new TextField();
            hbox.getChildren().addAll(label, input);

            Button submitButton = new Button("Submit");
            Button closeButton = new Button("Close");

            layout.getChildren().addAll(hbox, submitButton, closeButton);

            submitButton.setOnAction(ae -> {
                rp.deleteRoom(input.getText());
                start(primaryStage);
                allRooms.getItems().clear();
                listRooms();
            });
            closeButton.setOnAction(ae -> window.close());

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
        goBack.setOnAction(e -> {
            mg.goBack(primaryStage);
        });

        //Scene scene = new Scene (root, 1280, 720);
        Scene scene = new Scene (root);
        primaryStage.setTitle("Manage Rooms");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void listRooms() {
        ArrayList<String> rooms = rp.displayRooms();
        for (String r: rooms) {
            allRooms.getItems().add(r);
        }
    }
    private boolean isInt(TextField input) {
        try {
            long number = Long.parseLong(input.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @Override
    public void createPopUp(String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Pop up");

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);

        Label label = new Label(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(ae -> window.close());

        layout.getChildren().addAll(label, closeButton);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
