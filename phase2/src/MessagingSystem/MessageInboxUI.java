package MessagingSystem;

import UserSystem.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * For taking in user inputs for viewing chat history and replying to messages.
 *
 * @author Chrisee, Elliot
 */
public class MessageInboxUI {
    private MessageInboxController ic;
    private MessageInboxPresenter ip;
    private Scanner sc = new Scanner(System.in);
    private MessageOutboxUI mo;

    /**
     * Initiates a new InboxController
     *
     * @param ip        InboxPresenter
     * @param mo        MessageOutboxUI
     */
    public MessageInboxUI(MessageInboxPresenter ip, MessageInboxController ic, MessageOutboxUI mo) {
        this.ip = ip;
        this.mo = mo;
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
            System.out.println(ip.deleteMessage());
            if (ic.canReply(recipient)) {
                System.out.println(ip.replyMessage());
            }
            System.out.println(ip.exitMessage());
            e = sc.nextLine();
            if (e.equals("1")){
                promptDelete(recipient);
                System.out.println(ip.chatView(recipient));
            }else if (e.equals("2") && ic.canReply(recipient)){
                promptReply(recipient);
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
        System.out.println(ip.whichMessage());
        String re = sc.nextLine();
        while (!re.equals("$q")){
            if(re.matches("[0-9]+") && ic.canDelete(recipient, re)){
                ic.deleteMessage(recipient, re);
                break;
            }else{
                System.out.println(ip.invalidCommand("number"));
            }
            System.out.println(ip.whichMessage());
            re = sc.nextLine();
        }
    }

    /**
     * Prompts the user for a reply to be sent into the current chat.
     *
     * @param recipient     username of a user that the logged in user has chatted with
     */
    public void promptReply(String recipient) {
        mo.promptMessage(recipient);
    }
}
