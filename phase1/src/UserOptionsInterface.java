import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * (please describe)
 *
 * @author
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
    // All other UIs go here too and in the constructor

    /**
     * (please describe)
     *
     * @param loginFacade       (please describe)
     * @param ecp               (please describe)
     * @param esp               (please describe)
     * @param cmp               (please describe)
     * @param fp                (please describe)
     */
    public UserOptionsInterface(LoginOptionsFacade loginFacade, EventCreatorPresenter ecp, EventSignupPresenter esp,
                                ChatMenuPresenter cmp, FriendsPresenter fp){
        this.loginFacade = loginFacade;
        this.ecp = ecp;
        this.esp = esp;
        this.cmp = cmp;
        this.fp = fp;
        this.loginUI = new LoginUI(loginFacade);

    }

    /**
     * (please describe)
     *
     * @param user          (please describe)
     * @param registrar     (please describe)
     */
    public void loggedIn(User user, Registrar registrar) {
        if (loginFacade.getUser() == null) {
            showOptions(user);
        }
        while (loginFacade.getUser() == null) {
            System.out.println("Please hit enter to try again. If you no longer wish to continue, enter Q to exit the program");
            if (sc.nextLine().equals("Q")) {
                return;
            } else {
                showOptions(user);
            }
        }
        homeScreenMenu(loginFacade.getUser(), registrar);
    }

    /**
     * (please describe)
     *
     * @param user          (please describe)
     * @param registrar     (please describe)
     */
    public void homeScreenMenu(User user, Registrar registrar) {
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
                    showEventScreen(esp);
                    break;
                case "3":
                    showMessageScreen(registrar, user);
                    break;
                case "4":
                    changePassword();
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
                    showEventScreen(esp);
                    break;
                case "3":
                    showMessageScreen(registrar, user);
                    break;
                case "4":
                    changePassword();
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

    /**
     * (please describe)
     *
     * @param user      (please describe)
     */
    public void showOptions(User user){
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

    /**
     * (please describe)
     *
     * @param esp       (please describe)
     */
    public void showEventScreen(EventSignupPresenter esp) {
        esp.viewEvents();
        esp.usersEvents(loginFacade.getUser());
        System.out.println("\nWould you like to join or leave an event?");
        System.out.println("Type \"join\" or \"leave\"");
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
            } else {
                System.out.println("Please enter a valid input (join or leave)");
            }
            System.out.println("\nWould you like to join or leave an event?");
            System.out.println("Type \"join\" or \"leave\"");
            System.out.println("Press $q to go back.");
            choice = sc.nextLine();
        }
    }

    /**
     * (please describe)
     *
     * @param registrar     (please describe)
     */
    public void showCreateEventsScreen(Registrar registrar) { //TODO this ugly af, will need to change a bit
        System.out.println("Would you like to create an event? Press any key to continue, or $q to exit");
        String choice = sc.nextLine();
        while (!choice.equals("$q")) {
            try {
                System.out.println("Please input the event you want to create: name, room, capacity, time(yyyy-MM-dd HH:mm), speaker");
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
                }
                System.out.println("Username of speaker:");
                String speaker = sc.nextLine();
                User user = registrar.getUserByUserName(speaker);
                if (user instanceof Speaker) {
                    ecp.promptEventCreation(name, room, LocalDateTime.parse(time, formatter), user.getUserName(), capacity);
                } else {
                    System.out.println("Please input a valid Speaker. If you don't have any, please create a speaker account.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Please input the date/time in the following format yyyy-MM-dd HH:mm\n");
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Please input an integer for the event's capacity\n");
            }
            System.out.println("Please input the event you want to create: name, room, capacity, time(yyyy-MM-dd HH:mm), speaker");
            choice = sc.nextLine();
        }
    }

    /**
     * (please describe)
     */
    public void showCreateSpeakerScreen() {
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
            System.out.println("Would you like to create a Speaker? Press any key to continue, or $q to exit");
            choice = sc.nextLine();
        }
    }

    /**
     * (please describe)
     *
     * @param registrar     (please describe)
     * @param user          (please describe)
     */
    public void showFriends(Registrar registrar, User user) {
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

    /**
     * (please describe)
     *
     * @param reg       (please describe)
     * @param user      (please describe)
     */
    public void showMessageScreen(Registrar reg, User user){
        cmp.menuDisplay();
        cmp.commandPrompt("prompt");
        String choice = sc.nextLine();
        while (!choice.equals("$q")) {
            if (choice.equals("1")) {
                showOutbox(reg, user);
            }
            else if (choice.equals("2")) {
                InboxController ic = new InboxController(reg, user);
                ic.promptChatChoice();
            } else {
                cmp.invalidCommand("prompt");
            }
            cmp.menuDisplay();
            cmp.commandPrompt("prompt");
            choice = sc.nextLine();
        }
    }

    /**
     * (please describe)
     *
     * @param reg       (please describe)
     * @param user      (please describe)
     */
    public void showOutbox(Registrar reg, User user) {
        OutboxController oc = new OutboxController(reg, user);
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
