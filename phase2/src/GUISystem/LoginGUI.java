package GUISystem;
import LoginSystem.LoginOptionsFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.text.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class LoginGUI extends Application{
    private LoginOptionsFacade loginOptionsFacade;
    private MenuGetter menuGetter;

    @Override
    public void start(Stage primaryStage){
        VBox loginCanvas = new VBox(5);
        loginCanvas.setAlignment(Pos.CENTER);
        loginCanvas.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene (loginCanvas, 500, 600);

        Text loginTitle = new Text ("Welcome to the Conference!");
        loginTitle.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"),30));

        Text failedLogin = new Text();
        failedLogin.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));

        TextField usernameField = new TextField();
        usernameField.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-regular.ttf"), 12));
        PasswordField passwordField = new PasswordField();
        passwordField.setFont(Font.font("Times New Roman"));

        Text username = new Text("Username");
        username.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-regular.ttf"), 12));
        Text password = new Text("Password");
        password.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-regular.ttf"), 12));

        // Play a song
        Media song = new Media(new File("phase2/src/GUISystem/O-Canada.mp3").toURI().toString());
        MediaPlayer mediaPlayer= new MediaPlayer(song);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        MediaView mediaView = new MediaView(mediaPlayer);

        Button loginButton = new Button("Login");
        loginButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                if(loginOptionsFacade.login(username, password)){
                    // Stops playing song upon menu change
                    mediaPlayer.stop();
                    menuGetter.goHome(primaryStage);
                }
                else{
                    failedLogin.setFill(Color.RED);
                    failedLogin.setText("Login failed. Please check your credentials");
                }
            }
        });

        Button accountCreationButton = new Button("Create Account");
        accountCreationButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        accountCreationButton.setPrefSize(130, 25);
        accountCreationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Stops playing song upon menu change
                mediaPlayer.stop();
                menuGetter.goCreateAccount(primaryStage);
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
        loginCanvas.getChildren().addAll(mediaView, loginTitle, username, usernameField, password, passwordField, loginButton, accountCreationButton, failedLogin, quitButton);

        //Plays song
        mediaPlayer.play();
        primaryStage.setMinHeight(600);
        primaryStage.setMaxHeight(600);
        primaryStage.setHeight(600);
        primaryStage.setMinWidth(500);
        primaryStage.setMaxWidth(500);
        primaryStage.setWidth(500);
        primaryStage.setTitle("Login - Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public void setLogin(LoginOptionsFacade loginOptionsFacade){
        this.loginOptionsFacade = loginOptionsFacade;
    }

    public void setMenuGetter(MenuGetter menuGetter){
        this.menuGetter = menuGetter;
    }

}
