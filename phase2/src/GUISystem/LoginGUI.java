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


public class LoginGUI extends Application{
    private LoginOptionsFacade loginOptionsFacade;
    private String returnType;

    @Override
    public void start(Stage primaryStage){
        VBox loginCanvas = new VBox(5);
        loginCanvas.setAlignment(Pos.CENTER);
        loginCanvas.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene (loginCanvas, 500, 500);

        Text loginTitle = new Text ("Welcome to the Conference!");
        loginTitle.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD,30));

        Text failedLogin = new Text();


        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();



        Button loginButton = new Button("Login");
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
        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goAccountCreation(primaryStage);
            }
        });



        loginCanvas.getChildren().addAll(loginTitle, usernameField, passwordField, loginButton, returnButton, failedLogin);

        primaryStage.setTitle("Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void setLogin(LoginOptionsFacade loginOptionsFacade){
        this.loginOptionsFacade = loginOptionsFacade;
    }

    public String getReturnType(){
        return this.returnType;
    }

    private void goAccountCreation(Stage primaryStage){
        MenuFacade.getAccountCreationMenu().start(primaryStage);
    }

    private void goHome(Stage primaryStage){
        //MenuFacade.getHomeMenuGUI().start(primaryStage);
    }
}
