import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserOptionsInterface {

    private LoginUI loginUI;
    private EventCreatorPresenter ecp;
    private EventSignupPresenter esp;
    private Scanner sc = new Scanner(System.in);
    private LoginOptionsFacade loginFacade;
    // All other UIs go here too and in the constructor

    public UserOptionsInterface(LoginOptionsFacade loginFacade){
        this.loginFacade = loginFacade;
        this.loginUI = new LoginUI(loginFacade);

    }
    private void generalOptions(){
        System.out.println("Logout");
        System.out.println("Events");
        System.out.println("Messages");
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
        if (choice == "add") {
            System.out.println("Please input the event_id");
            String event_id = sc.nextLine();
            esp.joinEvent(loginFacade.getUser(), event_id);
        } else if (choice == "leave") {
            System.out.println("Please input the event_id");
            String event_id = sc.nextLine();
            esp.leaveEvent(loginFacade.getUser(), event_id);
        } else {
            System.out.println("Please enter a valid input (add or leave)");
        }
    }
    private void showCreateEventsScreen(Registrar registrar){
        System.out.println("Please input the event you want to create: name, room, capacity, time(yyyy-MM-dd HH:mm:ss), speaker");
        String name = sc.nextLine();
        String room = sc.nextLine();
        int capacity = sc.nextInt();
        String time = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime time1 = LocalDateTime.parse(time, formatter);

        String speaker = sc.nextLine();
        User user = registrar.getUserByUserName(speaker);
        if (user instanceof Speaker) {
            ecp.promptEventCreation(name, room, time1, (Speaker) user, capacity );
        } else {
            System.out.println("Please input a valid Speaker username");
        }
    }
    private void showCreateSpeakerScreen(Registrar registrar) {
        System.out.println("Please input the speaker account you wish to create: name, userName, password");
        String name = sc.nextLine();
        String userName = sc.nextLine();
        String password = sc.nextLine();

        if (registrar.userExisting(userName)) {
            System.out.println("Username already exists, please create another one");
        } else {
            System.out.println("Speaker account created successfully ");
        }

    }
    private void login(){
        loginUI.login();
    }
    private void logout(){
        loginUI.logout();
    }
    private void changePassword(){
        loginUI.changePassword();
    }
}
