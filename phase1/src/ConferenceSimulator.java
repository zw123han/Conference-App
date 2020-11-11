import java.util.*;

public class ConferenceSimulator {

    public ConferenceSimulator() {
    }

    public void run(){

        String userFilepath = "phase1/src/userData.ser";
        String eventFilepath = "phase1/src/eventData.ser";

        ReadEvents readEvents = new ReadEvents(userFilepath);
        ReadUsers readUsers = new ReadUsers(eventFilepath);

        SaveEvents saveEvents = new SaveEvents(eventFilepath);
        StoreUsers storeUsers = new StoreUsers(userFilepath);

        Registrar registrar = new Registrar(readUsers.read());
        EventManager eventManager = new EventManager(readEvents.read());


        LoginOptionsFacade loginFacade = new LoginOptionsFacade(registrar);
        // Other controllers with presenters go here

        UserOptionsInterface ui = new UserOptionsInterface(loginFacade);
        // Other UIs go into this ui

        ui.showOptions(loginFacade.getUser());


        // When program finishes running, save everything. How do we make it so that it autosaves upon exiting?
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

    private void showMessageScreen(User user){ // TODO: update once user storage is determined
        ChatMenuPresenter chatMenuPresenter = new ChatMenuPresenter();
        chatMenuPresenter.menuDisplay();
        chatMenuPresenter.commandPrompt("prompt");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        while (!choice.equals("$q")) {
            if (choice.equals("1")) {
                showOutbox(user);
            }
            else if (choice.equals("2")) {
                InboxController ic = new InboxController(user);
                ic.promptChatChoice();
            } else {
                chatMenuPresenter.invalidCommand("prompt");
            }
            chatMenuPresenter.menuDisplay();
            chatMenuPresenter.commandPrompt("prompt");
            choice = sc.nextLine();
        }
    }

    public void showOutbox(User user) {
        OutboxController oc = new OutboxController(user);
        if (user instanceof Organizer) {
            oc.promptChatChoice();
        } else if (user instanceof Speaker) {
            oc.promptEvent();
        } else if (user instanceof Attendee) {
            oc.promptRecipient();
        }
    }

    private void showCreateEventsScreen(){
        //shows screen to create and update events. Only for organizer.

    }

}
