package GUISystem;

import DatabaseSystem.DatabaseInteractor;
import LoginSystem.LoginOptionsFacade;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class HomeMenuGUI extends Application implements UserMenuGetter {
    private LoginOptionsFacade loginOptionsFacade;
    private MenuGetter menuGetter;
    private MessageInboxGUI messageMenu;
    private FriendsMenuGUI friendsMenu;
    private PasswordMenu passwordMenu;
    private RoomMenu roomMenu;
    private DatabaseInteractor databaseInteractor;
    private EventMenuGUI eventMenu;
    private ManageEventMenu manageEventMenu;
    private ManageAccountMenu manageAccountMenu;

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene (vbox);

        Text title = new Text ("Hello "+loginOptionsFacade.getUser().getName());
        title.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"),30));


        Button eventButton = new Button("Events");
        eventButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        eventButton.setPrefSize(100, 25);
        eventButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                eventMenu.setUser(loginOptionsFacade.getUser());
                eventMenu.start(primaryStage);
            }
        });

        Button friendsButton = new Button("Friends");
        friendsButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        friendsButton.setPrefSize(100, 25);
        friendsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                friendsMenu.setUser(loginOptionsFacade.getUser());
                friendsMenu.start(primaryStage);
            }
        });

        Button changePasswordButton = new Button("Password");
        changePasswordButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        changePasswordButton.setPrefSize(100, 25);
        changePasswordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                passwordMenu.start(primaryStage);
            }
        });

        Button messagingButton = new Button("Messages");
        messagingButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        messagingButton.setPrefSize(100, 25);
        messagingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                messageMenu.setLogin(loginOptionsFacade.getUser().getUserName());
                messageMenu.display(primaryStage);
            }
        });

        Button manageAccountButton = new Button("Manage Accounts");
        manageAccountButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        manageAccountButton.setTextFill(Color.RED);
        manageAccountButton.setPrefSize(130, 30);
        manageAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                manageAccountMenu.setUser(loginOptionsFacade.getUser());
                manageAccountMenu.start(primaryStage);
            }
        });

        Button manageEventsButton = new Button("Manage Events");
        manageEventsButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        manageEventsButton.setTextFill(Color.RED);
        manageEventsButton.setPrefSize(130, 30);
        manageEventsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Go to manage events menu
                manageEventMenu.start(primaryStage);
            }
        });
        Button roomMenuButton = new Button("Manage Rooms");
        roomMenuButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        roomMenuButton.setTextFill(Color.RED);
        roomMenuButton.setPrefSize(130, 30);
        roomMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Go to manage events menu
                roomMenu.start(primaryStage);
            }
        });
        Text emptyText = new Text();
        Button logoutButton = new Button("Logout");
        logoutButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        logoutButton.setPrefSize(100, 25);;
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginOptionsFacade.logout();
                databaseInteractor.saveToDatabase();
                System.out.println("Save successful");
                menuGetter.goLogin(primaryStage);
            }
        });

        Button saveButton = new Button("Save");
        saveButton.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/os-bold.ttf"), 12));
        saveButton.setPrefSize(100, 25);
        saveButton.setTextFill(Color.GREEN);
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                databaseInteractor.saveToDatabase();
                System.out.println("Save successful");
            }
        });

        vbox.getChildren().addAll(title,eventButton, friendsButton, changePasswordButton, messagingButton);
        if (loginOptionsFacade.getUser().getUserType().equals("administrator")){
            vbox.getChildren().addAll(manageAccountButton, manageEventsButton, roomMenuButton );
        }
        else if (loginOptionsFacade.getUser().getUserType().equals("organizer")){
            vbox.getChildren().add(manageEventsButton);
        }
        vbox.getChildren().addAll(emptyText, logoutButton, saveButton);

        primaryStage.setTitle("Home Screen - Conference Simulator Phase 2");
        primaryStage.setWidth(Screen.getPrimary().getBounds().getWidth()/4);
        primaryStage.setHeight(Screen.getPrimary().getBounds().getHeight()/1.75);
        //primaryStage.setMinHeight(600);
        //primaryStage.setMaxHeight(600);
        //primaryStage.setMinWidth(500);
        //primaryStage.setMaxWidth(500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public void setLogin(LoginOptionsFacade loginOptionsFacade) {
        this.loginOptionsFacade = loginOptionsFacade;
    }
    public void setMenuGetter(MenuGetter menuGetter) {
        this.menuGetter = menuGetter;
    }
    public void setEventMenu(EventMenuGUI eventMenu) {this.eventMenu = eventMenu;}
    public void setManageEventMenu(ManageEventMenu manageEventMenu) {this.manageEventMenu = manageEventMenu;}
    public void setManageAccountMenu(ManageAccountMenu manageAccountMenu) {this.manageAccountMenu = manageAccountMenu;}
    public void setMessageMenu(MessageInboxGUI messageMenu) {
        this.messageMenu = messageMenu;
    }
    public void setFriendsMenu(FriendsMenuGUI friendsMenu) {
        this.friendsMenu = friendsMenu;
    }
    public void setPasswordMenu(PasswordMenu passwordMenu){
        this.passwordMenu = passwordMenu;
    }
    public void setRoomMenu(RoomMenu roomMenu) {this.roomMenu = roomMenu;}

    public void setSave(DatabaseInteractor databaseInteractor){
        this.databaseInteractor = databaseInteractor;
    }

    @Override
    public void goBack(Stage primaryStage) {
        start(primaryStage);
    }

}
