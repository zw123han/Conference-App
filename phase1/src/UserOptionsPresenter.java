import java.util.ArrayList;

public class UserOptionsPresenter {

    private ArrayList<String> getAttendeeOptions(){

    }
    private ArrayList<String> getOrganizerOptions(){

    }
    private ArrayList<String> getSpeakerOptions(){

    }

    public void displayOptions(LoginOptionsFacade facade) {
        try {
            User user = facade.getUser();
            if (user instanceof Attendee) {
                for (String option : getAttendeeOptions()) {
                    System.out.println(option);
                }
            } else if (user instanceof Organizer) {
                for (String option : getOrganizerOptions()) {
                    System.out.println(option);
                }
            } else if (user instanceof Speaker) {
                for (String option : getSpeakerOptions()) {
                    System.out.println(option);
                }
            }
        } catch (EmptyUserException e) {
            System.out.println("You are not logged in. Please log in");
        }
    }

    public void executeOptions(){

    }


}
