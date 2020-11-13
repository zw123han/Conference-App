import java.util.*;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class OutboxController {
    private ChatController cc = new ChatController();
    private OutboxPresenter op = new OutboxPresenter();
    private EventManager em;
    private Scanner sc = new Scanner(System.in);
    private String username;
    private Registrar reg;

    /**
     * (please describe)
     *
     * @param reg       (please describe)
     * @param username      (please describe)
     * @param em (desc)
     */
    public OutboxController(Registrar reg, String username, EventManager em) {
        this.username = username;
        this.reg = reg;
        this.em = em;
    }

    /**
     * (please describe)
     */
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

    /**
     * (please describe)
     */
    public void promptRecipient() {
        op.friendMenu(reg, username);
        op.commandPrompt("username");
        String recipient = sc.nextLine();
        while (!recipient.equals("$q")) {
            recipient = recipient.replace("@", "");
            if (cc.canMessage(username, recipient, reg)) {
                promptMessage(recipient);
            } else {
                op.invalidCommand("username");
            }
            op.friendMenu(reg, username);
            op.commandPrompt("username");
            recipient = sc.nextLine();

        }
    }

    private boolean canSendSpeakers(ArrayList<String> speakers) {
        boolean result = true;
        for (String speaker : speakers) {
            if (!cc.canMessage(username, speaker, reg)) {
                result = false;
            }
        }
        return result;
    }

    private ArrayList<String> convertSpeakers(String speakers) {
        String[] speakerArray = speakers.split(" ", 0);
        return new ArrayList<>(Arrays.asList(speakerArray));
    }

    /**
     * (please describe)
     */
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
            if (!cc.canMessage(username, event_id, reg, em)) {
                result = false;
            }
        }
        return result;
    }

    private void loadEventMenu() {
        if (reg.isSpeaker(username)) {
            op.eventMenu(username, em, reg);
        } else if (reg.isOrganizer(username)) {
            op.eventMenu(em);
        }
    }

    private boolean validateEvents(String evt) {
        return evt.matches("^[0-9]+$");
    }

    /**
     * (please describe)
     */
    public void promptEvent() {
        loadEventMenu();
        op.commandPrompt("event ID (separate IDs with a space)");
        String evt = sc.nextLine();
        while (!evt.equals("$q")) {
            if (validateEvents(evt)) {
                ArrayList<Long> event_ids = convertLong(evt);
                if (canSendEvents(event_ids)) {
                    promptEventMessage(event_ids);
                } else {
                    op.invalidCommand("event ID (make sure they are valid)");
                }
            } else {
                op.invalidCommand("event ID (unexpected character)");
            }
            loadEventMenu();
            op.commandPrompt("event ID (separate IDs with a space)");
            evt = sc.nextLine();
        }
    }

    /**
     * (please describe)
     *
     * @param destination       (please describe)
     */
    public void promptMessage(String destination) {
        op.commandPrompt("message (requires at least 1 character)");
        String message = sc.nextLine();
        while (!message.equals("$q")) {
            if (cc.validateMessage(message)) {
                cc.sendMessage(username, destination, message);
                op.success();
                message = "$q";
            } else {
                op.invalidCommand("message");
                op.commandPrompt("message (requires at least 1 character)");
                message = sc.nextLine();
            }
        }
    }

    /**
     * (please describe)
     *
     * @param event_ids         (please describe)
     */
    public void promptEventMessage(ArrayList<Long> event_ids) {
        op.commandPrompt("message (requires at least 1 character)");
        String message = sc.nextLine();
        while (!message.equals("$q")) {
            if (cc.validateMessage(message)) {
                for (Long event_id : event_ids) {
                    cc.sendMessage(username, event_id, message, em);
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

    /**
     * (please describe)
     *
     * @param speakers      (please describe)
     */
    public void promptMessage(ArrayList<String> speakers) {
        op.commandPrompt("message (requires at least 1 character)");
        String message = sc.nextLine();
        while (!message.equals("$q")) {
            if (cc.validateMessage(message)) {
                for (String speaker : speakers) {
                    cc.sendMessage(username, speaker, message);
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
