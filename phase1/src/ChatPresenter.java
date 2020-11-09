import sun.invoke.empty.Empty;

import java.util.*;

public class ChatPresenter {
    private ChatController cc;
    private EventManager em;
    private Scanner sc = new Scanner(System.in);
    private String recipient;

    public ChatPresenter(ChatController cc, EventManager em) {
        this.cc = cc;
        this.em = em;
    }

    private void checkMessage(String message) throws EmptyMessageException {
        if (message.length() == 0) {
            throw new EmptyMessageException("Message cannot be empty.");
        }
    }

    public void promptRecipient(User user) {
        while (true) {
            try {
                System.out.println("Send to username ($back to exit):");
                String recipient = sc.nextLine();
                if (recipient.equals("$back")) {
                    break;
                }
                cc.canMessage(user, recipient, em);
                promptMessage(user, recipient, "One");
                break;
            } catch (UserNotFoundException e) {
                System.out.println("Enter a valid recipient from your list of friends.");
            }
        }
    }

    public void promptEvent(User user) {
        while (true) {
            try {
                System.out.println("Send to event ID ($back to exit):");
                String evt = sc.nextLine();
                Long event_id = Long.valueOf(evt);
                if (recipient.equals("$back")) {
                    break;
                }
                cc.canMessage(user, event_id, em);
                promptMessage(user, evt, "All");
                break;
            } catch (EventNotFoundException e) {
                System.out.println("Enter a valid event ID from your list of events.");
            }
        }
    }

    public void promptMessage(User user, String destination, String type) {
        while (true) {
            try {
                System.out.println("Message ($back to exit):");
                String msg = sc.nextLine();
                if (recipient.equals("$back")) {
                    break;
                }
                checkMessage(msg);
                Message message = new Message(msg, user.getUserName());
                if (type.equals("One")) {
                    cc.sendMessage(user, destination, message, em);
                } else if (type.equals("All")) {
                    Long event_id = Long.valueOf(destination);
                    cc.sendMessage(user, event_id, message, em);
                }
                System.out.println("Message sent!");
                break;
            } catch (EmptyMessageException e) {
                System.out.println("Enter a valid message.");
            }
        }
    }

}
