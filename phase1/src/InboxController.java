import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * For taking in user inputs for viewing chat history and replying to messages.
 *
 * @author Chrisee, Elliot
 * @version %I%, %G%
 */
public class InboxController {
    private InboxPresenter ip = new InboxPresenter();
    private Scanner sc = new Scanner(System.in);
    private String username;
    private Registrar reg;
    private EventManager em;

    /**
     * Initiates a new InboxController
     *
     * @param reg       Registrar
     * @param username  username of the currently logged in user
     * @param em        EventManager
     */
    public InboxController(Registrar reg, String username, EventManager em) {
        this.reg = reg;
        this.username = username;
        this.em = em;
    }

    private boolean canReply(Registrar reg, String username, String recipient, ChatroomManager cm) {
        if (cm.hasChatroom(username, recipient)) {
            if (reg.isOrganizer(username) || reg.isAttendee(username)) {
                return true;
            }
            Chatroom c = cm.getChatroom(username, recipient);
            ArrayList<Integer> history = c.getMessageKeys();
            for (Integer m : history) {
                if (c.getSender(m).equals(recipient)) {
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<String> getUsersTalkto(String username, ChatroomManager cm) {
        ArrayList<String> users = new ArrayList<>();
        HashMap<ArrayList<String>, Chatroom> cms = cm.getAllChatrooms(username);
        for (ArrayList<String> key : cms.keySet()) {
            if (key.contains(username)) {
                for (String person : key) {
                    if (!person.equals(username)) {
                        users.add(person);
                    }
                }
            }
        }
        return users;
    }

    /**
     * Prompts the user to choose a chatlog from a list of existing chatlogs by username.
     */
    public void promptChatChoice() {
        ChatPull pull = new ChatPull();
        ChatroomManager cm = pull.readChatlog();
        ArrayList<String> friends = getUsersTalkto(username, cm);
        ip.menuDisplay(reg, friends);
        ip.commandPrompt("username");
        String recipient = sc.nextLine();
        while (!recipient.equals("$q")) {
            recipient = recipient.replace("@", "");
            if (friends.contains(recipient)) {
                chatViewer(cm, recipient);
            } else {
                ip.invalidCommand("username");
            }
            ip.menuDisplay(reg, friends);
            ip.commandPrompt("username");
            recipient = sc.nextLine();
        }
    }

    /**
     * Prompts the user for whether they want to reply (if able) or whether they want to exit the chat.
     *
     * @param cm            ChatroomManager
     * @param recipient     username of a user that the logged in user has chatted with
     */
    public void chatViewer(ChatroomManager cm, String recipient) {
        ip.chatView(reg, cm.getChatroom(username, recipient));
        String e = "";
        while (!e.equals("$q")) {
            if (canReply(reg, username, recipient, cm)) {
                promptReply(recipient);
                break;
            }
            ip.exitMessage();
            e = sc.nextLine();
        }
    }

    /**
     * Prompts the user for a reply to be sent into the current chat.
     *
     * @param recipient     username of a user that the logged in user has chatted with
     */
    public void promptReply(String recipient) {
        OutboxController oc = new OutboxController(reg, username, em);
        ip.replyMessage();
        String re = sc.nextLine();
        while (!re.equals("$q")) {
            if (re.equals("")) {
                oc.promptMessage(recipient);
                ChatPull pull = new ChatPull();
                ChatroomManager cm = pull.readChatlog();
                ip.chatView(reg, cm.getChatroom(username, recipient));
            } else {
                ip.invalidCommand("prompt");
            }
            ip.replyMessage();
            re = sc.nextLine();
        }
    }
}
