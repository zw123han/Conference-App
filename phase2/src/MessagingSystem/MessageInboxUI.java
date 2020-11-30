package MessagingSystem;

import java.util.Scanner;

/**
 * For taking in user inputs for viewing chat history and replying to messages.
 *
 * @author Chrisee, Elliot
 */
public class MessageInboxUI implements MessageUI {
    private MessageInboxController ic;
    private MessageInboxPresenter ip;
    private Scanner sc = new Scanner(System.in);

    /**
     * Initiates a new InboxController
     *
     * @param ip        InboxPresenter
     * @param ic        InboxController
     */
    public MessageInboxUI(MessageInboxPresenter ip, MessageInboxController ic) {
        this.ip = ip;
        this.ic = ic;
    }

    /**
     * Prompts the user to choose a chatlog from a list of existing chatlogs by username.
     */
    public void promptChatChoice() {
        System.out.println(ip.menuDisplay());
        System.out.println(ip.commandPrompt("username"));
        String recipient = sc.nextLine();
        while (!recipient.equals("$q")) {
            recipient = recipient.replace("@", "");
            if (ic.canViewChat(recipient)) {
                chatViewer(recipient);
                ic.markAllRead(recipient);
            } else {
                System.out.println(ip.invalidCommand("username"));
            }
            System.out.println(ip.menuDisplay());
            System.out.println(ip.commandPrompt("username"));
            recipient = sc.nextLine();
        }
    }

    /**
     * Prompts the user for whether they want to reply (if able) or whether they want to exit the chat.
     *
     * @param recipient     username of a user that the logged in user has chatted with
     */
    public void chatViewer(String recipient) {
        System.out.println(ip.chatView(recipient));
        String e = "";
        while (!e.equals("$q")) {
            System.out.println(ip.messageMenu());
            if (ic.canReply(recipient)) {
                System.out.println(ip.replyMessage());
            }
            System.out.println(ip.exitMessage());
            e = sc.nextLine();
            if (e.equals("1")){
                promptDelete(recipient);
                System.out.println(ip.chatView(recipient));
            } else if (e.equals("2")){
                promptPin(recipient);
            } else if (e.equals("3")){
                System.out.println(ip.viewPinned(recipient));
                promptPin(recipient);
            }else if (e.equals("4") && ic.canReply(recipient)){
                promptMessage(recipient);
                System.out.println(ip.chatView(recipient));
            }
        }
    }

    /**
     * Prompts the user for which message user wants to delete
     *
     * @param recipient     username of a user that the logged in user has chatted with
     */
    public void promptDelete(String recipient){
        System.out.println(ip.whichMessage("delete"));
        System.out.println(ip.exitMessage());
        String re = sc.nextLine();
        while (!re.equals("$q")){
            if(re.matches("[0-9]+") && ic.canDelete(recipient, re)){
                ic.deleteMessage(recipient, re);
                break;
            }else{
                System.out.println(ip.invalidCommand("number"));
            }
            System.out.println(ip.whichMessage("delete"));
            re = sc.nextLine();
        }
    }

    public void promptPin(String recipient){
        System.out.println(ip.whichMessage("pin/unpin"));
        System.out.println(ip.exitMessage());
        String re = sc.nextLine();
        while (!re.equals("$q")){
            if(re.matches("[0-9]+")){
                ic.pinUnpinMessage(recipient, re);
                break;
            }else{
                System.out.println(ip.invalidCommand("number"));
            }
            System.out.println(ip.whichMessage("pin/unpin"));
            re = sc.nextLine();
        }
    }

    /**
     * Prompts the user for a reply to be sent into the current chat.
     *
     * @param recipient     username of a user that the logged in user has chatted with
     */
    public void promptMessage(String recipient) {
        System.out.println(ip.commandPrompt("message (requires at least 1 character)"));
        String message = sc.nextLine();
        while (!message.equals("$q")) {
            if (ic.validateMessage(message)) {
                ic.sendMessage(recipient, message);
                System.out.println(ip.success(recipient));
                message = "$q";
            } else {
                System.out.println(ip.invalidCommand("message"));
                System.out.println(ip.commandPrompt("message (requires at least 1 character)"));
                message = sc.nextLine();
            }
        }
    }
}
