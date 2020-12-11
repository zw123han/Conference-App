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

import java.util.ArrayList;

/**
 * The GUI menu to access events.
 *
 * @author Tao
 */
public class EventMenuGUI extends Application implements EventSignupPresenter.EventInterface {

    private EventSignupPresenter esp;
    private User user;
    private ListView<VBox> yourEvents ;
    private ListView<VBox> allEvents;
    private UserMenuGetter mg;

    /**
     * Sets the eventSignupPresenter of this class.
     * @param esp An instance of EventSignupPresenter.
     */
    public void setEventElements(EventSignupPresenter esp) {
        this.esp = esp;
    }

    /**
     * Sets the user of this class.
     *
     * @param user An instance of user, representing the currently logged in user.
     */

    public void setUser(User user) {
        this.user = user;
    }
    /**
     * Sets the userMenuGetter interface of this class.
     *
     * @param userMenuGetter An instance of the userMenuGetter interface.
     */
    public void setUserMenuGetter(UserMenuGetter userMenuGetter) {
        this.mg = userMenuGetter;
    }

    /**
     * The main executable of this class. Will start the menu.
     * @param primaryStage The primaryStage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        // #setting up grid and layout
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20, 20, 20, 20));
        root.setPrefSize(800, 600);

        HBox topView = new HBox(5);
        VBox leftView = new VBox();
        VBox rightView = new VBox();
        yourEvents = new ListView<>();
        allEvents = new ListView<>();
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

        //preliminary loading
        esp.usersEvents(user);
        esp.viewEvents();

        // event handlers
        viewSchedule.setOnAction(e -> this.esp.downloadUserEvents(this.user));

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
            searchButton.setOnAction(ae -> esp.getEventInfo(input.getText()));
            layout.getChildren().addAll(hBox, searchButton, closeButton);

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
        goBack.setOnAction(e -> mg.goBack(primaryStage));

        Scene scene = new Scene (root);
        primaryStage.setTitle("Events");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a visual representation of an event the user has signed up for and adds it to a list of signed up events.
     *
     * @param name The name of the event.
     * @param id The id of the event.
     * @param time The time the event starts.
     * @param duration The duration of the event.
     * @param room The room of the event.
     * @param capacity The capacity of the event.
     * @param speakers The list of speakers at this event.
     */
    @Override
    public void loadUserEvents(String name, String id, String time, String duration, String room, String capacity, String speakers) {
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
        yourEvents.getItems().add(eventContainer);

    }

    /**
     * Creates a visual representation of an event and adds it to a list of all events.
     *
     * @param name The name of the event.
     * @param id The id of the event.
     * @param time The time the event starts.
     * @param duration The duration of the event.
     * @param room The room of the event.
     * @param capacity The capacity of the event.
     * @param speakers The list of speakers at this event.
     */
    @Override
    public void loadAllEvents(String name, String id, String time, String duration, String room, String capacity, String speakers) {
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

    /**
     * Creates a popup window for event joining.
     *
     * @param message A message for the popup window to display.
     */
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

    /**
     * Creates a popup window to leave an event.
     *
     * @param message The message to display.
     */
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

    /**
     * Creates a popup window to give information on attendees of an event.
     *
     * @param usernames A list of attendees' usernames at the event to display.
     */
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

    /**
     * A popup for an error in the eventMenu.
     *
     * @param message A message to display.
     */
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
