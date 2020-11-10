import java.util.*;

public class OutboxController {
    private ChatController cc = new ChatController();
    private OutboxPresenter op = new OutboxPresenter();
    private EventManager em;
    private Scanner sc = new Scanner(System.in);
    private User user;

    public OutboxController(EventManager em, User user) {
        this.user = user;
        this.em = em; // update this when read events is implemented
    }

    public void promptChatChoice() {
        op.menuDisplay();
        op.commandPrompt("prompt");
        String choice = sc.nextLine();
        while (!choice.equals("$back")) {
            if (choice.equals("1")) {
                promptRecipient();
            }
            else if (choice.equals("2")) {
                promptEvent();
            } else {
                op.invalidCommand("command");
                op.menuDisplay();
                choice = sc.nextLine();
            }
        }
    }

    public void promptRecipient() {
        op.commandPrompt("username");
        String recipient = sc.nextLine();
        while (!recipient.equals("$back")) {
            if (cc.canMessage(user, recipient, em)) {
                promptMessage(recipient);
            } else {
                op.invalidCommand("username");
                op.commandPrompt("username");
                recipient = sc.nextLine();
            }
        }
    }

    public void promptEvent() {
        op.commandPrompt("event ID");
        String evt = sc.nextLine();
        while (!evt.equals("$back")) {
            Long event_id = Long.valueOf(evt);
            if (cc.canMessage(user, event_id, em)) {
                promptMessage(event_id);
            } else {
                op.invalidCommand("event ID");
                op.commandPrompt("event ID");
                evt = sc.nextLine();
            }
        }
    }

    public void promptMessage(String destination) {
        op.commandPrompt("message");
        String message = sc.nextLine();
        while (!message.equals("$back")) {
            if (cc.sendMessage(user, destination, message)) {
                System.out.println("Message successfully sent.");
            } else {
                op.invalidCommand("message");
                op.commandPrompt("message");
                message = sc.nextLine();
            }
        }
    }

    public void promptMessage(Long event_id) {
        op.commandPrompt("message");
        String message = sc.nextLine();
        while (!message.equals("$back")) {
            if (cc.sendMessage(user, event_id, message, em)) {
                op.success();
            } else {
                op.invalidCommand("message");
                op.commandPrompt("message");
                message = sc.nextLine();
            }
        }
    }
}
