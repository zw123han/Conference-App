import java.util.*;

/**
 * (please describe)
 *
 * @author
 * @version %I%, %G%
 */
public class InboxPresenter extends CommandPresenter {

    /**
     * (please describe)
     *
     * @param reg           (please describe)
     * @param friends       (please describe)
     */
    public void menuDisplay(Registrar reg, ArrayList<String> friends) {
        System.out.println("\nCHAT HISTORY:");
        System.out.println("------------------------");
        for (String friend : friends) {
            System.out.println(reg.getNameByUsername(friend) + " (@" + friend + ")");
        }
        if (friends.isEmpty()) {
            System.out.println("You can't chat with any users.");
        }
        System.out.println();
    }

    /**
     * (please describe)
     *
     * @param reg       (please describe)
     * @param cm        (please describe)
     */
    public void chatView(Registrar reg, Chatroom cm) {
        ArrayList<Integer> history = cm.getMessageKeys();
        for (Integer m : history) {
            String sender = cm.getSender(m);
            System.out.println("\nFrom: " + reg.getNameByUsername(sender) + " (@" + sender + ")");
            System.out.println("Sent: " + cm.getDate(m));
            messageFormatter(cm.getMessage(m));
        }
        System.out.println();
    }

    /**
     * (please describe)
     *
     * @param message       (please describe)
     */
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

    /**
     * (please describe)
     */
    public void replyMessage() {
        System.out.println("Press enter to reply to the message.");
        exitMessage();
    }
}
