import java.util.*;

public class OutboxPresenter {
    private ChatController cc = new ChatController();
    private EventManager em;
    private Scanner sc = new Scanner(System.in);
    private User user;

    public OutboxPresenter(EventManager em, User user) {
        this.user = user;
        this.em = em;
    }

    public void promptChatChoice() {
        System.out.println("1 Direct message\n2 Group message\n$back to exit");
        String choice = sc.nextLine();
        while (!choice.equals("$back")) {
            if (choice.equals("1")) {
                promptRecipient();
            }
            else if (choice.equals("2")) {
                promptEvent();
            } else {
                System.out.println("Command not found.");
                System.out.println("1 Direct message\n2 Group message\n$back to exit");
                choice = sc.nextLine();
            }
        }
    }

    public void promptRecipient() {
        System.out.println("Enter recipient username\n$back to exit");
        String recipient = sc.nextLine();
        while (!recipient.equals("$back")) {
            if (cc.canMessage(user, recipient, em)) {
                promptMessage(recipient);
                break;
            } else {
                System.out.println("Please enter a valid recipient from your list of friends.");
                System.out.println("Enter recipient username\n$back to exit");
                recipient = sc.nextLine();
            }
        }
    }

    public void promptEvent() {
        System.out.println("Enter event ID\n$back to exit");
        String evt = sc.nextLine();
        while (!evt.equals("$back")) {
            Long event_id = Long.valueOf(evt);
            if (cc.canMessage(user, event_id, em)) {
                promptMessage(event_id);
                break;
            } else {
                System.out.println("Please enter a valid event ID from your list of events.");
                System.out.println("Enter event ID\n$back to exit");
                evt = sc.nextLine();
            }
        }
    }

    public void promptMessage(String destination) {
        System.out.println("Enter message\n$back to exit");
        String message = sc.nextLine();
        while (!message.equals("$back")) {
            if (cc.sendMessage(user, destination, message)) {
                System.out.println("Message successfully sent.");
                break;
            } else {
                System.out.println("Invalid message.");
                System.out.println("Enter message\n$back to exit");
                message = sc.nextLine();
            }
        }
    }

    public void promptMessage(Long event_id) {
        System.out.println("Enter message\n$back to exit");
        String message = sc.nextLine();
        while (!message.equals("$back")) {
            if (cc.sendMessage(user, event_id, message, em)) {
                System.out.println("Message successfully sent.");
                message = "$back";
            } else {
                System.out.println("Invalid message.");
                System.out.println("Enter message\n$back to exit");
                message = sc.nextLine();
            }
        }
    }
}
