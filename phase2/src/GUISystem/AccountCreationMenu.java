package GUISystem;
import LoginSystem.LoginOptionsFacade;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.text.*;
import javafx.util.Duration;

import java.io.File;


public class AccountCreationMenu extends Application{
    private LoginOptionsFacade loginOptionsFacade;
    private MenuGetter menuGetter;

    @Override
    public void start(Stage primaryStage){
        VBox loginCanvas = new VBox(5);
        loginCanvas.setAlignment(Pos.CENTER);
        loginCanvas.setPadding(new Insets(20, 20, 20, 20));
        loginCanvas.setPrefSize(500, 400);
        Scene scene = new Scene (loginCanvas);

        Text loginTitle = new Text ("Welcome to the Conference!");
        loginTitle.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 30));

        Text accountMessage = new Text();
        accountMessage.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));

        TextField nameField = new TextField();
        nameField.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        TextField usernameField = new TextField();
        usernameField.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        PasswordField passwordField = new PasswordField();

        Text name = new Text("Name (First Name and Surname)");
        name.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-regular.ttf"), 12));
        Text username = new Text("Username");
        username.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-regular.ttf"), 12));
        Text password = new Text("Password");
        password.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-regular.ttf"), 12));

        // Play a song
        Media song = new Media(new File("phase2/src/resources/O-Canada.mp3").toURI().toString());
        MediaPlayer mediaPlayer= new MediaPlayer(song);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        MediaView mediaView = new MediaView(mediaPlayer);

        Button creationButton = new Button("Create Account");
        creationButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        creationButton.setPrefSize(130, 25);

        creationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String name = nameField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                if(loginOptionsFacade.createUser(name, username, password, "attendee")){
                    // Stops playing song upon menu change
                    //accountMessage.setFill(Color.GREEN);
                    //accountMessage.setText("Account creation successful. Please login.");

                    loginOptionsFacade.login(username, password);

                    Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(primaryStage);
                    VBox dialogVbox = new VBox(20);

                    Text text = new Text("Welcome " + name);
                    text.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 20));

                    dialogVbox.getChildren().add(text);
                    Scene dialogScene = new Scene(dialogVbox);

                    dialog.setScene(dialogScene);
                    dialog.show();
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished( event -> dialog.close() );
                    delay.play();
                    //mediaPlayer.stop();
                    menuGetter.goHome(primaryStage);
                }
                else{
                    accountMessage.setFill(Color.RED);
                    accountMessage.setText("Credentials are invalid for use. Please try again.");
                }
            }
        });

        loginCanvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if(k.getCode() == KeyCode.ENTER) {
                    String name = nameField.getText();
                    String username = usernameField.getText();
                    String password = passwordField.getText();

                    if(loginOptionsFacade.createUser(name, username, password, "attendee")) {
                        // Stops playing song upon menu change
                        loginOptionsFacade.login(username, password);
                        Stage dialog = new Stage();

                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(primaryStage);
                        VBox dialogVbox = new VBox(20);


                        Text text = new Text("Welcome " + name);
                        text.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 20));

                        dialogVbox.getChildren().add(text);
                        Scene dialogScene = new Scene(dialogVbox);

                        dialog.setScene(dialogScene);
                        dialog.show();
                        PauseTransition delay = new PauseTransition(Duration.seconds(1));
                        delay.setOnFinished( event -> dialog.close() );
                        delay.play();
                        //mediaPlayer.stop();
                        menuGetter.goHome(primaryStage);
                    } else {
                        accountMessage.setFill(Color.RED);
                        accountMessage.setText("Credentials are invalid for use. Please try again.");
                    }
                }
            }
        });

        Button loginButton = new Button("Back to Login");
        loginButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        loginButton.setPrefSize(130, 25);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //mediaPlayer.stop();
                menuGetter.goLogin(primaryStage);
            }
        });


        Button quitButton = new Button("Quit");
        quitButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        // mediaView added to vbox
        loginCanvas.getChildren().addAll(mediaView, loginTitle, name, nameField, username, usernameField, password, passwordField, creationButton, loginButton, accountMessage, quitButton);

        // Play the song
        //mediaPlayer.play();
        //primaryStage.setMinHeight(600);
        //primaryStage.setMaxHeight(600);
        //primaryStage.setHeight(600);
        //primaryStage.setMinWidth(500);
        //primaryStage.setMaxWidth(500);
        //primaryStage.setWidth(500);
        primaryStage.setTitle("Account Creation - Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void setLogin(LoginOptionsFacade loginOptionsFacade){
        this.loginOptionsFacade = loginOptionsFacade;
    }


    public void setMenuGetter(MenuGetter menuGetter) {
        this.menuGetter = menuGetter;
    }
}
