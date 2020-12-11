package GUISystem;
import LoginSystem.LoginOptionsFacade;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.text.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * A GUI class for the login menu.
 *
 * @author Ziwen
 */
public class LoginGUI extends Application{

    private LoginOptionsFacade loginOptionsFacade;
    private MenuGetter menuGetter;

    /**
     * Will start the loginMenu.
     *
     * @param primaryStage The primaryStage of the application.
     */
    @Override
    public void start(Stage primaryStage){
        VBox loginCanvas = new VBox(5);
        loginCanvas.setAlignment(Pos.CENTER);
        loginCanvas.setPadding(new Insets(20, 20, 20, 20));
        loginCanvas.setPrefSize(500, 400);
        Scene scene = new Scene (loginCanvas);

        Text loginTitle = new Text("Welcome to the Conference!");
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
        Media song = new Media(new File("phase2/src/resources/O-Canada.mp3").toURI().toString());
        MediaPlayer mediaPlayer= new MediaPlayer(song);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        MediaView mediaView = new MediaView(mediaPlayer);

        Button loginButton = new Button("Login");
        loginButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));

        loginButton.setOnAction(e -> {
            String userName = usernameField.getText();
            String userPassword = passwordField.getText();

            if (loginOptionsFacade.login(userName, userPassword)) {
                // Stops playing song upon menu change
                mediaPlayer.stop();
                menuGetter.goHome(primaryStage);
            } else {
                failedLogin.setFill(Color.RED);
                failedLogin.setText("Login failed. Please check your credentials");
            }
        });

        loginCanvas.setOnKeyPressed(k -> {
            if(k.getCode() == KeyCode.ENTER) {
                String userName = usernameField.getText();
                String userPassword = passwordField.getText();

                if (loginOptionsFacade.login(userName, userPassword)) {
                    // Stops playing song upon menu change
                    mediaPlayer.stop();
                    menuGetter.goHome(primaryStage);
                } else {
                    failedLogin.setFill(Color.RED);
                    failedLogin.setText("Login failed. Please check your credentials");
                }
            }
        });

        Button accountCreationButton = new Button("Create Account");
        accountCreationButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        accountCreationButton.setPrefSize(130, 25);
        accountCreationButton.setOnAction(event -> {
            // Stops playing song upon menu change
            mediaPlayer.stop();
            menuGetter.goCreateAccount(primaryStage);
        });

        Button quitButton = new Button("Quit");
        quitButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        quitButton.setOnAction(event -> primaryStage.close());

        // mediaView added to vbox
        loginCanvas.getChildren().addAll(mediaView, loginTitle, username, usernameField, password, passwordField, loginButton, accountCreationButton, failedLogin, quitButton);

        //Plays song
        mediaPlayer.play();
        primaryStage.setTitle("Login - Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Sets the loginOptionsFacade for this class.
     *
     * @param loginOptionsFacade An instance of LoginOptionsFacade.
     */
    public void setLogin(LoginOptionsFacade loginOptionsFacade){
        this.loginOptionsFacade = loginOptionsFacade;
    }

    /**
     * Sets the menuGetter interface for this class.
     *
     * @param menuGetter An instance of MenuGetter interface.
     */
    public void setMenuGetter(MenuGetter menuGetter){
        this.menuGetter = menuGetter;
    }

}
