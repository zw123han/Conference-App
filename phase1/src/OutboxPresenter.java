import java.util.*;

public class OutboxPresenter {
    private ChatController cc = new ChatController();
    private EventManager em;
    private Scanner sc = new Scanner(System.in);

    public OutboxPresenter(EventManager em) {
        this.cc = cc;
        this.em = em;
    }

    private void isAuthorized(User user, String choice) throws UserNotAuthorizedException {
        if ((user instanceof Speaker && choice.equals("1")) || (user instanceof Attendee && choice.equals("2")) ||
                (!choice.equals("1") && !choice.equals("2"))) {
            throw new UserNotAuthorizedException("This action cannot be performed.");
        }
    }

    public void promptChatChoice(User user) {
        while (true) {
            try {
                System.out.println("1 Direct message\n2 Group message\n$back to exit");
                String choice = sc.nextLine();
                if (choice.equals("$back")) {
                    break;
                }
                isAuthorized(user, choice);
                if (choice.equals("1")) {
                    promptRecipient(user);
                }
                else if (choice.equals("2")) {
                    promptEvent(user);
                }
                break;
            } catch (UserNotAuthorizedException e) {
                System.out.println("Please enter a valid command.");
            }
        }
    }

    public void promptRecipient(User user) {
        while (true) {
            try {
                System.out.println("Enter recipient username\n$back to exit");
                String recipient = sc.nextLine();
                if (recipient.equals("$back")) {
                    break;
                }
                cc.canMessage(user, recipient, em);
                promptMessage(user, recipient, "One");
                break;
            } catch (UserNotFoundException e) {
                System.out.println("Please enter a valid recipient from your list of friends.");
            }
        }
    }

    public void promptEvent(User user) {
        while (true) {
            try {
                System.out.println("Enter event ID\n$back to exit");
                String evt = sc.nextLine();
                Long event_id = Long.valueOf(evt);
                if (evt.equals("$back")) {
                    break;
                }
                cc.canMessage(user, event_id, em);
                promptMessage(user, evt, "All");
                break;
            } catch (EventNotFoundException e) {
                System.out.println("Please enter a valid event ID from your list of events.");
            }
        }
    }

    public void promptMessage(User user, String destination, String type) {
        while (true) {
            try {
                System.out.println("Enter message\n$back to exit");
                String message = sc.nextLine();
                if (message.equals("$back")) {
                    break;
                }
                if (type.equals("One")) {
                    cc.sendMessage(user, destination, message);
                } else if (type.equals("All")) {
                    Long event_id = Long.valueOf(destination);
                    cc.sendMessage(user, event_id, message, em);
                }
                System.out.println("Message sent.");
                break;
            } catch (EmptyMessageException e) {
                System.out.println("Please enter a non-empty message.");
            }
        }
    }
}
