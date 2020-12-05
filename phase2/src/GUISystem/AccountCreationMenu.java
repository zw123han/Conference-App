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
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.text.*;


public class AccountCreationMenu extends Application implements MenuInteractor, LoginInteractor{
    private LoginOptionsFacade loginOptionsFacade;
    private MenuGetter menuGetter;

    @Override
    public void start(Stage primaryStage){
        VBox loginCanvas = new VBox(5);
        loginCanvas.setAlignment(Pos.CENTER);
        loginCanvas.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene (loginCanvas, 500, 500);

        Text loginTitle = new Text ("Welcome to the Conference!");
        loginTitle.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD,30));

        Text accountMessage = new Text();

        TextField nameField = new TextField();
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();



        Button creationButton = new Button("Create Account");
        creationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String name = nameField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                if(loginOptionsFacade.createUser(name, username, password, "attendee")){
                    accountMessage.setText("Account creation successful. Please login to continue.");
                }
                else{
                    accountMessage.setFill(Color.RED);
                    accountMessage.setText("Credentials are invalid for use. Please try again.");
                }
            }
        });

        Button loginButton = new Button("Login");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goLogin(primaryStage);
            }
        });



        loginCanvas.getChildren().addAll(loginTitle, nameField, usernameField, passwordField, creationButton, loginButton, accountMessage);

        primaryStage.setTitle("Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void setLogin(LoginOptionsFacade loginOptionsFacade){
        this.loginOptionsFacade = loginOptionsFacade;
    }

    private void goLogin(Stage primaryStage){
        menuGetter.getLoginGUI().start(primaryStage);
    }

    @Override
    public void setMenuGetter(MenuGetter menuGetter) {
        this.menuGetter = menuGetter;
    }
}
