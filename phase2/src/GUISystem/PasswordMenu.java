package GUISystem;

import LoginSystem.LoginOptionsFacade;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class PasswordMenu extends Application {
    private LoginOptionsFacade loginOptionsFacade;
    private UserMenuGetter userMenuGetter;

    @Override
    public void start(Stage primaryStage){
        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene (vbox, 500, 500);

        Text title = new Text ("Change password");
        title.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"),20));

        Text passwordChangeMessage = new Text();
        passwordChangeMessage.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));

        TextField usernameField = new TextField();
        usernameField.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-regular.ttf"), 12));

        PasswordField currentPassword = new PasswordField();
        currentPassword.setFont(Font.font("Times New Roman"));

        PasswordField newPassword = new PasswordField();
        newPassword.setFont(Font.font("Times New Roman"));

        PasswordField passwordConfirmField = new PasswordField();
        passwordConfirmField.setFont(Font.font("Times New Roman"));

        Text username = new Text("Username");
        username.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-regular.ttf"), 12));
        Text passwordText = new Text("Current Password");
        passwordText.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-regular.ttf"), 12));
        Text newPasswordText = new Text("New Password");
        newPasswordText.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-regular.ttf"), 12));
        Text confirmPasswordText = new Text("Confirm New Password");
        confirmPasswordText.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-regular.ttf"), 12));

        Button changePasswordButton = new Button("Change password");
        changePasswordButton.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        changePasswordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(newPassword.getText().length()<1|passwordConfirmField.getText().length()<1){
                    passwordChangeMessage.setFill(Color.RED);
                    passwordChangeMessage.setText("Please ensure you are entering a valid password");
                }
                else if(!newPassword.getText().equals(passwordConfirmField.getText())){
                    passwordChangeMessage.setFill(Color.RED);
                    passwordChangeMessage.setText("New password does not match password confirmation");
                }
                else if(!loginOptionsFacade.getUser().getUserName().equals(usernameField.getText())){
                    passwordChangeMessage.setFill(Color.RED);
                    passwordChangeMessage.setText("Invalid username");
                }
                else if(loginOptionsFacade.resetPassword(usernameField.getText(), currentPassword.getText(), newPassword.getText())){
                    passwordChangeMessage.setText("Password change successful");
                }
                else{
                    passwordChangeMessage.setFill(Color.RED);
                    passwordChangeMessage.setText("Invalid password");
                }
            }
        });

        Button goBack = new Button("Go back");
        goBack.setFont(Font.loadFont(getClass().getResourceAsStream("/open-sans/os-bold.ttf"), 12));
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                userMenuGetter.goBack(primaryStage);
            }
        });



        vbox.getChildren().addAll(title,username, usernameField, passwordText, currentPassword, newPasswordText, newPassword, confirmPasswordText, passwordConfirmField, changePasswordButton,passwordChangeMessage,goBack);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setLogin(LoginOptionsFacade loginOptionsFacade){
        this.loginOptionsFacade = loginOptionsFacade;
    }

    public void setUserMenuGetter(UserMenuGetter userMenuGetter){
        this.userMenuGetter = userMenuGetter;
    }
}
