import java.util.*;

public class InboxPresenter extends CommandPresenter {

    public void menuDisplay(Registrar reg, ArrayList<String> friends) {
        System.out.println("\nCHAT HISTORY:");
        for (String friend : friends) {
            System.out.println(reg.getUserByUserName(friend).getName() + " (@" + friend + ")");
        }
        System.out.println("\n");
    }

    public void chatView(Registrar reg, Chatroom cm) {
        ArrayList<Message> history = cm.getHistory();
        for (Message m : history) {
            String sender = m.getSender();
            System.out.println("From: " + reg.getUserByUserName(sender) + " (@" + sender + ")");
            System.out.println("Sent: " + m.getDate());
            messageFormatter(m.getMessage());
            System.out.println("\n");
        }
    }

    public void messageFormatter(String message) {
        StringBuilder sbm = new StringBuilder(message);
        int i = 0;
        while (i + 50 < sbm.length()) {
            int firstSpace = sbm.indexOf(" ", i + 50);
            sbm.replace(firstSpace,  firstSpace + 1, "\n");
            i += 50;
        }
        System.out.println(sbm);
    }
}
