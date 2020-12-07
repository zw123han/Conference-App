package GUISystem;

import LoginSystem.LoginOptionsFacade;
import UserSystem.FriendsController;
import UserSystem.Registrar;
import UserSystem.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManageAccountMenu extends Application {
    private User user;
    private UserMenuGetter mg;
    private LoginOptionsFacade facade;
    private ListView<String> row2;

    public void setUserMenuGetter(UserMenuGetter userMenuGetter) {
        this.mg = userMenuGetter;
    }
    public void setFacade(LoginOptionsFacade facade) {this.facade = facade;}
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage primaryStage) {
        Registrar registrar = facade.getRegistrar();

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20, 20, 20, 20));
        HBox row1 = new HBox();
        row2 = new ListView<>();
        HBox row3 = new HBox();
        // row 1
        Label label = new Label("Please specify the type of accounts to view: speakers, organizers, attendees or administrators:)");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("speakers", "organizers", "attendees", "administrators");
        row1.getChildren().addAll(label, choiceBox);
        // row 2
        // row 3
        Button createButton = new Button("Create");
        Button modifyButton = new Button("Modify");
        Button goBack = new Button("Back");
        row3.getChildren().addAll(createButton, modifyButton, goBack);

        // view users
        choiceBox.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
            choiceBoxListener(registrar, newValue, row2);
        });
        // create users
        createButton.setOnAction(e -> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Create User");

            VBox layout = new VBox(5);

            HBox titleBox = new HBox();
            Label title = new Label("Please specify the type of account: speaker, organizer or attendee:");
            ChoiceBox<String> choice = new ChoiceBox<>();
            choice.getItems().addAll("speakers", "organizers", "attendees");
            titleBox.getChildren().addAll(title, choice);

            HBox nameBox = new HBox();
            Label name = new Label("Name");
            TextField nameInput = new TextField();
            nameBox.getChildren().addAll(name, nameInput);

            HBox usernameBox = new HBox();
            TextField usernameInput = new TextField();
            Label username = new Label("Username");
            usernameBox.getChildren().addAll(username, usernameInput);

            HBox passwordBox = new HBox();
            TextField passwordInput = new TextField();
            Label password = new Label("Password");
            passwordBox.getChildren().addAll(password, passwordInput);

            Button submitButton = new Button("Submit");
            submitButton.setOnAction(ae -> {
                String name1 = nameInput.getText();
                String userName1 = usernameInput.getText();
                String password1 = passwordInput.getText();
                if (facade.createUser(name1, userName1, password1, choice.getValue())) {
                    createPopUp("Speaker account created successfully");
                } else {
                    createPopUp("You cannot use those credentials. Please try again.");
                }
            });

            Button closeButton = new Button("Close");
            closeButton.setOnAction(ae -> window.close());

            layout.getChildren().addAll(titleBox, nameBox, usernameBox, passwordBox, submitButton, closeButton);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
        // modify users
        modifyButton.setOnAction(event -> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Create User");

            VBox layout = new VBox(5);

            HBox titleBox = new HBox();
            Label title = new Label("Name");
            TextField nameInput = new TextField();
            titleBox.getChildren().addAll(title, nameInput);

            HBox selectBox = new HBox();
            Label select = new Label("View accounts");
            ChoiceBox<String> choice = new ChoiceBox<>();
            choice.getItems().addAll("speakers", "organizers", "attendees");
            selectBox.getChildren().addAll(select, choice);

            ListView<String> list = new ListView();

            choice.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
                choiceBoxListener(registrar, newValue, list);
            });

            Button submitButton = new Button("Submit");
            submitButton.setOnAction(ae -> {
                Stage window1 = new Stage();
                window1.initModality(Modality.APPLICATION_MODAL);
                window1.setTitle("Pop up");
                VBox layout1 = new VBox();
                layout.setAlignment(Pos.CENTER);
                //layout
                VBox vBox = new VBox();

                HBox enterNameBox = new HBox();
                Label enterName = new Label("Please enter the new name for the user");
                TextField input = new TextField();
                enterNameBox.getChildren().addAll(enterName, input);

                HBox  enterNewNameBox = new HBox();
                Label enterNewName = new Label("Please enter the new name for the user");
                TextField newName = new TextField();
                enterNewNameBox.getChildren().addAll(enterNewName, newName);

                vBox.getChildren().addAll(enterNameBox, enterNewNameBox);
                // buttons
                Button submit = new Button("Submit");
                Button closeButton = new Button("Close");

                submitButton.setOnAction(actionEvent -> {
                    try {
                        registrar.getUserByUserName(input.getText()).setName(newName.getText());
                        String message = "The new name has been set:\n registrar.getUserByUserName(input).getName()\nregistrar.getUserByUserName(input).getUserName()";
                        createPopUp(message);
                    } catch (NullPointerException nps) {
                        createPopUp("Error: User does not exist.");
                    }
                });
                closeButton.setOnAction(actionEvent -> window1.close());

                layout.getChildren().addAll(vBox, submit, closeButton);
                Scene scene = new Scene(layout);
                window1.setScene(scene);
                window1.showAndWait();
            });

            Button closeButton = new Button("Close");
            closeButton.setOnAction(ae -> window.close());

            layout.getChildren().addAll(titleBox, selectBox, submitButton, closeButton);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
        // go back
        goBack.setOnAction(e -> {
            mg.goBack(primaryStage);
        });
        root.getChildren().addAll(row1, row2, row3);
        Scene scene = new Scene (root, 800, 600);
        primaryStage.setTitle("Manage Accounts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void createPopUp(String message) {
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
    private void choiceBoxListener(Registrar registrar, String newValue, ListView<String> list) {
        if (newValue.equals("speakers")) {
            list.getItems().clear();
            for (User s : registrar.getUsersByType("speakers")) {
                String string = "name: " + s.getName() + " | " + "username: " + s.getUserName();
                list.getItems().addAll(string);
            }
        } else if (newValue.equals("organizers")) {
            list.getItems().clear();
            for (User s : registrar.getUsersByType("organizers")) {
                String string = "name: " + s.getName() + " | " + "username: " + s.getUserName();
                list.getItems().addAll(string);

            }
        } else if (newValue.equals("attendees")) {
            list.getItems().clear();
            for (User s : registrar.getUsersByType("attendees")) {
                String string = "name: " + s.getName() + " | " + "username: " + s.getUserName();
                list.getItems().addAll(string);
            }
        } else {
            list.getItems().clear();
            for (User s : registrar.getUsersByType("administrators")) {
                String string = "name: " + s.getName() + " | " + "username: " + s.getUserName();
                list.getItems().addAll(string);
            }
        }
    }
}




