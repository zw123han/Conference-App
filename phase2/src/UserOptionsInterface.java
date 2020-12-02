import EventSystem.EventCreatorPresenter;
import EventSystem.EventManager;
import EventSystem.EventSignupPresenter;
import LoginSystem.LoginOptionsFacade;
import LoginSystem.LoginUI;
import MessagingSystem.*;
import UserSystem.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * UserOptionsInterface is the main controller class that handles the home menu controls for a single user's session.
 *
 * @author Tao
 */
public class UserOptionsInterface {

    private LoginUI loginUI;
    private EventCreatorPresenter ecp;
    private EventSignupPresenter esp;
    private ChatMenuPresenter cmp;
    private Scanner sc = new Scanner(System.in);
    private LoginOptionsFacade loginFacade;
    private FriendsPresenter fp;
    private MessageOutboxUI mo;
    private MessageInboxUI mi;
    private EventManager em;
    // All other UIs go here too and in the constructor

    /**
     * Instantiates a UserOptionsInterface object
     *
     * @param loginFacade       the LoginSystem.LoginOptionsFacade instance for the current session
     * @param ecp               the EventCreatorPresenter instance for the current session
     * @param esp               the EventSignupPresenter instance for the current session
     * @param cmp               the ChatMenuPresenter instance for the current session
     * @param fp                the FriendsPresenter instance for the current session
     */
    public UserOptionsInterface(LoginOptionsFacade loginFacade, EventCreatorPresenter ecp, EventSignupPresenter esp,
                                ChatMenuPresenter cmp, FriendsPresenter fp, MessageOutboxUI mo, MessageInboxUI mi,
                                EventManager em){
        this.loginFacade = loginFacade;
        this.ecp = ecp;
        this.esp = esp;
        this.cmp = cmp;
        this.fp = fp;
        this.mo = mo;
        this.loginUI = new LoginUI(loginFacade);
        this.mi = mi;
        this.em = em;
    }

    /**
     * Displays the available options to the user when logged in. If the user is unsuccessful in logging in, they may
     * try again or quit the program.
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
        mo.setLoggedInUser(user.getUserName());
        mi.setLoggedInUser(user.getUserName());
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
                    showManageAccountsScreen(registrar);
                    break;
                default:
                    System.out.println("Please input a valid option(1-7).");
                    break;
            }
        } else if (user instanceof Administrator) {
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
                    showManageEventsScreen(registrar);
                    break;
                case "7":
                    showManageAccountsScreen(registrar);
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
            System.out.println("7) Manage Accounts");
        }
        else if (user instanceof Administrator){
            generalOptions();
            System.out.println("6) Manage Events");
            System.out.println("7) Manage Accounts");
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

    private void printEventScreen(){
        esp.viewEvents();
        esp.usersEvents(loginFacade.getUser());
        System.out.println("Would you like download your events list? Press p to print. Or press any other key to exit");
        String choice = sc.nextLine();
        if(choice.equals("p") || choice.equals("P")){
            //printScreen call will go here.
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
                System.out.println("duration of event in minutes: ");
                long duration = Integer.parseInt(sc.nextLine());

                System.out.println("list of speakers:");
                for (User s : registrar.getUsersByType("Speaker")) {
                    System.out.println("name: " + s.getName());
                    System.out.println("username: " + s.getUserName());
                    System.out.println("-----------------------------");
                }
                System.out.println("Usernames of speakers (separated by comma and space, hit enter if no speaker): ");
                String speakers = sc.nextLine();
                ArrayList<String> speakersNameList = new ArrayList<>();
                boolean allSpeakersValid = true;
                for(String s: speakers.split(", ")){
                    User user = registrar.getUserByUserName(s);
                    if (user instanceof Speaker) {
                        speakersNameList.add(s);
                    }
                    else{
                        allSpeakersValid = false;
                    }
                }
                if(allSpeakersValid){
                    System.out.println(ecp.promptEventCreation(name, room, LocalDateTime.parse(time, formatter), duration, speakersNameList, capacity));
                }
                else {
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

    private void showManageEventsScreen(Registrar registrar) {
        ecp.viewEvents();
        System.out.println("Would you like to create, modify or delete an event? Press c to create, m to modify or " +
                "d to delete, or press $q to exit");
        String choice = sc.nextLine();
        if(choice.equals("c")){
            while (choice.equals("c")) {
                try{
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
                    System.out.println("Duration of event in minutes:");
                    long duration = Integer.parseInt(sc.nextLine());

                    System.out.println("list of speakers:");
                    for (User s : registrar.getUsersByType("Speaker")) {
                        System.out.println("name: " + s.getName());
                        System.out.println("username: " + s.getUserName());
                        System.out.println("-----------------------------");
                    }
                    System.out.println("Usernames of speakers (separated by comma and space, hit enter if no speaker): ");
                    String speakers = sc.nextLine();
                    ArrayList<String> speakersNameList = new ArrayList<>();
                    boolean allSpeakersValid = true;
                    for(String s: speakers.split(", ")){
                        User user = registrar.getUserByUserName(s);
                        if (user instanceof Speaker) {
                            speakersNameList.add(s);
                        }
                        else{
                            allSpeakersValid = false;
                        }
                    }
                    if(allSpeakersValid){
                        System.out.println(ecp.promptEventCreation(name, room, LocalDateTime.parse(time, formatter), duration, speakersNameList, capacity));
                    }
                    else {
                        System.out.println("Please input a valid Speaker. If you don't have any, please create a speaker account.");
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Please input the date/time in the following format yyyy-MM-dd HH:mm\n");
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Please input an integer for the event's capacity\n");
                }
                System.out.println("Press c to continue creating events or any key to exit.");
                choice = sc.nextLine();
            }
        } else if(choice.equals("m")){

        } else if(choice.equals("d")){
            this.ecp.viewEvents();
            System.out.println("Enter the Id of the event you would like to delete:");
            Long eventId = Long.parseLong(sc.nextLine());
            System.out.println(eventId);

            try {
                if (this.em.getEvent(eventId).isEmpty()) {
                    System.out.println(this.ecp.promptEventDeletion(eventId));
                } else {
                    System.out.println("This event still has registered users. Are you sure that you want to delete " +
                            "it? Press 'y' to continue or any other key to quit.");
                    choice = sc.nextLine();

                    if (choice.equals("y") || choice.equals("Y")) {
                        System.out.println(this.ecp.promptEventDeletion(eventId));
                    }

                }
            } catch(NullPointerException npe){
                System.out.println("This event ID is not valid. Please try again.");
            }
        }

    }

    private void showManageAccountsScreen(Registrar registrar) {
        System.out.println("Press 'c' to create an account. Press any other key to quit.");
        String choice = sc.nextLine();
        if(choice.equals("c") || choice.equals("C")){
            while(choice.equals("c") || choice.equals("C")) {
                createAccount();
                System.out.println("Do you want to create another account? Press 'c' to continue. Press any other to exit");
                choice = sc.nextLine();
            }
        }
    }

    private void createAccount(){
        System.out.println("Please specify the type of account: (s)peaker, (o)rganizer or (a)ttendee:");
        String type = sc.nextLine();

        if(type.equals("s") || type.equals("S")){
            System.out.println("Please input the name of the user:");
            String name = sc.nextLine();
            System.out.println("Please input the username:");
            String userName = sc.nextLine();
            System.out.println("Please input the password:");
            String password = sc.nextLine();
            if (loginFacade.createUser(name, userName, password, "speaker")) {

                System.out.println("Speaker account created successfully");
            } else {
                System.out.println("You cannot use those credentials. Please try again.");
            }
        } else if(type.equals("o") || type.equals("O")){
            System.out.println("Please input the name of the user:");
            String name = sc.nextLine();
            System.out.println("Please input the username:");
            String userName = sc.nextLine();
            System.out.println("Please input the password:");
            String password = sc.nextLine();
            if (loginFacade.createUser(name, userName, password, "organizer")){
                System.out.println("Organizer account created successfully");
            } else {
                System.out.println("You cannot use those credentials. Please try again.");
            }
        } else if(type.equals("a") || type.equals("A")){
            System.out.println("Please input the name of the user:");
            String name = sc.nextLine();
            System.out.println("Please input the username:");
            String userName = sc.nextLine();
            System.out.println("Please input the password:");
            String password = sc.nextLine();
            if (loginFacade.createUser(name, userName, password, "attendee")) {
                System.out.println("Attendee account created successfully");
            } else {
                System.out.println("You cannot use those credentials. Please try again.");
            }
        } else {
            System.out.println("Invalid option. Please try again.");
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
        System.out.println(cmp.menuDisplay());
        System.out.println(cmp.commandPrompt("prompt"));
        String choice = sc.nextLine();
        while (!choice.equals("$q")) {
            if (choice.equals("1")) {
                showOutbox(reg, username, mo);
            }
            else if (choice.equals("2")) {
                mi.promptChatChoice();
            } else {
                System.out.println(cmp.invalidCommand("prompt"));
            }
            System.out.println(cmp.menuDisplay());
            System.out.println(cmp.commandPrompt("prompt"));
            choice = sc.nextLine();
        }
    }

    private void showOutbox(Registrar reg, String username, MessageOutboxUI mo) {
        if (reg.isOrganizer(username) || reg.isAdmin(username)) {
            mo.promptChatChoice();
        } else if (reg.isSpeaker(username)) {
            mo.promptEvent();
        } else if (reg.isAttendee(username)) {
            mo.promptRecipient();
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
