package GUISystem;
import LoginSystem.LoginOptionsFacade;
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
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.text.*;


/**
 * The GUI Menu to create an account.
 *
 * @author Ziwen
 */
public class AccountCreationMenu extends Application{

    private LoginOptionsFacade loginOptionsFacade;
    private MenuGetter menuGetter;

    /**
     * The main executable method of this class. Will start the menu.
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
                    loginOptionsFacade.login(username, password);
                    Stage dialog = new Stage();

                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(primaryStage);
                    VBox dialogVbox = new VBox(20);
                    dialogVbox.setAlignment(Pos.CENTER);

                    Text text = new Text("Account created successfully.");
                    text.setFont(Font.loadFont(getClass().getResourceAsStream(
                            "/resources/os-bold.ttf"), 20));

                    Button loginButton = new Button("Login");
                    loginButton.setOnAction(lb -> {
                            dialog.close();
                            menuGetter.goHome(primaryStage);});

                    dialogVbox.setOnKeyPressed(k -> {
                        if (k.getCode() == KeyCode.ENTER) {
                            dialog.close();
                            menuGetter.goHome(primaryStage);
                        }
                    });

                    dialogVbox.getChildren().addAll(text, loginButton);

                    Scene dialogScene = new Scene(dialogVbox);
                    dialog.setTitle("Success");
                    dialog.setScene(dialogScene);
                    dialog.showAndWait();
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
                        loginOptionsFacade.login(username, password);
                        Stage dialog = new Stage();

                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(primaryStage);
                        VBox dialogVbox = new VBox(20);
                        dialogVbox.setAlignment(Pos.CENTER);

                        Text text = new Text("Account created successfully.");
                        text.setFont(Font.loadFont(getClass().getResourceAsStream(
                                "/resources/os-bold.ttf"), 20));

                        Button loginButton = new Button("Login");
                        loginButton.setOnAction(lb -> {
                            dialog.close();
                            menuGetter.goHome(primaryStage);});

                        dialogVbox.setOnKeyPressed(k1 -> {
                            if (k1.getCode() == KeyCode.ENTER) {
                                dialog.close();
                                menuGetter.goHome(primaryStage);
                            }
                        });

                        dialogVbox.getChildren().addAll(text, loginButton);

                        Scene dialogScene = new Scene(dialogVbox);
                        dialog.setTitle("Success");
                        dialog.setScene(dialogScene);
                        dialog.showAndWait();
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

        loginButton.setOnAction(event -> menuGetter.goLogin(primaryStage));

        Button quitButton = new Button("Quit");
        quitButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        quitButton.setOnAction(event -> primaryStage.close());

        // mediaView added to vbox
        loginCanvas.getChildren().addAll(loginTitle, name, nameField, username, usernameField, password, passwordField, creationButton, loginButton, accountMessage, quitButton);

        primaryStage.setTitle("Account Creation - Conference Simulator Phase 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Sets the loginOptionsFacade instance variable of this class.
     *
     * @param loginOptionsFacade An instance of LoginOptionsFacade.
     */
    public void setLogin(LoginOptionsFacade loginOptionsFacade){
        this.loginOptionsFacade = loginOptionsFacade;
    }

    /**
     * Sets the menuGetter interface of this class.
     *
     * @param menuGetter An instance of the MenuGetter of this class.
     */
    public void setMenuGetter(MenuGetter menuGetter) {
        this.menuGetter = menuGetter;
    }
}
