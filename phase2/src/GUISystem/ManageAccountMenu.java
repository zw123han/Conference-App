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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManageAccountMenu extends Application {
    private User user;
    private UserMenuGetter mg;
    private LoginOptionsFacade facade;
    private ListView<String> row2;
    private Registrar registrar;
    public void setUserMenuGetter(UserMenuGetter userMenuGetter) {
        this.mg = userMenuGetter;
    }
    public void setFacade(LoginOptionsFacade facade) {this.facade = facade;}
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage primaryStage) {
        registrar = facade.getRegistrar();

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20, 20, 20, 20));
        root.setPrefSize(500, 600);
        HBox row1 = new HBox();
        row2 = new ListView<>();
        HBox row3 = new HBox();
        // row 1
        Label label = new Label("Please specify the type of accounts to view: ");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("speaker", "organizer", "attendee", "administrator");
        row1.getChildren().addAll(label, choiceBox);
        // row 2
        // row 3
        Button createButton = new Button("Create");
        Button deleteButton = new Button("Delete");
        Button modifyButton = new Button("Modify");
        Button goBack = new Button("Back");
        row3.getChildren().addAll(createButton, deleteButton, modifyButton, goBack);

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
            layout.setPadding(new Insets(20, 20, 20, 20));

            HBox titleBox = new HBox();
            Label title = new Label("Please specify the type of account");
            ChoiceBox<String> choice = new ChoiceBox<>();
            choice.getItems().addAll("attendee", "speaker", "organizer", "administrator");
            titleBox.getChildren().addAll(title, choice);
            choice.setValue("attendee");

            HBox nameBox = new HBox();
            Label name = new Label("Name");
            TextField nameInput = new TextField();
            nameBox.getChildren().addAll(name, nameInput);

            HBox usernameBox = new HBox();
            Label username = new Label("Username");
            TextField usernameInput = new TextField();
            usernameBox.getChildren().addAll(username, usernameInput);

            HBox passwordBox = new HBox();
            Label password = new Label("Password");
            TextField passwordInput = new TextField();
            passwordBox.getChildren().addAll(password, passwordInput);

            Button submitButton = new Button("Submit");
            submitButton.setOnAction(ae -> {
                String name1 = nameInput.getText();
                String userName1 = usernameInput.getText();
                String password1 = passwordInput.getText();
                if (facade.createUser(name1, userName1, password1, choice.getValue())) {
                    createPopUp("Account created successfully");
                    choiceBoxListener(registrar, choice.getValue(), row2);
                    choiceBox.setValue(choice.getValue());
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
        // delete user
        deleteButton.setOnAction(e -> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Create User");

            VBox layout = new VBox(5);
            layout.setPadding(new Insets(20, 20, 20, 20));

            HBox titleBox = new HBox();
            Label title = new Label("Enter the user you would like the delete: \n(You cannot delete yourself) ");
            TextField input = new TextField();
            titleBox.getChildren().addAll(title, input);


            Button submitButton = new Button("Submit");

            submitButton.setOnAction(ae -> {
                String username = input.getText();
                if(!registrar.userExisting(username)){
                    createPopUp("No such user with username "   + username);
                }
                else{
                String userType = registrar.getUserType(username);
                String userTypeLower = userType.toLowerCase();
                String userTypeLower1 = userTypeLower.substring(userTypeLower.indexOf(".")+1);
                if (facade.deleteUser(input.getText())) {
                    createPopUp(username +" has been deleted");
                    choiceBoxListener(registrar, userTypeLower1, row2);
                    choiceBox.setValue(userTypeLower1);
                } else {
                    createPopUp("Unable to delete user");
                }}

            });

            Button closeButton = new Button("Close");
            closeButton.setOnAction(ae -> window.close());

            layout.getChildren().addAll(titleBox, submitButton, closeButton);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
        // modify users
        modifyButton.setOnAction(event -> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Modify User");

            VBox layout = new VBox(5);
            layout.setPadding(new Insets(20, 20, 20, 20));
            layout.setPrefSize(500, 600);
            HBox enterNameBox = new HBox();
            Label enterName = new Label("Enter the username to modify: ");
            TextField input = new TextField();
            enterNameBox.getChildren().addAll(enterName, input);

            HBox selectBox = new HBox();
            Label select = new Label("View accounts");
            ChoiceBox<String> choice = new ChoiceBox<>();
            choice.getItems().addAll("attendee", "speaker", "organizer", "administrator");
            selectBox.getChildren().addAll(select, choice);

            VBox  enterNewNameBox = new VBox();
            Label instructions = new Label("Enter any fields you want to update, and leave the rest blank\n");
            Label enterNewName = new Label("Enter the new name for the user: ");
            TextField newName = new TextField();
            Label enterNewType = new Label("Enter the new type for the user: \n(\"attendee\", \"speaker\", \"organizer\", or \"administrator\") \n(You cannot modify your own type)");
            TextField newType = new TextField();
            Label enterNewUsername = new Label("Enter the new username for this user: ");
            TextField newUsername = new TextField();

            enterNewNameBox.getChildren().addAll(instructions, enterNewName, newName, enterNewType, newType, enterNewUsername, newUsername);

            ListView<String> list = new ListView();

            choice.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
                choiceBoxListener(registrar, newValue, list);
            });

            Button submitButton = new Button("Submit");
            submitButton.setOnAction(ae -> {
                String username = input.getText();
                if(!registrar.userExisting(username)){
                    createPopUp("No such user with username "+username);
                }
                else{
                String newName1 = newName.getText();
                String newType1 = newType.getText();
                String newUsername1 = newUsername.getText();
                String message = "No changes made";
                String userType = registrar.getUserType(username);

                if(newName1.length()>=1&& facade.updateName(username, newName1)){
                    message = "";
                    message += "The new name for " + username +" has been set to: "+newName1+"\n";
                }
                if(newType1.length()>=1&& facade.updateUserType(username, newType1)){
                    userType = newType1;
                    if(message.equals("No changes made")){
                        message = "";
                    }
                    message += "The new user type for " + username + " has been set to: "+newType1+"\n";
                }

                if(newUsername1.length()>1&& facade.updateUsername(username, newUsername1)){
                    if(message.equals("No changes made")){
                        message = "";
                    }
                    message += "The username for " + username + " has been updated to " + newUsername1;}

                createPopUp(message);

                String userTypeLower = userType.toLowerCase();
                String userTypeLower1 = userTypeLower.substring(userTypeLower.indexOf(".")+1);
                choiceBoxListener(registrar, userTypeLower1, list);
                choiceBoxListener(registrar, userTypeLower1, row2);
                choiceBox.setValue(userTypeLower1);}



            });

            Button closeButton = new Button("Close");
            closeButton.setOnAction(ae -> window.close());

            layout.getChildren().addAll(enterNameBox, enterNewNameBox, selectBox, list, submitButton, closeButton);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
        // go back
        goBack.setOnAction(e -> {
            mg.goBack(primaryStage);
        });
        root.getChildren().addAll(row1, row2, row3);
        //Scene scene = new Scene (root, 800, 600);
        Scene scene = new Scene (root);
        primaryStage.setTitle("Manage Accounts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void createPopUp(String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("");

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

        if (newValue.equals("speaker")) {
            list.getItems().clear();
            for (User s : registrar.getUsersByType("Speaker")) {
                String string = "name: " + s.getName() + " | " + "username: " + s.getUserName();
                list.getItems().addAll(string);
            }
        } else if (newValue.equals("organizer")) {
            list.getItems().clear();
            for (User s : registrar.getUsersByType("Organizer")) {
                String string = "name: " + s.getName() + " | " + "username: " + s.getUserName();
                list.getItems().addAll(string);

            }
        } else if (newValue.equals("attendee")) {
            list.getItems().clear();
            for (User s : registrar.getUsersByType("Attendee")) {
                String string = "name: " + s.getName() + " | " + "username: " + s.getUserName();
                list.getItems().addAll(string);
            }
        } else {
            list.getItems().clear();
            for (User s : registrar.getUsersByType("Administrator")) {
                String string = "name: " + s.getName() + " | " + "username: " + s.getUserName();
                list.getItems().addAll(string);
            }
        }
    }
}




