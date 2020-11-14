import java.util.*;

/**
 * OutboxController handles user requests for sending messages.
 *
 * @author  ????
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
     * initializes a new OutboxController.
     *
     * @param reg       the registrar
     * @param username  the username of the logged in user
     * @param em        the event manager
     */
    public OutboxController(Registrar reg, String username, EventManager em) {
        this.username = username;
        this.reg = reg;
        this.em = em;
    }

    /**
     * Prompts and processes a user's request for message composition options by target.
     */
    public void promptChatChoice() {
        op.menuDisplay();
        op.commandPrompt("prompt");
        String choice = sc.nextLine();
        while (!choice.equals("$q")) {
            switch (choice) {
                case "1":
                    promptRecipient();
                    break;
                case "2":
                    promptEvent();
                    break;
                case "3":
                    promptSpeaker();
                    break;
                default:
                    op.invalidCommand("prompt");
                    break;
            }
            op.menuDisplay();
            op.commandPrompt("prompt");
            choice = sc.nextLine();
        }
    }

    /**
     * Prompts and processes a user's request to message a specific friend by username.
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
     * Prompts and processes a user's request to message one or more speakers by username.
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
     * Prompts and processes a user's request to message all attendees of a specific event by ID.
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
     * Prompts and processes a user's request to send a message to a user.
     *
     * @param destination     the username of the target user
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
     * Prompts and processes a user's request to send a message to all attendees of an event.
     *
     * @param event_ids        the ID of the event
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
     * Prompts and processes a user's request to send a message to one or more speakers.
     *
     * @param speakers      the username(s) of the speakers
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
