import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class InboxController {
    private ChatController cc = new ChatController();
    private InboxPresenter ip = new InboxPresenter();
    private Scanner sc = new Scanner(System.in);
    private User user;
    private Registrar reg;

    /**
     * (please describe)
     *
     * @param reg       (please describe)
     * @param user      (please describe)
     */
    public InboxController(Registrar reg, User user) {
        this.user = user;
        this.reg = reg;
    }

    private ArrayList<String> getUsersTalkto(User user, ChatroomManager cm) {
        ArrayList<String> users = new ArrayList<>();
        HashMap<ArrayList<String>, Chatroom> cms = cm.getAllChatrooms(user);
        for (ArrayList<String> key : cms.keySet()) {
            if (key.contains(user.getUserName())) {
                for (String person : key) {
                    if (!person.equals(user.getUserName())) {
                        users.add(person);
                    }
                }
            }
        }
        return users;
    }

    /**
     * (please describe)
     */
    public void promptChatChoice() {
        ChatPull pull = new ChatPull();
        ChatroomManager cm = pull.readChatlog();
        ArrayList<String> friends = getUsersTalkto(user, cm);
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
     * (please describe)
     *
     * @param cm            (please describe)
     * @param recipient     (please describe)
     */
    public void chatViewer(ChatroomManager cm, String recipient) {
        ip.chatView(reg, cm.getChatroom(user, recipient));
        String e = "";
        while (!e.equals("$q")) {
            if (cc.canReply(user, recipient, cm)) {
                promptReply(user, recipient);
                break;
            }
            ip.exitMessage();
            e = sc.nextLine();
        }
    }

    /**
     * (please describe)
     *
     * @param user          (please describe)
     * @param recipient     (please describe)
     */
    public void promptReply(User user, String recipient) {
        OutboxController oc = new OutboxController(reg, user);
        ip.replyMessage();
        String re = sc.nextLine();
        while (!re.equals("$q")) {
            if (re.equals("")) {
                oc.promptMessage(recipient);
                ChatPull pull = new ChatPull();
                ChatroomManager cm = pull.readChatlog();
                ip.chatView(reg, cm.getChatroom(user, recipient));
            } else {
                ip.invalidCommand("prompt");
            }
            ip.replyMessage();
            re = sc.nextLine();
        }
    }
}
