package GUISystem;

import EventSystem.EventCreatorPresenter;
import EventSystem.EventManager;
import EventSystem.EventSignupPresenter;
import LoginSystem.LoginOptionsFacade;
import RoomSystem.RoomPresenter;
import UserSystem.Registrar;
import UserSystem.Speaker;
import UserSystem.User;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ManageEventMenu extends Application implements EventCreatorPresenter.EventCreatorInterface{

    private EventCreatorPresenter ecp;
    private EventManager em;
    private RoomPresenter rp;
    private UserMenuGetter mg;
    private ListView allEvents;
    private LoginOptionsFacade facade;

    public void setEventCreatorElements(EventCreatorPresenter ecp, RoomPresenter rp) {
        this.ecp = ecp;
        this.rp = rp;
    }
    public void setUserMenuGetter(UserMenuGetter userMenuGetter) {
        this.mg = userMenuGetter;
    }
    public void setFacade(LoginOptionsFacade facade) {this.facade = facade;}


    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20, 20, 20, 20));

        HBox topView = new HBox(5);
        VBox botView = new VBox();
        allEvents = new ListView();
        Label title = new Label("Events");
        botView.getChildren().addAll(title, allEvents);

        root.add(topView, 0,0);
        root.add(botView, 0, 1);

        Button createButton = new Button("Create");
        Button modifyButton = new Button("Modify");
        Button removeButton = new Button("Remove");
        Button goBack = new Button("Back");
        topView.getChildren().addAll(createButton, modifyButton, removeButton, goBack);

//        Label label = new Label("hi");
//        Label label2 = new Label("3");
//        VBox box = new VBox();
//        box.getChildren().addAll(label, label2);
//        allEvents.getItems().addAll(box);
//        yourEvents.getItems().addAll(label2);

        //preliminary loading
        ecp.viewEvents();

        // event handlers
        createButton.setOnAction(e -> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Create Event");

            VBox parent = new VBox();

            HBox name = new HBox();
            Label nameLabel = new Label("Name");
            TextField nameInput = new TextField();
            name.getChildren().addAll(nameLabel, nameInput);


            HBox time = new HBox();
            Label timeLabel = new Label("Date format(yyyy-MM-dd HH:mm):");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            TextField timeInput = new TextField();
            time.getChildren().addAll(timeLabel, timeInput);

            HBox duration = new HBox();
            Label durationLabel = new Label("Duration");
            TextField durationInput = new TextField();
            duration.getChildren().addAll(durationLabel, durationInput);

            //populate a list view with all speakers
            VBox speaker_list = new VBox();
            Label speakerLabel = new Label("Select Speakers (hold ctrl to multi select)");
            ListView<String> speakers = new ListView<>();
            ArrayList<String> allSpeakers = getSpeakers();

            for (String s: allSpeakers) {
                speakers.getItems().add(s);
            }
            speakers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            speaker_list.getChildren().addAll(speakerLabel, speakers);

            //populate a list view with all rooms
            VBox roomBox = new VBox();
            Label roomListLabel = new Label("Rooms");
            ListView<String> room_list = new ListView<>();
            listRooms(room_list);
            roomBox.getChildren().addAll(roomListLabel, room_list);
//            room_list.getSelectionModel().selectedIndexProperty().addListener((v, oldValue, newValue) -> {
//                String selectedRoom = room_list.getSelectionModel().getSelectedItem();
//                String[] list = selectedRoom.split("\n");
//                String eventName = list[1];
//                String eventName1 = eventName.substring(eventName.indexOf(":")+1).trim();
//                System.out.println(eventName);
//                System.out.println(eventName1);
//            });

//            HBox capacity = new HBox();
//            Label capacityLabel = new Label("Capacity");
//            TextField capacityInput = new TextField();
//            capacity.getChildren().addAll(capacityLabel, capacityInput);

            Button submitButton = new Button("Submit");
            submitButton.setOnAction(ae -> {
                String selectedRoom = room_list.getSelectionModel().getSelectedItem();
                String[] list = selectedRoom.split("\n");
                String roomName = list[0];
                String roomName1 = roomName.substring(roomName.indexOf(":")+1).trim();
                String capacityInput = list[1];
                int capacityInput1 = Integer.parseInt(capacityInput.substring(capacityInput.indexOf(":")+1).trim());
                System.out.println(capacityInput1);
                System.out.println(roomName1);
                if (isValidTime(timeInput, formatter)  && isInt(durationInput)) {
                    ObservableList<String> selectedItems = speakers.getSelectionModel().getSelectedItems();
                    Registrar registrar = facade.getRegistrar();
                    boolean allSpeakersValid = true;
                    ArrayList<String> speakersNameList = new ArrayList<>();
                    for(String s: selectedItems) {
                        if (registrar.userExisting(s)) {
                            User user = registrar.getUserByUserName(s);
                            if (user instanceof Speaker) {
                                speakersNameList.add(s);
                            } else {
                                allSpeakersValid = false;
                            }
                        }
                    }
                    long duration1 = Integer.parseInt(durationInput.getText());
//                    int capacity1 = Integer.parseInt(capacityInput.getText());
                    if(allSpeakersValid){
                        ecp.promptEventCreation(nameInput.getText(), roomName1,
                                LocalDateTime.parse(timeInput.getText(), formatter), duration1, speakersNameList, capacityInput1);
                    }
                    else {
                        createPopUp("Please input a valid Speaker. If you don't have any, please create a speaker account.");
                    }

                } else {
                    createPopUp("Please enter in proper format");
                }
                allEvents.getItems().clear();
                ecp.viewEvents();

            });

            Button closeButton = new Button("Close");
            closeButton.setOnAction(ae -> window.close());

            parent.getChildren().addAll(name, time, duration, speaker_list, roomBox, submitButton, closeButton);
            Scene scene = new Scene(parent);
            window.setScene(scene);
            window.showAndWait();
        });

        modifyButton.setOnAction(e -> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Modify Event");

            VBox parent = new VBox();
            TextField input = new TextField();
            Button submitButton = new Button("Modify");
            Button closeButton = new Button("Close");

            HBox name = new HBox();
            Label nameLabel = new Label("Name: ");
            HBox room = new HBox();
            Label roomLabel = new Label("Room: ");
            HBox time = new HBox();
            Label timeLabel = new Label("Date format(yyyy-MM-dd HH:mm): ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            HBox duration = new HBox();
            Label durationLabel = new Label("Duration: ");

            VBox speaker_list = new VBox();
            Label speakerLabel = new Label("Select Speakers (hold ctrl to multi select)");
            ListView<String> speakers = new ListView<>();
            ArrayList<String> allSpeakers = getSpeakers();

            HBox capacity = new HBox();
            Label capacityLabel = new Label("Capacity: ");

            for (String s: allSpeakers) {
                speakers.getItems().add(s);
            }

            speakers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            speaker_list.getChildren().addAll(speakerLabel, speakers);

            submitButton.setOnAction(ae -> {
                try{
                    long id = Long.parseLong(input.getText());

                    TextField nameInput = new TextField(em.getEvent(id).getName());
                    name.getChildren().addAll(nameLabel, nameInput);

                    TextField roomInput = new TextField(em.getEvent(id).getRoom());
                    room.getChildren().addAll(roomLabel, roomInput);


                    TextField timeInput = new TextField(em.getEvent(id).getTime().toString());
                    time.getChildren().addAll(timeLabel, timeInput);

                    TextField durationInput = new TextField(String.valueOf(em.getEvent(id).getDuration()));
                    duration.getChildren().addAll(durationLabel, durationInput);

                    TextField capacityInput = new TextField(String.valueOf(em.getEvent(id).getCapacity()));
                    capacity.getChildren().addAll(capacityLabel, capacityInput);

                } catch(NumberFormatException n) {
                    createPopUp("Invalid Event ID.");
                }
            //    allEvents.getItems().clear();
              //  ecp.viewEvents();
            });

            closeButton.setOnAction(ae -> window.close());

           parent.getChildren().addAll(name, room, time, duration, speaker_list, capacity, submitButton, closeButton);
            Scene scene = new Scene(parent);
            window.setScene(scene);
            window.showAndWait();
        });



        removeButton.setOnAction(e -> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Delete Event");

            VBox parent = new VBox();
            TextField input = new TextField();
            Button submitButton = new Button("Remove");
            Button closeButton = new Button("Close");

            submitButton.setOnAction(ae -> {

                try{
                    long id = Long.parseLong(input.getText());
                    ecp.promptEventDeletion(id);
                } catch(NumberFormatException n) {
                    createPopUp("Must be an Number");
                }
                allEvents.getItems().clear();
                ecp.viewEvents();
            });

            closeButton.setOnAction(ae -> window.close());

            parent.getChildren().addAll(input, submitButton, closeButton);
            Scene scene = new Scene(parent);
            window.setScene(scene);
            window.showAndWait();
        });

        goBack.setOnAction(e -> {
            mg.goBack(primaryStage);
        });

        //Scene scene = new Scene (root, 1280, 720);
        Scene scene = new Scene (root);
        primaryStage.setTitle("Manage Events");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    private boolean validateSpeakers(ObservableList<String> selectedItems) {
//        Registrar registrar = facade.getRegistrar();
//        boolean allSpeakersValid = true;
//        ArrayList<String> speakersNameList = new ArrayList<>();
//        for(String s: selectedItems) {
//            if (registrar.userExisting(s)) {
//                User user = registrar.getUserByUserName(s);
//                if (user instanceof Speaker) {
//                    speakersNameList.add(s);
//                } else {
//                    allSpeakersValid = false;
//                }
//            }
//        }
//    }
    private void listRooms(ListView<String> roomList) {
        ArrayList<String> rooms = rp.displayRooms();
        for (String r: rooms) {
            roomList.getItems().add(r);
        }
    }
    private ArrayList<String> getSpeakers() {
        Registrar registrar = facade.getRegistrar();
        ArrayList<String> list = new ArrayList<>();
        for (User s : registrar.getUsersByType("Speaker")) {
            list.add(s.getUserName());
        }
        return list;
    }
    private ArrayList<String> getRooms() {
        return rp.displayRooms();
    }

    private boolean isValidTime(TextField time, DateTimeFormatter formatter) {
        try {
            LocalDateTime time1 = LocalDateTime.parse(time.getText(), formatter);
            return true;

        } catch (DateTimeParseException e) {
            return false;
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
    public void loadAllEvents(String name,String id, String time, String duration, String room, String capacity, String speakers) {
        VBox eventContainer = new VBox();
        Label name1 = new Label(name);
        TextField id1 = new TextField(id);
        id1.setEditable(false);
        Label time1 = new Label(time);
        Label duration1 = new Label(duration);
        Label room1 = new Label(room);
        Label capacity1 = new Label(capacity);
        Label speakers1 = new Label(speakers);
        eventContainer.getChildren().addAll(name1, id1, time1, duration1, room1, capacity1, speakers1);
        allEvents.getItems().add(eventContainer);
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
