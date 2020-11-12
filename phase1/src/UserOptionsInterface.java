import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserOptionsInterface {

    private LoginUI loginUI;
    private EventCreatorPresenter ecp;
    private EventSignupPresenter esp;
    ChatMenuPresenter cmp;
    private Scanner sc = new Scanner(System.in);
    private LoginOptionsFacade loginFacade;
    // All other UIs go here too and in the constructor

    public UserOptionsInterface(LoginOptionsFacade loginFacade, EventCreatorPresenter ecp, EventSignupPresenter esp,
                                ChatMenuPresenter cmp){
        this.loginFacade = loginFacade;
        this.ecp = ecp;
        this.esp = esp;
        this.cmp = cmp;
        this.loginUI = new LoginUI(loginFacade);

    }

    public void homeScreenMenu(User user, Registrar registrar) {
        boolean retry = true;
        if (loginFacade.getUser() == null) {
            showOptions(user);
        }
        while (loginFacade.getUser() == null && retry != false) {
            System.out.println("Please hit enter to try again. If you no longer wish to continue, enter Q to exit the program");
            if (sc.nextLine().equals("Q")) {
                retry = false;
                return;
            } else {
                showOptions(user);
            }
        }
        showOptions(loginFacade.getUser());
        System.out.println("\nPlease select an option listed above.");
        String choice = sc.nextLine();
        if (user instanceof Organizer) { //TODO need to implement a "go back" option
            switch (choice) {
                case "Logout":
                    logout();
                    break;
                case "Change password":
                    changePassword();
                    break;
                case "Events":
                    showEventScreen(esp);
                    break;
                case "Messages":
                    showMessageScreen(); // don't think this is sufficient, need to revise later
                    break;
                case "Add Event":
                    showCreateEventsScreen(registrar);
                    break;
                case "Add Speaker":
                    showCreateSpeakerScreen();
                    break;
                default:
                    System.out.println("Please input a valid option.");
                    break;
            }
        } else {
            switch (choice) {
                case "Logout":
                    logout();
                    break;
                case "Change password":
                    changePassword();
                    break;
                case "Events":
                    showEventScreen(esp);
                    break;
                case "Messages":
                    showMessageScreen();
                    break;
                case "Add Event":
                    showCreateEventsScreen(registrar);
                    break;
                default:
                    System.out.println("Please input a valid option.");
                    break;
            }
        }
    }
    private void generalOptions(){
        System.out.println("Logout");
        System.out.println("Events");
        System.out.println("Messages");
        System.out.println("Change password");
    }
    public void showOptions(User user){
        if (user == null){
            login();
        }
        else if(user instanceof Attendee){
            generalOptions();
        }
        else if (user instanceof Organizer){
            generalOptions();
            System.out.println("Add Event");
            System.out.println("Add Speaker");
        }
        else if (user instanceof Speaker){
            generalOptions();
        }
    }
    public void showEventScreen(EventSignupPresenter esp) {
        esp.viewEvents();
        esp.usersEvents(loginFacade.getUser());
        System.out.println("\nWould you like to add or leave an event?");
        String choice = sc.nextLine();
        if (choice.equals("add")) {
            System.out.println("Please input the event_id");
            String event_id = sc.nextLine();
            esp.joinEvent(loginFacade.getUser(), event_id);
        } else if (choice.equals("leave")) {
            System.out.println("Please input the event_id");
            String event_id = sc.nextLine();
            esp.leaveEvent(loginFacade.getUser(), event_id);
        } else {
            System.out.println("Please enter a valid input (add or leave)");
        }
    }
    public void showCreateEventsScreen(Registrar registrar){
        System.out.println("Please input the event you want to create: name, room, capacity, time(yyyy-MM-dd HH:mm:ss), speaker");
        System.out.println("name:");
        String name = sc.nextLine();
        System.out.println("room:");
        String room = sc.nextLine();
        System.out.println("capacity:");
        int capacity = Integer.parseInt(sc.nextLine());
        System.out.println("date format(yyyy-MM-dd HH:mm):");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String time = sc.nextLine();
        System.out.println("list of speakers:");
        for(User s: registrar.getUsersByType("Speaker")){
            System.out.println("name: " + s.getName());
            System.out.println("username: "+ s.getUserName());
        }
        System.out.println("Username of speaker:");
        String speaker = sc.nextLine();
        User user = registrar.getUserByUserName(speaker);
        if (user instanceof Speaker) {
            ecp.promptEventCreation(name, room, LocalDateTime.parse(time, formatter), (Speaker) user, capacity );
        } else {
            System.out.println("Please input a valid Speaker. If you dont have any, please create a speaker account.");
        }
    }
    public void showCreateSpeakerScreen() {
        System.out.println("Please input the speaker account you wish to create: name, userName, password");
        String name = sc.nextLine();
        String userName = sc.nextLine();
        String password = sc.nextLine();

        if (loginFacade.userExists(userName)) {
            System.out.println("Username already exists, please create another one");
        } else {
            loginFacade.createUser(name, userName, password, "speaker");
            System.out.println("Speaker account created successfully ");
        }

    }
    public void showMessageScreen(){
        User user = loginFacade.getUser();
        cmp.menuDisplay();
        cmp.commandPrompt("prompt");
        String choice = sc.nextLine();
        while (!choice.equals("$q")) {
            if (choice.equals("1")) {
                showOutbox(user);
            }
            else if (choice.equals("2")) {
                InboxController ic = new InboxController(user);
                ic.promptChatChoice();
            } else {
                cmp.invalidCommand("prompt");
            }
            cmp.menuDisplay();
            cmp.commandPrompt("prompt");
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

    private void login(){
        loginUI.login();
        //this.homeScreenMenu(loginFacade.getUser(), registrar);
    }
    private void logout(){
        loginUI.logout();
    }
    private void changePassword(){
        loginUI.changePassword();
    }

}
