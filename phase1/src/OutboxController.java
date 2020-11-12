
import java.util.*;

public class OutboxController {
    private ChatController cc = new ChatController();
    private OutboxPresenter op = new OutboxPresenter();
    private EventManager em;
    private Scanner sc = new Scanner(System.in);
    private User user;
    private Registrar reg;

    public OutboxController(Registrar reg, User user) {
        this.user = user;
        ReadEvents reader = new ReadEvents("phase1/src/eventData.ser");
        ArrayList<Event> re = new ArrayList<>(reader.read());
        this.em = new EventManager(re);
        this.reg = reg;
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
            } else if (choice.equals("3")) {
                promptSpeaker();
            } else {
                op.invalidCommand("prompt");
            }
            op.menuDisplay();
            op.commandPrompt("prompt");
            choice = sc.nextLine();
        }
    }

    public void promptRecipient() {
        op.friendMenu(reg, user);
        op.commandPrompt("username");
        String recipient = sc.nextLine();
        while (!recipient.equals("$q")) {
            recipient = recipient.replace("@", "");
            if (cc.canMessage(user, recipient, reg)) {
                promptMessage(recipient);
            } else {
                op.invalidCommand("username");
            }
            op.friendMenu(reg, user);
            op.commandPrompt("username");
            recipient = sc.nextLine();

        }
    }

    private boolean canSendSpeakers(ArrayList<String> speakers) {
        boolean result = true;
        for (String speaker : speakers) {
            if (!cc.canMessage(user, speaker, reg)) {
                result = false;
            }
        }
        return result;
    }

    private ArrayList<String> convertSpeakers(String speakers) {
        String[] speakerArray = speakers.split(" ", 0);
        return new ArrayList<>(Arrays.asList(speakerArray));
    }

    public void promptSpeaker() {
        op.speakerMenu(reg, em);
        op.commandPrompt("speaker username (separate usernames with a space)");
        String speakers = sc.nextLine();
        while (!speakers.equals("$q")) {
            speakers = speakers.replace("@", "");
            ArrayList<String> speakerArrayList = convertSpeakers(speakers);
            if (canSendSpeakers(speakerArrayList)) {
                promptMessage(speakerArrayList);
            } else {
                op.invalidCommand("username");
            }
            op.speakerMenu(reg, em);
            op.commandPrompt("speaker username (string after the @, separate usernames with a space)");
            speakers = sc.nextLine();
        }
    }

    private ArrayList<Long> convertLong(String longs) {
        String[] longArray = longs.split(" ", 0);
        ArrayList<Long> longArrayList = new ArrayList<>();
        for (String s : longArray) {
            longArrayList.add(Long.valueOf(s));
        }
        return longArrayList;
    }

    private boolean canSendEvents(ArrayList<Long> event_ids) {
        boolean result = true;
        for (Long event_id : event_ids) {
            if (!cc.canMessage(user, event_id, em)) {
                result = false;
            }
        }
        return result;
    }

    private void loadEventMenu(User user) {
        if (user instanceof Speaker) {
            op.eventMenu((Speaker)user, em);
        } else if (user instanceof Organizer) {
            op.eventMenu(em);
        }
    }

    public void promptEvent() {
        loadEventMenu(user);
        op.commandPrompt("event ID (separate IDs with a space)");
        String evt = sc.nextLine();
        while (!evt.equals("$q")) {
            ArrayList<Long> event_ids = convertLong(evt);
            if (canSendEvents(event_ids)) {
                promptEventMessage(event_ids);
            } else {
                op.invalidCommand("event ID");
            }
            loadEventMenu(user);
            op.commandPrompt("event ID (separate IDs with a space)");
            evt = sc.nextLine();
        }
    }

    public void promptMessage(String destination) {
        op.commandPrompt("message (requires at least 1 character)");
        String message = sc.nextLine();
        while (!message.equals("$q")) {
            if (cc.validateMessage(message)) {
                cc.sendMessage(user, destination, message);
                op.success();
                message = "$q";
            } else {
                op.invalidCommand("message");
                op.commandPrompt("message (requires at least 1 character)");
                message = sc.nextLine();
            }
        }
    }

    public void promptEventMessage(ArrayList<Long> event_ids) {
        op.commandPrompt("message (requires at least 1 character)");
        String message = sc.nextLine();
        while (!message.equals("$q")) {
            if (cc.validateMessage(message)) {
                for (Long event_id : event_ids) {
                    cc.sendMessage(user, event_id, message, em);
                }
                op.success();
                message = "$q";
            } else {
                op.invalidCommand("message");
                op.commandPrompt("message (requires at least 1 character)");
                message = sc.nextLine();
            }
        }
    }

    public void promptMessage(ArrayList<String> speakers) {
        op.commandPrompt("message (requires at least 1 character)");
        String message = sc.nextLine();
        while (!message.equals("$q")) {
            if (cc.validateMessage(message)) {
                for (String speaker : speakers) {
                    cc.sendMessage(user, speaker, message);
                }
                op.success();
                message = "$q";
            } else {
                op.invalidCommand("message");
                op.commandPrompt("message (requires at least 1 character)");
                message = sc.nextLine();
            }
        }
    }
}
