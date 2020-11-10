import java.util.Scanner;
import java.util.*;

public class InboxPresenter {
    private ChatController cc = new ChatController();
    private ChatroomManager cm;
    private Scanner sc = new Scanner(System.in);
    private User user;

    public InboxPresenter(User user, ChatroomManager cm) {
        this.user = user;
        this.cm = cm;
    }

    public void promptChatChoice() {
        System.out.println("Enter username to view chat history\n$back to exit");
        String recipient = sc.nextLine();
        while (!recipient.equals("$back")) {
            if (user.hasFriend(recipient) && cm.hasChatroom(user, recipient)) {
                //allows client to view chat
            } else {
                System.out.println("Invalid username.");
                System.out.println("Enter username to view chat history\n$back to exit");
                recipient = sc.nextLine();
            }
        }
    }

    // prototype message display format
    public void chatView(Chatroom cm) {
        ArrayList<Message> history = cm.getHistory();
        for (Message m : history) {
            System.out.println(m.getSender() + " | Sent: " + m.getDate());
            System.out.println(m.getMessage() + "\n\n");
        }
    }
}
