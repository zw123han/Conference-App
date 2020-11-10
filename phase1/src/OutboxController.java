import com.sun.tools.corba.se.idl.constExpr.Or;

import java.util.*;

public class OutboxController {
    private ChatController cc = new ChatController();
    private OutboxPresenter op = new OutboxPresenter();
    private EventManager em;
    private Scanner sc = new Scanner(System.in);
    private User user;

    public OutboxController(User user) {
        this.user = user;
        ReadEvents reader = new ReadEvents("filepath");
        ArrayList<Event> re = new ArrayList<>(reader.readEvents());
        this.em = new EventManager(re);
    }

    public void promptChatChoice() {
        op.menuDisplay();
        op.commandPrompt("prompt");
        String choice = sc.nextLine();
        while (!choice.equals("$q")) {
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
        op.friendMenu(user);
        op.commandPrompt("username");
        String recipient = sc.nextLine();
        while (!recipient.equals("$q")) {
            if (cc.canMessage(user, recipient, em)) {
                promptMessage(recipient);
                recipient = "$q";
            } else {
                op.invalidCommand("username");
                op.commandPrompt("username");
                recipient = sc.nextLine();
            }
        }
    }

    public void promptEvent() {
        if (user instanceof Speaker) {
            op.eventMenu((Speaker)user, em);
        } else if (user instanceof Organizer) {
            op.eventMenu(em);
        }
        op.commandPrompt("event ID");
        String evt = sc.nextLine();
        while (!evt.equals("$q")) {
            Long event_id = Long.valueOf(evt);
            if (cc.canMessage(user, event_id, em)) {
                promptMessage(event_id);
                evt = "$q";
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
        while (!message.equals("$q")) {
            if (cc.sendMessage(user, destination, message)) {
                System.out.println("Message successfully sent.");
                message = "$q";
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
        while (!message.equals("$q")) {
            if (cc.sendMessage(user, event_id, message, em)) {
                op.success();
                message = "$q";
            } else {
                op.invalidCommand("message");
                op.commandPrompt("message");
                message = sc.nextLine();
            }
        }
    }
}
