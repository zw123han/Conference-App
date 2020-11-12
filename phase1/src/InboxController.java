import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class InboxController {
    private ChatController cc = new ChatController();
    private InboxPresenter ip = new InboxPresenter();
    private Scanner sc = new Scanner(System.in);
    private User user;
    private Registrar reg;

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

    public void promptChatChoice() {
        ChatPull pull = new ChatPull();
        ChatroomManager cm = pull.readChatlog();
        ArrayList<String> friends = getUsersTalkto(user, cm);
        ip.menuDisplay(reg, friends);
        ip.commandPrompt("username (string after the @)");
        String recipient = sc.nextLine();
        while (!recipient.equals("$q")) {
            if (friends.contains(recipient)) {
                chatViewer(cm, recipient);
            } else {
                ip.invalidCommand("username");
            }
            ip.menuDisplay(reg, friends);
            ip.commandPrompt("username (string after the @)");
            recipient = sc.nextLine();
        }
    }

    public void chatViewer(ChatroomManager cm, String recipient) {
        ip.chatView(reg, cm.getChatroom(user, recipient));
        String e = "";
        while (!e.equals("$q")) {
            if (cc.canReply(user, recipient, cm)) {
                promptReply(user, recipient);
            }
            ip.exitMessage();
            e = sc.nextLine();
        }
    }

    public void promptReply(User user, String recipient) {
        OutboxController oc = new OutboxController(reg, user);
        oc.promptMessage(recipient);
        ChatPull pull = new ChatPull();
        ChatroomManager cm = pull.readChatlog();
        ip.chatView(reg, cm.getChatroom(user, recipient));
    }
}
