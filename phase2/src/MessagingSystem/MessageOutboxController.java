package MessagingSystem;

import UserSystem.*;
import EventSystem.*;
import java.util.*;

/**
 * OutboxController handles user requests for sending messages.
 *
 * @author  Chrisee Zhu
 */
public class MessageOutboxController {
    private ChatController cc;
    private MessageOutboxPresenter op;
    private EventManager em;
    private Scanner sc = new Scanner(System.in);
    private String username;
    private Registrar reg;

    /**
     * initializes a new OutboxController.
     *
     * @param reg       Registrar
     * @param username  the username of the sender
     * @param em        EventManager
     * @param op        OutboxPresenter
     */
    public MessageOutboxController(Registrar reg, String username, EventManager em, MessageOutboxPresenter op) {
        this.username = username;
        this.reg = reg;
        this.em = em;
        this.op = op;
        this.cc = new ChatController(username, reg, em);
    }

    /**
     * Prompts and processes a user's request for message composition options by target.
     */
    public void promptChatChoice() {
        System.out.println(op.menuDisplay());
        System.out.println(op.commandPrompt("prompt"));
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
            System.out.println(op.menuDisplay());
            System.out.println(op.commandPrompt("prompt"));
            choice = sc.nextLine();
        }
    }

    /**
     * Prompts and processes a user's request to message a specific friend by username.
     */
    public void promptRecipient() {
        System.out.println(op.friendMenu(reg, username));
        System.out.println(op.commandPrompt("username"));
        String recipient = sc.nextLine();
        while (!recipient.equals("$q")) {
            recipient = recipient.replace("@", "");
            if (cc.canMessage(recipient)) {
                promptMessage(recipient);
            } else {
                System.out.println(op.invalidCommand("username"));
            }
            System.out.println(op.friendMenu(reg, username));
            System.out.println(op.commandPrompt("username"));
            recipient = sc.nextLine();

        }
    }

    private ArrayList<String> convertSpeakers(String speakers) {
        String[] speakerArray = speakers.split("[ ]+", 0);
        return new ArrayList<>(Arrays.asList(speakerArray));
    }

    /**
     * Prompts and processes a user's request to message one or more speakers by username.
     */
    public void promptSpeaker() {
        System.out.println(op.speakerMenu(reg, em));
        System.out.println(op.commandPrompt("speaker username (separate usernames with a space, enter * to send all)"));
        String speakers = sc.nextLine();
        while (!speakers.equals("$q")) {
            speakers = speakers.replace("@", "");
            ArrayList<String> speakerArrayList = convertSpeakers(speakers);
            if (speakers.equals("*")) {
                ArrayList<String> s = new ArrayList<>();
                for (Long event_id : em.getEventIDs()) {
                    if (!s.contains(em.getSpeaker(event_id)))
                        s.add(em.getSpeaker(event_id));
                }
                if (cc.canSendSpeakers(s)) {
                    promptMessage(s);
                }
            } else if (cc.canSendSpeakers(speakerArrayList)) {
                promptMessage(speakerArrayList);
            } else {
                System.out.println(op.invalidCommand("username"));
            }
            System.out.println(op.speakerMenu(reg, em));
            System.out.println(op.commandPrompt("speaker username (string after the @, separate usernames with a space)"));
            speakers = sc.nextLine();
        }
    }

    private ArrayList<Long> convertLong(String longs) {
        String[] longArray = longs.split("[ ]+", 0);
        ArrayList<Long> longArrayList = new ArrayList<>();
        for (String s : longArray) {
            longArrayList.add(Long.valueOf(s));
        }
        return longArrayList;
    }

    private void loadEventMenu() {
        if (reg.isSpeaker(username)) {
            System.out.println(op.eventMenu(username, em, reg));
        } else if (reg.isAdmin(username) || reg.isOrganizer(username)) {
            System.out.println(op.eventMenu(em));
        }
    }

    /**
     * Prompts and processes a user's request to message all attendees of a specific event by ID.
     */
    public void promptEvent() {
        loadEventMenu();
        System.out.println(op.commandPrompt("event ID (separate IDs with a space, enter * to send all)"));
        String evt = sc.nextLine();
        while (!evt.equals("$q")) {
            if (evt.equals("*")) {
                ArrayList<Long> event_ids = em.getEventIDs();
                if (cc.canSendEvents(event_ids)) {
                    promptEventMessage(event_ids);
                }
            } else if (evt.replace(" ", "").matches("^[0-9]+$")) {
                ArrayList<Long> event_ids = convertLong(evt);
                if (cc.canSendEvents(event_ids)) {
                    promptEventMessage(event_ids);
                } else {
                    System.out.println(op.invalidCommand("event ID (make sure they are valid)"));
                }
            } else {
                System.out.println(op.invalidCommand("event ID (unexpected character)"));
            }
            loadEventMenu();
            System.out.println(op.commandPrompt("event ID (separate IDs with a space)"));
            evt = sc.nextLine();
        }
    }

    /**
     * Prompts and processes a user's request to send a message to a user.
     *
     * @param destination     the username of the target user; target user must be a friend of the sender
     */
    public void promptMessage(String destination) {
        System.out.println(op.commandPrompt("message (requires at least 1 character)"));
        String message = sc.nextLine();
        while (!message.equals("$q")) {
            if (cc.validateMessage(message)) {
                cc.sendMessage(destination, message);
                System.out.println(op.success(destination));
                message = "$q";
            } else {
                System.out.println(op.invalidCommand("message"));
                System.out.println(op.commandPrompt("message (requires at least 1 character)"));
                message = sc.nextLine();
            }
        }
    }

    /**
     * Prompts and processes a user's request to send a message to all attendees of an event.
     *
     * @param event_ids        the ID of the event; events must exist in EventManager
     */
    public void promptEventMessage(ArrayList<Long> event_ids) {
        System.out.println(op.commandPrompt("message (requires at least 1 character)"));
        String message = sc.nextLine();
        while (!message.equals("$q")) {
            if (cc.validateMessage(message)) {
                for (Long event_id: event_ids) {
                    if (cc.sendMessage(event_id, message)) {
                        System.out.println(op.success("event: " + event_id));
                    } else {
                        System.out.println(op.invalidCommand("event id, message not sent to event: " + event_id));
                    }
                }
                message = "$q";
            } else {
                System.out.println(op.invalidCommand("message"));
                System.out.println(op.commandPrompt("message (requires at least 1 character)"));
                message = sc.nextLine();
            }
        }
    }

    /**
     * Prompts and processes a user's request to send a message to one or more speakers.
     *
     * @param speakers      the username(s) of the speakers; speakers must exist in Registrar
     */
    public void promptMessage(ArrayList<String> speakers) {
        System.out.println(op.commandPrompt("message (requires at least 1 character)"));
        String message = sc.nextLine();
        while (!message.equals("$q")) {
            if (cc.validateMessage(message)) {
                for (String speaker : speakers) {
                    cc.sendMessage(speaker, message);
                    System.out.println(op.success("@" + speaker));
                }
                message = "$q";
            } else {
                System.out.println(op.invalidCommand("message"));
                System.out.println(op.commandPrompt("message (requires at least 1 character)"));
                message = sc.nextLine();
            }
        }
    }
}
