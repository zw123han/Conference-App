import java.util.*;

public class InboxPresenter extends CommandPresenter {

    public void menuDisplay(Registrar reg, ArrayList<String> friends) {
        System.out.println("\nCHAT HISTORY:");
        for (String friend : friends) {
            System.out.println(reg.getUserByUserName(friend).getName() + " (@" + friend + ")");
        }
        if (friends.isEmpty()) {
            System.out.println("You can't chat with any users.");
        }
        System.out.println("");
    }

    public void chatView(Registrar reg, Chatroom cm) {
        ArrayList<Message> history = cm.getHistory();
        for (Message m : history) {
            String sender = m.getSender();
            System.out.println("\nFrom: " + reg.getUserByUserName(sender).getName() + " (@" + sender + ")");
            System.out.println("Sent: " + m.getDate());
            messageFormatter(m.getMessage());
        }
        System.out.println("");
    }

    public void messageFormatter(String message) {
        StringBuilder sbm = new StringBuilder(message);
        int i = 0;
        while (i + 80 < sbm.length()) {
            int firstSpace = sbm.indexOf(" ", i + 80);
            if (firstSpace != -1) {
                sbm.replace(firstSpace, firstSpace + 1, "\n");
            }
            i += 80;
        }
        System.out.println(sbm);
    }

    public void replyMessage() {
        System.out.println("Press enter to reply to the message.");
        exitMessage();
    }
}
