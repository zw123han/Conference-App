import java.util.Scanner;

public class ChatMenuController {
    private ChatMenuPresenter chatMenuPresenter = new ChatMenuPresenter();
    private InboxController inboxController;
    private OutboxController outboxController;
    private Scanner scanner = new Scanner(System.in);
    private User user;

    public ChatMenuController(User user){
        this.user = user;
        InboxController inboxController = new InboxController(user);
        OutboxController outboxController = new OutboxController(user);
    }

    public void promptInOutChoice(){
        chatMenuPresenter.menuDisplay();
        chatMenuPresenter.commandPrompt("prompt");
        String choice = scanner.nextLine();
        while (!choice.equals("$q")) {
            if (choice.equals("1")) {
                promptOutbox();
            } else if (choice.equals("2")) {
                promptInbox();
            } else {
                chatMenuPresenter.invalidCommand("command");
                chatMenuPresenter.menuDisplay();
                choice = scanner.nextLine();
            }
        }
    }

    public void promptOutbox(){
        outboxController.promptChatChoice();
    }

    public void promptInbox(){
        inboxController.promptChatChoice();
    }
}
