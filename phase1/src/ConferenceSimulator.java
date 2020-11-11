import java.util.*;

public class ConferenceSimulator {

    public ConferenceSimulator() {
    }

    public void run(){

        String userFilepath = "phase1/src/userData.ser";
        String eventFilepath = "phase1/src/eventData.ser";

        ReadEvents readEvents = new ReadEvents(eventFilepath);
        ReadUsers readUsers = new ReadUsers(userFilepath);

        SaveEvents saveEvents = new SaveEvents(eventFilepath);
        StoreUsers storeUsers = new StoreUsers(userFilepath);

        Registrar registrar = new Registrar(readUsers.read());
        EventManager eventManager = new EventManager(readEvents.read());
        EventSignup eventSignup = new EventSignup();

        LoginOptionsFacade loginFacade = new LoginOptionsFacade(registrar);
        EventCreatorPresenter eventCreatorPresenter = new EventCreatorPresenter(eventManager);
        EventSignupPresenter eventSignupPresenter = new EventSignupPresenter(eventSignup, eventManager);
        ChatMenuPresenter chatMenuPresenter = new ChatMenuPresenter();
        // Other controllers with presenters go here

        UserOptionsInterface ui = new UserOptionsInterface(loginFacade, eventCreatorPresenter, eventSignupPresenter,
                chatMenuPresenter);
        // Other UIs go into this ui

        ui.homeScreenMenu(loginFacade.getUser(), registrar);

        // Static organizers from script
        // Only have attendees create their own accounts (assume they are already signed up)
        // Give organizers option to create any accounts other than organizers
        while (loginFacade.getUser() != null) {
            ui.homeScreenMenu(loginFacade.getUser(), registrar);
        }

        // When program finishes running, save everything. How do we make it so that it autosaves upon exiting? -> good question
        storeUsers.store(registrar.getUsers());
        saveEvents.saveEvents(eventManager.getEventsList());


    }




    // Can we move all of this into new classes?
    private void showHomeScreen(){
        //prints required homescreen depending on user.

    }

    private void showEventsScreen(){
        //shows all events User is part of.

    }
}
