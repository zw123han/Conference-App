import java.util.*;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class ConferenceSimulator {

    public ConferenceSimulator() {
    }

    public void run() {

        String userFilepath = "phase1/src/userData.ser";
        String eventFilepath = "phase1/src/eventData.ser";
        // Should we also have chatlog filepath?

        ReadEvents readEvents = new ReadEvents(eventFilepath);
        ReadUsers readUsers = new ReadUsers(userFilepath);

        SaveEvents saveEvents = new SaveEvents(eventFilepath);
        StoreUsers storeUsers = new StoreUsers(userFilepath);

        Registrar registrar = new Registrar(readUsers.read());
        EventManager eventManager = new EventManager(readEvents.read());
        EventSignup eventSignup = new EventSignup();

        // Make admin accounts
        OrganizerCreationScript organizerCreationScript = new OrganizerCreationScript();
        organizerCreationScript.createOrganizers(registrar);

        LoginOptionsFacade loginFacade = new LoginOptionsFacade(registrar);
        EventCreatorPresenter eventCreatorPresenter = new EventCreatorPresenter(eventManager, registrar);
        EventSignupPresenter eventSignupPresenter = new EventSignupPresenter(eventSignup, eventManager);
        ChatMenuPresenter chatMenuPresenter = new ChatMenuPresenter();
        FriendsPresenter friendsPresenter = new FriendsPresenter();
        // Other controllers with presenters go here

        UserOptionsInterface ui = new UserOptionsInterface(loginFacade, eventCreatorPresenter, eventSignupPresenter,
                chatMenuPresenter, friendsPresenter);
        // Other UIs go into this ui

        // Only have attendees create their own accounts (assume they are already signed up)
        // Give organizers option to create any accounts other than organize    rs

        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        do{
            do {
                ui.loggedIn(loginFacade.getUser(), registrar);
                storeUsers.store(registrar.getUsers());
                saveEvents.saveEvents(eventManager.getEventsList());
            } while (loginFacade.getUser() != null);

            System.out.println("Are you sure you want to close the program? Press Q to close.");
            exit = sc.nextLine().equals("Q");

        } while (!exit);



        // Reset user and event data
        // ArrayList<User> emptyUserList = new ArrayList<>();
        // ArrayList<Event> emptyEventList = new ArrayList<>();
        // storeUsers.store(emptyUserList);
        // saveEvents.saveEvents(emptyEventList);
    }
}
