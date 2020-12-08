package GUISystem;

import EventSystem.EventSignupPresenter;
import UserSystem.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.util.ArrayList;

public class EventMenuGUI extends Application implements EventSignupPresenter.EventInterface {

    private EventSignupPresenter esp;
    private User user;
    private ListView yourEvents ;
    private ListView allEvents;
    private UserMenuGetter mg;

    public void setEventElements(EventSignupPresenter esp) {
        this.esp = esp;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setUserMenuGetter(UserMenuGetter userMenuGetter) {
        this.mg = userMenuGetter;
    }
    @Override
    public void start(Stage primaryStage) {
        // #setting up grid and layout
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20, 20, 20, 20));

        HBox topView = new HBox(5);
        VBox leftView = new VBox();
        VBox rightView = new VBox();
        yourEvents = new ListView();
        allEvents = new ListView();
        SplitPane splitPane = new SplitPane();

        Label leftViewTitle = new Label("Your Events");
        Label rightViewTitle = new Label("Events");
        leftView.getChildren().addAll(leftViewTitle, yourEvents);
        rightView.getChildren().addAll(rightViewTitle, allEvents);
        splitPane.getItems().addAll(leftView, rightView);
        root.add(topView, 0,0);
        root.add(splitPane, 0, 1);

        Button joinEvent = new Button("Join Event");
        Button leaveEvent = new Button("Leave Event");
        Button eventInfo = new Button("Event Info");
        Button goBack = new Button("Back");
        Button viewSchedule = new Button("Download Event Schedule");
        topView.getChildren().addAll(joinEvent, leaveEvent, eventInfo, goBack, viewSchedule);

//        Label label = new Label("hi");
//        Label label2 = new Label("3");
//        VBox box = new VBox();
//        box.getChildren().addAll(label, label2);
//        allEvents.getItems().addAll(box);
//        yourEvents.getItems().addAll(label2);

        //preliminary loading
        esp.usersEvents(user);
        esp.viewEvents();

        // event handlers
        viewSchedule.setOnAction(e -> {
            this.esp.downloadUserEvents(this.user);
        });

        joinEvent.setOnAction(e -> {
            //prompts window to join event
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Join Event");

            VBox layout = new VBox();
            layout.setAlignment(Pos.CENTER);
            HBox hBox = new HBox();

            Label enterEvent = new Label("Event ID");
            TextField input = new TextField();
            hBox.getChildren().addAll(enterEvent, input);
            Button joinButton = new Button("Join");
            Button closeButton = new Button("Close");

            closeButton.setOnAction(ae -> window.close());
            // clear the listview and then repopulate left-view.
            joinButton.setOnAction(ae -> {
                esp.joinEvent(user, input.getText());
                yourEvents.getItems().clear();
                esp.usersEvents(user);
                start(primaryStage);
            });
            layout.getChildren().addAll(hBox, joinButton, closeButton);

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });

        leaveEvent.setOnAction(e -> {
            //prompts window to leave event
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Leave Event");

            VBox layout = new VBox();
            layout.setAlignment(Pos.CENTER);
            HBox hBox = new HBox();

            Label enterEvent = new Label("Event ID");
            TextField input = new TextField();
            hBox.getChildren().addAll(enterEvent, input);
            Button leaveButton = new Button("Leave");
            Button closeButton = new Button("Close");

            closeButton.setOnAction(ae -> window.close());
            // clear the listview and then repopulate left-view.
            leaveButton.setOnAction(ae -> {
                esp.leaveEvent(user, input.getText());
                yourEvents.getItems().clear();
                esp.usersEvents(user);
                start(primaryStage);
            });
            layout.getChildren().addAll(hBox, leaveButton, closeButton);

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
        eventInfo.setOnAction(e -> {
            //prompts window to leave event
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Event Info");

            VBox layout = new VBox();
            layout.setAlignment(Pos.CENTER);
            HBox hBox = new HBox();

            Label enterEvent = new Label("Event ID");
            TextField input = new TextField();
            hBox.getChildren().addAll(enterEvent, input);
            Button searchButton = new Button("Search");
            Button closeButton = new Button("Close");

            closeButton.setOnAction(ae -> window.close());
            // clear the listview and then repopulate left-view.
            searchButton.setOnAction(ae -> {
                esp.getEventInfo(input.getText());
            });
            layout.getChildren().addAll(hBox, searchButton, closeButton);

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
        goBack.setOnAction(e -> {
            mg.goBack(primaryStage);
        });

        Scene scene = new Scene (root, 1280, 720);
        primaryStage.setTitle("Events");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void loadUserEvents(String name, String id, String time, String room, String capacity, String speakers) {
        VBox eventContainer = new VBox();
        Label name1 = new Label(name);
        Label id1 = new Label(id);
        Label time1 = new Label(time);
        Label room1 = new Label(room);
        Label capacity1 = new Label(capacity);
        Label speakers1 = new Label(speakers);
        eventContainer.getChildren().addAll(name1, id1, time1, room1, capacity1, speakers1);
        yourEvents.getItems().add(eventContainer);

    }

    @Override
    public void loadAllEvents(String name, String id, String time, String room, String capacity, String speakers) {
        VBox eventContainer = new VBox();
        Label name1 = new Label(name);
        Label id1 = new Label(id);
        Label time1 = new Label(time);
        Label room1 = new Label(room);
        Label capacity1 = new Label(capacity);
        Label speakers1 = new Label(speakers);
        eventContainer.getChildren().addAll(name1, id1, time1, room1, capacity1, speakers1);
        allEvents.getItems().add(eventContainer);

    }

    @Override
    public void joinEvent(String message) {
        //going to pop up a window that affirms event joining thing
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Join Event");

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);

        Label msg = new Label(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(ae -> window.close());
        layout.getChildren().addAll(msg, closeButton);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    @Override
    public void leaveEvent(String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Leave Event");

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);

        Label msg = new Label(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(ae -> window.close());
        layout.getChildren().addAll(msg, closeButton);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }


    @Override
    public void eventInfo(ArrayList<String> usernames) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Event info");

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);

        ListView<String> list = new ListView<>();

        for (String username: usernames) {
            list.getItems().add(username);
        }

        Button closeButton = new Button("Close");
        closeButton.setOnAction(ae -> window.close());

        layout.getChildren().addAll(list, closeButton);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
    @Override
    public void eventInfoError(String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Event info");

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
