import java.util.Scanner;

public class ChatMenuController {
    private ChatMenuPresenter chatMenuPresenter = new ChatMenuPresenter();
    private Scanner scanner = new Scanner(System.in);

    public void promptInOutChoice(){
        chatMenuPresenter.menuDisplay();
        chatMenuPresenter.commandPrompt("prompt");
        String choice = sc.nextLine();
        while (!choice.equals("$q")) {
            if (choice.equals("1")) {
                promptRecipient();
            } else if (choice.equals("2")) {
                promptEvent();
            } else {
                chatMenuPresenter.invalidCommand("command");
                chatMenuPresenter.menuDisplay();
                choice = sc.nextLine();
            }
        }
    }
}
