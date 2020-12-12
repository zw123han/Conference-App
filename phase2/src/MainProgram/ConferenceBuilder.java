package MainProgram;

import DatabaseSystem.DatabaseInteractor;
import DatabaseSystem.Savable;
import EventSystem.EventCreatorPresenter;
import EventSystem.EventManager;
import EventSystem.EventSignup;
import EventSystem.EventSignupPresenter;
import GUISystem.*;
import LoginSystem.LoginOptionsFacade;
import MessagingSystem.*;
import RoomSystem.RoomManager;
import RoomSystem.RoomPresenter;
import UserSystem.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Builder class for Conference Simulator, which is the main executable for this program.
 *
 * @author Ziwen
 */
public class ConferenceBuilder {
    private DatabaseInteractor databaseInteractor;
    private Registrar registrar;
    private EventManager eventManager;
    private RoomManager roomManager;
    private ChatroomManager chatroomManager;
    private HashMap<String, String> profanities;

    private EventSignup eventSignup = new EventSignup();
    private FriendsPresenter friendsPresenter = new FriendsPresenter();

    private LoginOptionsFacade loginFacade;
    private EventCreatorPresenter eventCreatorPresenter;
    private EventSignupPresenter eventSignupPresenter;
    private FriendsController friendsController;
    private RoomPresenter roomPresenter;

    /**
     * Constructor for ConferenceBuilder.
     *
     * @param databaseInteractor The database interactor that will pull and push data from and to the database.
     */
    public ConferenceBuilder(DatabaseInteractor databaseInteractor){
        this.databaseInteractor = databaseInteractor;
    }

    private void getSavables(){
        // For database saving
        this.registrar = (Registrar) databaseInteractor.readFromDatabase(new Registrar());
        this.eventManager = (EventManager) databaseInteractor.readFromDatabase(new EventManager());
        this.chatroomManager = (ChatroomManager) databaseInteractor.readFromDatabase(new ChatroomManager());
        this.profanities = databaseInteractor.getProfanityList();
        this.roomManager = (RoomManager) databaseInteractor.readFromDatabase(new RoomManager());

    }

    private void setSavables(){
        ArrayList<Savable> savables = new ArrayList<>(Arrays.asList(registrar, eventManager, chatroomManager, roomManager));
        databaseInteractor.setSavables(savables);
    }

    //deprecated
    private void makeAdmins(){
        AdminCreationScript adminCreationScript = new AdminCreationScript();
        adminCreationScript.createAdmin(registrar);
    }

    //deprecated
    private void makeOrganizers(){
        OrganizerCreationScript organizerCreationScript = new OrganizerCreationScript();
        organizerCreationScript.createOrganizers(registrar);
    }

    private void makeControllersPresenters(){
        this.loginFacade = new LoginOptionsFacade(registrar, eventManager, chatroomManager);
        this.eventCreatorPresenter = new EventCreatorPresenter(eventManager, registrar, roomManager);
        this.eventSignupPresenter = new EventSignupPresenter(eventSignup, eventManager);
        this.friendsController = new FriendsController(registrar, friendsPresenter);
        this.roomPresenter = new RoomPresenter(roomManager, eventManager);
    }

    private void makeMenus(){
        // Create menus and dependency inject necessary classes
        MessageOutboxController outboxController = new MessageOutboxController("", registrar, eventManager, chatroomManager);
        MessageOutboxPresenter outboxPresenter = new MessageOutboxPresenter(outboxController);
        MessageOutboxGUI outboxGUI = new MessageOutboxGUI();
        outboxGUI.setOutboxElements(outboxPresenter);
        MessageInboxController inboxDataCollector = new MessageInboxController(registrar, "", chatroomManager, profanities);
        MessageInboxPresenter inboxPresenter = new MessageInboxPresenter(inboxDataCollector);
        MessageInboxGUI inboxGUI = new MessageInboxGUI();
        inboxPresenter.setView(inboxGUI);
        inboxGUI.setInboxElements(inboxPresenter, outboxGUI);

        EventMenuGUI eventMenu = new EventMenuGUI();
        ManageEventMenu manageEventMenu = new ManageEventMenu();
        FriendsMenuGUI friendsMenuGUI = new FriendsMenuGUI();
        ManageAccountMenu manageAccountMenu = new ManageAccountMenu();
        RoomMenu roomMenu = new RoomMenu();
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setLogin(loginFacade);
        AccountCreationMenu accountCreationMenu = new AccountCreationMenu();
        accountCreationMenu.setLogin(loginFacade);
        PasswordMenu passwordMenu = new PasswordMenu();
        passwordMenu.setLogin(loginFacade);
        HomeMenuGUI homeMenuGUI = new HomeMenuGUI();
        homeMenuGUI.setLogin(loginFacade);
        homeMenuGUI.setMessageMenu(inboxGUI);
        homeMenuGUI.setEventMenu(eventMenu);
        homeMenuGUI.setManageEventMenu(manageEventMenu);
        homeMenuGUI.setFriendsMenu(friendsMenuGUI);
        homeMenuGUI.setManageAccountMenu(manageAccountMenu);
        homeMenuGUI.setPasswordMenu(passwordMenu);
        homeMenuGUI.setRoomMenu(roomMenu);

        homeMenuGUI.setSave(databaseInteractor);

        eventSignupPresenter.setInterface(eventMenu);
        eventCreatorPresenter.setInterface(manageEventMenu);
        friendsController.setInterface(friendsMenuGUI);
        roomPresenter.setInterface(roomMenu);
        eventMenu.setEventElements(eventSignupPresenter);
        manageEventMenu.setEventCreatorElements(eventCreatorPresenter, roomPresenter);
        manageEventMenu.setFacade(loginFacade);
        manageAccountMenu.setFacade(loginFacade);
        friendsMenuGUI.setFriendsElements(friendsController);
        roomMenu.setRoomElements(roomPresenter);

        // Create menu facade and DI menus
        MenuFacade menuFacade = new MenuFacade();
        menuFacade.set(loginGUI, accountCreationMenu, homeMenuGUI);

        //  Dependency inject userMenuGetter into various submenus
        inboxGUI.setUserMenuGetter(homeMenuGUI);
        passwordMenu.setUserMenuGetter(homeMenuGUI);
        eventMenu.setUserMenuGetter(homeMenuGUI);
        manageEventMenu.setUserMenuGetter(homeMenuGUI);
        friendsMenuGUI.setUserMenuGetter(homeMenuGUI);
        roomMenu.setUserMenuGetter(homeMenuGUI);
        manageAccountMenu.setUserMenuGetter(homeMenuGUI);

        // Dependency inject MenuGetter into menus
        loginGUI.setMenuGetter(menuFacade);
        accountCreationMenu.setMenuGetter(menuFacade);
        homeMenuGUI.setMenuGetter(menuFacade);

        // Add menuFacade to the application
        LaunchMenu.setMenuFacade(menuFacade);
    }

    /**
     * Builds all the necessities to use ConferenceSimulator.
     */
    public void buildAConference(){
        getSavables();
        setSavables();
        //makeAdmins();
        //makeOrganizers();
        makeControllersPresenters();
        makeMenus();
    }

}
