package GUISystem;
import LoginSystem.LoginOptionsFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.text.*;

import java.io.IOException;


public class LoginGUI extends Application implements MenuInteractor, LoginInteractor{
    private LoginOptionsFacade loginOptionsFacade;
    private MenuGetter menuGetter;

    @Override
    public void start(Stage primaryStage){
        VBox loginCanvas = new VBox(5);
        loginCanvas.setAlignment(Pos.CENTER);
        loginCanvas.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene (loginCanvas, 500, 500);

        Text loginTitle = new Text ("Welcome to the Conference!");
        loginTitle.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"),30));

        Text failedLogin = new Text();


        TextField usernameField = new TextField();
        usernameField.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-regular.ttf"), 12));
        PasswordField passwordField = new PasswordField();
        passwordField.setFont(Font.font("Times New Roman"));


        Button loginButton = new Button("Login");
        loginButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                if(loginOptionsFacade.login(username, password)){
                    goHome(primaryStage);
                }
                else{
                    failedLogin.setFill(Color.RED);
                    failedLogin.setText("Login failed. Please check your credentials");
                }
            }
        });

        Button returnButton = new Button("Create Account Instead");
        returnButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goAccountCreation(primaryStage);
            }
        });

        // Add a quit button to each login/creation screen

        loginCanvas.getChildren().addAll(loginTitle, usernameField, passwordField, loginButton, returnButton, failedLogin);

        primaryStage.setTitle("Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void setLogin(LoginOptionsFacade loginOptionsFacade){
        this.loginOptionsFacade = loginOptionsFacade;
    }

    @Override
    public void setMenuGetter(MenuGetter menuGetter){
        this.menuGetter = menuGetter;
    }

    private void goAccountCreation(Stage primaryStage){
        menuGetter.getAccountCreationMenu().start(primaryStage);
    }

    private void goHome(Stage primaryStage){
        menuGetter.getHomeMenuGUI().start(primaryStage);
    }
}
