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
import javafx.stage.*;
import javafx.scene.text.*;


public class LoginGUI extends Application{
    private LoginOptionsFacade loginOptionsFacade;

    @Override
    public void start(Stage primaryStage){
        VBox loginCanvas = new VBox(5);
        loginCanvas.setAlignment(Pos.CENTER);
        loginCanvas.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene (loginCanvas, 500, 500);

        Text loginTitle = new Text ("Welcome to the Conference!");
        loginTitle.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD,30));




        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                loginOptionsFacade.login(username, password);
            }
        });





        loginCanvas.getChildren().addAll(loginTitle, loginButton, usernameField, passwordField);

        primaryStage.setTitle("Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void display(){

    };

    public void setLogin(LoginOptionsFacade loginOptionsFacade){
        this.loginOptionsFacade = loginOptionsFacade;
    }


}
