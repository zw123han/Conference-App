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
public class MessageInboxController {
    private MessageInboxPresenter ip;
    private Scanner sc = new Scanner(System.in);
    private String username;
    private Registrar reg;
    private MessageOutboxController oc;

    /**
     * Initiates a new InboxController
     *
     * @param reg       Registrar
     * @param username  username of the currently logged in user
     * @param ip        InboxPresenter
     * @param oc        OutboxController
     */
    public MessageInboxController(Registrar reg, String username, MessageInboxPresenter ip, MessageOutboxController oc) {
        this.reg = reg;
        this.username = username;
        this.ip = ip;
        this.oc = oc;
    }

    private boolean canReply(Registrar reg, String username, String recipient, ChatroomManager cm) {
        if (cm.hasChatroom(username, recipient)) {
            if (reg.isAdmin(username) || reg.isOrganizer(username) || reg.isAttendee(username)) {
                return true;
            }
            Chatroom c = cm.getChatroom(username, recipient);
            ArrayList<Integer> history = c.getMessagePositions();
            for (Integer m : history) {
                if (c.getSender(m).equals(recipient)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canDelete(ChatroomManager cm, String username, String recipient, int choice){
        Chatroom chatroom = cm.getChatroom(username, recipient);
        if(choice < chatroom.getSize()){
            return chatroom.getSender(choice).equals(username);
        }
        return false;
    }

    private ArrayList<String> getUsersTalkto(String username, ChatroomManager cm) {
        ArrayList<String> users = new ArrayList<>();
        HashMap<ArrayList<String>, Chatroom> cms = cm.getAllChatrooms(username);
        for (ArrayList<String> key : cms.keySet()) {
            if (key.contains(username)) {
                for (String person : key) {
                    if (!person.equals(username)) {
                        users.add(person);
                    }
                }
            }
        }
        return users;
    }

    /**
     * Prompts the user to choose a chatlog from a list of existing chatlogs by username.
     */
    public void promptChatChoice() {
        ReadChat pull = new ReadChat();
        ChatroomManager cm = pull.readChatlog();
        ArrayList<String> friends = getUsersTalkto(username, cm);
        System.out.println(ip.menuDisplay(reg, friends));
        System.out.println(ip.commandPrompt("username"));
        String recipient = sc.nextLine();
        while (!recipient.equals("$q")) {
            recipient = recipient.replace("@", "");
            if (friends.contains(recipient)) {
                chatViewer(cm, recipient);
            } else {
                System.out.println(ip.invalidCommand("username"));
            }
            System.out.println(ip.menuDisplay(reg, friends));
            System.out.println(ip.commandPrompt("username"));
            recipient = sc.nextLine();
        }
    }

    /**
     * Prompts the user for whether they want to reply (if able) or whether they want to exit the chat.
     *
     * @param cm            ChatroomManager
     * @param recipient     username of a user that the logged in user has chatted with
     */
    public void chatViewer(ChatroomManager cm, String recipient) {
        System.out.println(ip.chatView(reg, cm.getChatroom(username, recipient)));
        String e = "";
        while (!e.equals("$q")) {
            System.out.println(ip.deleteMessage());
            if (canReply(reg, username, recipient, cm)) {
                System.out.println(ip.replyMessage());
            }
            System.out.println(ip.exitMessage());
            e = sc.nextLine();
            if (e.equals("1")){
                promptDelete(cm, recipient);
                ReadChat pull = new ReadChat();
                cm = pull.readChatlog();
                System.out.println(ip.chatView(reg, cm.getChatroom(username, recipient)));
            }else if (e.equals("2") && canReply(reg, username, recipient, cm)){
                promptReply(recipient);
                ReadChat pull = new ReadChat();
                cm = pull.readChatlog();
                System.out.println(ip.chatView(reg, cm.getChatroom(username, recipient)));
            }
        }
    }

    /**
     * Prompts the user for which message user wants to delete
     *
     * @param cm            ChatroomManager
     * @param recipient     username of a user that the logged in user has chatted with
     */
    public void promptDelete(ChatroomManager cm, String recipient){
        System.out.println(ip.whichMessage());
        String re = sc.nextLine();
        while (!re.equals("$q")){
            if(re.matches("[0-9]+") && canDelete(cm, username, recipient, Integer.parseInt(re))){
                cm.deleteMessage(username, recipient, Integer.parseInt(re));
                StoreChat push = new StoreChat();
                push.storeChat(cm);
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
        oc.promptMessage(recipient);
    }
}
