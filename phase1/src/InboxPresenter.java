import java.util.*;

public class InboxPresenter extends CommandPresenter {

    public void menuDisplay(ArrayList<String> friends) {
        System.out.println("CHAT HISTORY:");
        int n = 1;
        for (String friend : friends) {
            System.out.println(n + ". " + friend);
            n += 1;
        }
    }

    public void chatView(Chatroom cm) {
        ArrayList<Message> history = cm.getHistory();
        for (Message m : history) {
            System.out.println(m.getSender() + " | Sent: " + m.getDate());
            System.out.println(m.getMessage() + "\n\n");
        }
    }
}
