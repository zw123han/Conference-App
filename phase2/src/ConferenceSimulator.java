import EventSystem.*;
import GUISystem.*;
import LoginSystem.*;
import MessagingSystem.*;
import UserSystem.*;
import DatabaseSystem.*;
import com.mongodb.BasicDBObject;
import javafx.application.Application;

import java.util.*;

/**
 * This class represents the executable for the application.
 *
 * @author  Fred, Ziwen
 */
public class ConferenceSimulator {

    ReadEvents readEvents;
    ReadUsers readUsers;

    SaveEvents saveEvents;
    StoreUsers storeUsers;
    Registrar registrar;
    EventManager eventManager;

    ReadChat readChat;
    StoreChat storeChat;
    ReadProfanityList readProfanitiesList;
    ChatroomManager chatroomManager;
    HashMap<String, String> profanities;
    HashMap<String, String> profanities2;

    DatabaseInteractor databaseInteractor = new DatabaseInteractor();
    /**
     *  Constructor for Conference simulator. Creates gateways and necessary use cases to store data.
     */
    public ConferenceSimulator() {
        String userFilepath = "phase2/src/UserSystem/userData.ser";
        String eventFilepath = "phase2/src/EventSystem/eventData.ser";
        String chatFilepath = "phase2/src/MessagingSystem/chatlog.ser";
        String profanityListFilepath = "phase2/src/MessagingSystem/profanityList.ser";

        readEvents = new ReadEvents(eventFilepath);
        readUsers = new ReadUsers(userFilepath);

        saveEvents = new SaveEvents(eventFilepath);
        storeUsers = new StoreUsers(userFilepath);

        registrar = new Registrar();
        eventManager = new EventManager();
        chatroomManager = new ChatroomManager();

        readChat = new ReadChat(chatFilepath);
        storeChat = new StoreChat(chatFilepath);
        readProfanitiesList = new ReadProfanityList(profanityListFilepath);
        profanities = readProfanitiesList.readProfanities();

        // Connect to database and get use cases
        databaseInteractor.connect();
        registrar = (Registrar) databaseInteractor.readFromDatabase(registrar);
        eventManager = (EventManager) databaseInteractor.readFromDatabase(eventManager);
        chatroomManager = (ChatroomManager) databaseInteractor.readFromDatabase(chatroomManager);

        profanities2 = databaseInteractor.getProfanityList();
    }
    // Add a local save class in addition to the database save

    /**
     * The static main method will run this method to start the application.
     */
    public void run() {
        // Move all of this into a builder. No return necessary since we use a static class.
        System.out.println("The UI has been deleted and is being refactored. Do not panic.");

        // Set savables
        ArrayList<Savable> savables = new ArrayList<>(Arrays.asList(registrar, eventManager, chatroomManager));
        databaseInteractor.setSavables(savables);

        // Ok
        EventSignup eventSignup = new EventSignup();

        // Make administrator account
        AdminCreationScript adminCreationScript = new AdminCreationScript();
        adminCreationScript.createAdmin(registrar);

        // Make organizer account
        OrganizerCreationScript organizerCreationScript = new OrganizerCreationScript();
        organizerCreationScript.createOrganizers(registrar);

        // Add necessary presenters and controllers
        LoginOptionsFacade loginFacade = new LoginOptionsFacade(registrar);
        EventCreatorPresenter eventCreatorPresenter = new EventCreatorPresenter(eventManager, registrar);
        EventSignupPresenter eventSignupPresenter = new EventSignupPresenter(eventSignup, eventManager);
        ChatMenuPresenter chatMenuPresenter = new ChatMenuPresenter();
        FriendsPresenter friendsPresenter = new FriendsPresenter();

        // Lots of GUI creation
        MessageOutboxDataCollector outboxDateCollector = new MessageOutboxDataCollector("", registrar, eventManager);
        MessageOutboxController outboxController = new MessageOutboxController("", registrar, eventManager, chatroomManager);
        MessageOutboxPresenter outboxPresenter = new MessageOutboxPresenter(outboxController, outboxDateCollector);
        MessageOutboxGUI outboxGUI = new MessageOutboxGUI();
        outboxPresenter.setView(outboxGUI);
        outboxGUI.setOutboxElements(outboxPresenter);
        MessageInboxDataCollector inboxDataCollector = new MessageInboxDataCollector(registrar, "", chatroomManager, profanities);
        MessageInboxController inboxController = new MessageInboxController(registrar, "", chatroomManager);
        MessageInboxPresenter inboxPresenter = new MessageInboxPresenter(inboxDataCollector, inboxController);
        MessageInboxGUI inboxGUI = new MessageInboxGUI();
        inboxPresenter.setView(inboxGUI);
        inboxGUI.setInboxElements(inboxPresenter, outboxGUI);


        // Main user UI
        // Create menus and dependency inject necessary classes
        EventMenuGUI eventMenu = new EventMenuGUI();
        ManageEventMenu manageEventMenu = new ManageEventMenu();
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
        homeMenuGUI.setPasswordMenu(passwordMenu);
        homeMenuGUI.setSave(databaseInteractor);
        eventSignupPresenter.setInterface(eventMenu);
        eventCreatorPresenter.setInterface(manageEventMenu);
        eventMenu.setEventElements(eventSignupPresenter);
        manageEventMenu.setEventCreatorElements(eventCreatorPresenter);
        manageEventMenu.setFacade(loginFacade);


        // Create menu facade and DI menus
        MenuFacade menuFacade = new MenuFacade();
        menuFacade.set(loginGUI, accountCreationMenu, homeMenuGUI);

        //  DI userMenuGetter into various submenus
        inboxGUI.setUserMenuGetter(homeMenuGUI);
        passwordMenu.setUserMenuGetter(homeMenuGUI);
        eventMenu.setUserMenuGetter(homeMenuGUI);
        manageEventMenu.setUserMenuGetter(homeMenuGUI);

        // DI MenuGetter into menus
        loginGUI.setMenuGetter(menuFacade);
        accountCreationMenu.setMenuGetter(menuFacade);
        homeMenuGUI.setMenuGetter(menuFacade);

        // Launch application
        LaunchMenu.setMenuFacade(menuFacade);
        Application.launch(LaunchMenu.class);

        // Disconnect from database
        databaseInteractor.disconnect();

    }

}
