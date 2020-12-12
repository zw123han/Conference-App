package GUISystem;

import UserSystem.FriendsController;
import UserSystem.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A GUI menu which displays friends of a user and allows modification.
 *
 * @author Tao
 */
public class FriendsMenuGUI extends Application implements FriendsController.FriendInterface {

    private User user;
    private FriendsController fc;
    private UserMenuGetter mg;
    private ListView<String> allFriends;

    /**
     * A setter for a controller of a user's friendlist.
     *
     * @param fc An instance of FriendsController
     */
    public void setFriendsElements(FriendsController fc) {
        this.fc = fc;
    }

    /**
     * Sets the userMenuGetter interface of this class.
     *
     * @param userMenuGetter An instance of the UserMenuGetter interface.
     */
    public void setUserMenuGetter(UserMenuGetter userMenuGetter) {
        this.mg = userMenuGetter;
    }

    /**
     * Sets the current user of the menu.
     *
     * @param user The current user logged in, an instance of User.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * The main executable of this class. Will open the menu.
     *
     * @param primaryStage The primaryStage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20, 20, 20, 20));
        root.setPrefSize(500, 600);

        HBox topView = new HBox(5);
        VBox botView = new VBox();
        allFriends = new ListView<>();
        Label friendLabel = new Label("Friends");
        botView.getChildren().addAll(friendLabel, allFriends);

        root.add(topView, 0,0);
        root.add(botView, 0, 1);

        Button addButton = new Button("Add Friend");
        Button removeButton = new Button("Remove Friend");
        Button goBack = new Button("Back");
        topView.getChildren().addAll(addButton, removeButton, goBack);

        //preliminary loading
        fc.viewFriends(user);

        addButton.setOnAction(e-> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Add Friend");

            VBox layout = new VBox();
            layout.setAlignment(Pos.CENTER);

            HBox hbox = new HBox();
            Label label = new Label("Enter username");
            TextField input = new TextField();
            hbox.getChildren().addAll(label, input);

            Button submitButton = new Button("Submit");
            Button closeButton = new Button("Close");

            layout.getChildren().addAll(hbox, submitButton, closeButton);

            submitButton.setOnAction(ae -> {
                fc.addFriends(user, input.getText());
                start(primaryStage);
            });

            closeButton.setOnAction(ae -> window.close());

            layout.setOnKeyPressed(k -> {
                if(k.getCode() == KeyCode.ENTER) {
                    fc.addFriends(user, input.getText());
                    start(primaryStage);
                }
            });

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });

        removeButton.setOnAction(e -> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Remove Friend");

            VBox layout = new VBox();
            layout.setAlignment(Pos.CENTER);

            HBox hbox = new HBox();
            Label label = new Label("Enter username");
            TextField input = new TextField();
            hbox.getChildren().addAll(label, input);

            Button submitButton = new Button("Submit");
            Button closeButton = new Button("Close");

            layout.getChildren().addAll(hbox, submitButton, closeButton);

            submitButton.setOnAction(ae -> {
                fc.removeFriends(user, input.getText());
                start(primaryStage);
            });

            layout.setOnKeyPressed(k -> {
                if(k.getCode() == KeyCode.ENTER) {
                    fc.removeFriends(user, input.getText());
                    start(primaryStage);
                }
            });

            closeButton.setOnAction(ae -> window.close());

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });

        goBack.setOnAction(e -> mg.goBack(primaryStage));


        Scene scene = new Scene (root);
        primaryStage.setTitle("Friends");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Loads a list of friends to display.
     *
     * @param friend The username of a friend.
     */
    @Override
    public void loadFriends(String friend) {
        if (!(friend.equals(""))) {
            allFriends.getItems().add(friend);
        }
    }

    /**
     * Creates a popup window to display.
     *
     * @param message The message to display.
     */
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

        layout.setOnKeyPressed(k -> {
            if(k.getCode() == KeyCode.ENTER) {
                window.close();
            }
        });

        layout.getChildren().addAll(label, closeButton);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
