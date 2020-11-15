import java.util.*;

/**
 * This class represents the executable for the application.
 *
 * @author  ..., ..., Fred
 * @version %I%, %G%
 */
public class ConferenceSimulator {

    ReadEvents readEvents;
    ReadUsers readUsers;

    SaveEvents saveEvents;
    StoreUsers storeUsers;
    Registrar registrar;
    EventManager eventManager;

    public ConferenceSimulator() {
        String userFilepath = "phase1/src/userData.ser";
        String eventFilepath = "phase1/src/eventData.ser";
        // Should we also have chatlog filepath?
        // We could add filepaths as parameters

        readEvents = new ReadEvents(eventFilepath);
        readUsers = new ReadUsers(userFilepath);

        saveEvents = new SaveEvents(eventFilepath);
        storeUsers = new StoreUsers(userFilepath);
        registrar = new Registrar(readUsers.read());
        eventManager = new EventManager(readEvents.read());
    }

    /**
     *
     */
    public void save(){
        storeUsers.store(registrar.getUsers());
        saveEvents.saveEvents(eventManager.getEventsList());
    }

    /**
     * The static main method will run this method to start the application.
     */
    public void run() {

        EventSignup eventSignup = new EventSignup();

        // Make admin accounts
        OrganizerCreationScript organizerCreationScript = new OrganizerCreationScript();
        organizerCreationScript.createOrganizers(registrar);

        // Add necessary presenters and controllers
        LoginOptionsFacade loginFacade = new LoginOptionsFacade(registrar);
        EventCreatorPresenter eventCreatorPresenter = new EventCreatorPresenter(eventManager, registrar);
        EventSignupPresenter eventSignupPresenter = new EventSignupPresenter(eventSignup, eventManager);
        ChatMenuPresenter chatMenuPresenter = new ChatMenuPresenter();
        FriendsPresenter friendsPresenter = new FriendsPresenter();

        // Main user UI
        UserOptionsInterface ui = new UserOptionsInterface(loginFacade, eventCreatorPresenter, eventSignupPresenter,
                chatMenuPresenter, friendsPresenter, eventManager);



        // Run the program
        Scanner sc = new Scanner(System.in);
        boolean exit;
        do{
            do {
                ui.loggedIn();
                save();
            } while (loginFacade.getUser() != null);
            System.out.println("Press any key to log in again, or press Q to close the program.");
            exit = sc.nextLine().equals("Q");

        } while (!exit);



        // Reset user and event data
        // ArrayList<User> emptyUserList = new ArrayList<>();
        // ArrayList<Event> emptyEventList = new ArrayList<>();
        // storeUsers.store(emptyUserList);
        // saveEvents.saveEvents(emptyEventList);
    }

}
