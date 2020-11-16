import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * (please describe)
 *
 * @author Tao,
 * @version %I%, %G%
 */
public class UserOptionsInterface {

    private LoginUI loginUI;
    private EventCreatorPresenter ecp;
    private EventSignupPresenter esp;
    private ChatMenuPresenter cmp;
    private Scanner sc = new Scanner(System.in);
    private LoginOptionsFacade loginFacade;
    private FriendsPresenter fp;
    private EventManager em;
    // All other UIs go here too and in the constructor

    /**
     * Instantiates a UserOptionsInterface object
     *
     * @param loginFacade       (please describe)
     * @param ecp               (please describe)
     * @param esp               (please describe)
     * @param cmp               (please describe)
     * @param fp                (please describe)
     * @param em                (please describe)
     */
    public UserOptionsInterface(LoginOptionsFacade loginFacade, EventCreatorPresenter ecp, EventSignupPresenter esp,
                                ChatMenuPresenter cmp, FriendsPresenter fp, EventManager em){
        this.loginFacade = loginFacade;
        this.ecp = ecp;
        this.esp = esp;
        this.cmp = cmp;
        this.fp = fp;
        this.em = em;
        this.loginUI = new LoginUI(loginFacade);

    }

    /**
     * Displays the available options to the user when logged in
     *
     */
    public void loggedIn() {
        if (loginFacade.getUser() == null) {
            showOptions(loginFacade.getUser());
        }
        while (loginFacade.getUser() == null) {
            System.out.println("Please press any key to return to the login screen, or press Q to save and exit.");
            if (sc.nextLine().equals("Q")) {
                return;
            } else {
                showOptions(loginFacade.getUser());
            }
        }
        homeScreenMenu(loginFacade.getUser(), loginFacade.getRegistrar());
    }

    private void homeScreenMenu(User user, Registrar registrar) {
        System.out.println("\nWelcome " + user.getUserName());
        showOptions(user);
        System.out.println("\nPlease select an option listed above.");
        String choice = sc.nextLine();
        if (user instanceof Organizer) {
            switch (choice) {
                case "1":
                    logout();
                    break;
                case "2":
                    showEventScreen();
                    break;
                case "3":
                    showMessageScreen(registrar, user.getUserName());
                    break;
                case "4":
                    changePassword(user.getUserName());
                    break;
                case "5":
                    showFriends(registrar, user);
                    break;
                case "6":
                    showCreateEventsScreen(registrar);
                    break;
                case "7":
                    showCreateSpeakerScreen();
                    break;
                default:
                    System.out.println("Please input a valid option(1-7).");
                    break;
            }
        } else {
            switch (choice) {
                case "1":
                    logout();
                    break;
                case "2":
                    showEventScreen();
                    break;
                case "3":
                    showMessageScreen(registrar, user.getUserName());
                    break;
                case "4":
                    changePassword(user.getUserName());
                    break;
                case "5":
                    showFriends(registrar, user);
                    break;
                default:
                    System.out.println("Please input a valid option(1-5).");
                    break;
            }
        }
    }

    private void generalOptions(){
        System.out.println("1) Logout");
        System.out.println("2) Events");
        System.out.println("3) Messages");
        System.out.println("4) Change password");
        System.out.println("5) Friends");
    }

    private void showOptions(User user){
        if (user == null){
            login();
        }
        else if(user instanceof Attendee){
            generalOptions();
        }
        else if (user instanceof Organizer){
            generalOptions();
            System.out.println("6) Add Event");
            System.out.println("7) Add Speaker");
        }
        else if (user instanceof Speaker){
            generalOptions();
        }
    }

    private void showEventScreen() {
        esp.viewEvents();
        esp.usersEvents(loginFacade.getUser());
        System.out.println("\nWould you like to join, leave, or get participant information on an event?");
        System.out.println("Type \"join\", \"leave\", or \"info\"");
        System.out.println("Press $q to go back.");
        String choice = sc.nextLine();
        while (!choice.equals("$q")) {
            if (choice.toLowerCase().equals("join")) {
                System.out.println("Please input the event_id");
                String event_id = sc.nextLine();
                esp.joinEvent(loginFacade.getUser(), event_id);
            } else if (choice.toLowerCase().equals("leave")) {
                System.out.println("Please input the event_id");
                String event_id = sc.nextLine();
                esp.leaveEvent(loginFacade.getUser(), event_id);
            } else if (choice.toLowerCase().equals("info")){
                System.out.println("Please input the event_id");
                String event_id = sc.nextLine();
                esp.getEventInfo(event_id);
            }
            else {
                System.out.println("Please enter a valid input (join or leave)");
            }
            System.out.println("\nWould you like to join, leave, or get participant information on an event?");
            System.out.println("Type \"join\", \"leave\", or \"info\"");
            System.out.println("Press $q to go back.");
            choice = sc.nextLine();
        }
    }

    private void showCreateEventsScreen(Registrar registrar) {
        ecp.viewEvents();
        System.out.println("Would you like to create an event? Press any key to continue, or $q to exit");
        String choice = sc.nextLine();
        while (!choice.equals("$q")) {
            try {
                System.out.println("Please input some info on the event you want to create:");
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
                for (User s : registrar.getUsersByType("Speaker")) {
                    System.out.println("name: " + s.getName());
                    System.out.println("username: " + s.getUserName());
                    System.out.println("-----------------------------");
                }
                System.out.println("Username of speaker:");
                String speaker = sc.nextLine();
                User user = registrar.getUserByUserName(speaker);
                if (user instanceof Speaker) {
                    System.out.println(ecp.promptEventCreation(name, room, LocalDateTime.parse(time, formatter), user.getUserName(), capacity));

                } else {
                    System.out.println("Please input a valid Speaker. If you don't have any, please create a speaker account.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Please input the date/time in the following format yyyy-MM-dd HH:mm\n");
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Please input an integer for the event's capacity\n");
            }
            System.out.println("Press any key to continue, or $q to exit");
            choice = sc.nextLine();
        }
    }

    private void showCreateSpeakerScreen() {
        System.out.println("Would you like to create a Speaker? Press any key to continue, or $q to exit");
        String choice = sc.nextLine();
        while (!choice.equals("$q")) {
            System.out.println("Please input the speaker account you wish to create: name, userName, password.");
            System.out.println("name:");
            String name = sc.nextLine();
            System.out.println("username:");
            String userName = sc.nextLine();
            System.out.println("password:");
            String password = sc.nextLine();

            if (loginFacade.createUser(name, userName, password, "speaker")) {
                System.out.println("Speaker account created successfully");
            } else {
                System.out.println("You cannot use those credentials. Please try again.");
            }
            System.out.println("Press any key to continue speaker creation, or $q to exit");
            choice = sc.nextLine();
        }
    }

    private void showFriends(Registrar registrar, User user) {
        FriendsController fc = new FriendsController(registrar,fp);
        fp.viewFriends(user); //shows user a list of all their friends
        System.out.println(fp.addOrRemove());
        String choice = sc.nextLine();
        while (!choice.equals("$q")) {
            if (choice.equals("1")) {
                fc.addFriends(user); // will prompt the user for who they wanna add
            } else if (choice.equals("2")) {
                fc.removeFriends(user);
            }else{
                cmp.invalidCommand("prompt");
            }
            fp.viewFriends(user);
            System.out.println(fp.addOrRemove());
            choice = sc.nextLine();
        }
    }

    private void showMessageScreen(Registrar reg, String username){
        cmp.menuDisplay();
        cmp.commandPrompt("prompt");
        String choice = sc.nextLine();
        while (!choice.equals("$q")) {
            if (choice.equals("1")) {
                showOutbox(reg, username);
            }
            else if (choice.equals("2")) {
                InboxPresenter ip = new InboxPresenter();
                InboxController ic = new InboxController(reg, username, em, ip);
                ic.promptChatChoice();
            } else {
                cmp.invalidCommand("prompt");
            }
            cmp.menuDisplay();
            cmp.commandPrompt("prompt");
            choice = sc.nextLine();
        }
    }

    private void showOutbox(Registrar reg, String username) {
        OutboxPresenter op = new OutboxPresenter();
        OutboxController oc = new OutboxController(reg, username, em, op);
        if (reg.isOrganizer(username)) {
            oc.promptChatChoice();
        } else if (reg.isSpeaker(username)) {
            oc.promptEvent();
        } else if (reg.isAttendee(username)) {
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
    private void changePassword(String username){
        loginUI.changePassword(username);
    }

}
