import java.util.Scanner;

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
            if (cm.hasChatroom(user, recipient)) {
                //tbd
            }
            System.out.println("Invalid username.");
            System.out.println("Enter username to view chat history\n$back to exit");
            recipient = sc.nextLine();
        }
    }

    public void chatView(Chatroom cm) {
        //tbd
    }
}
