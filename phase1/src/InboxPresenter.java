import java.util.*;

public class InboxPresenter extends CommandPresenter {

    public void menuDisplay(Registrar reg, ArrayList<String> friends) {
        System.out.println("\nCHAT HISTORY:");
        for (String friend : friends) {
            System.out.println(reg.getUserByUserName(friend).getName() + " (@" + friend + ")");
        }
        System.out.println("\n");
    }

    public void chatView(Chatroom cm) {
        ArrayList<Message> history = cm.getHistory();
        for (Message m : history) {
            System.out.println("From: " + m.getSender());
            System.out.println("Sent: " + m.getDate());
            System.out.println(m.getMessage() + "\n");
        }
    }
}
