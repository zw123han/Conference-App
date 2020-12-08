package MessagingSystem;

import java.util.*;

/**
 * For taking in user inputs for viewing chat history and replying to messages.
 *
 * @author Chrisee, Elliot
 */
public class MessageInboxPresenter {
    private MessageInboxController messageInboxController;
    private IView view;

    /**
     * Initiates a new InboxController
     *
     * @param messageInboxController        InboxPresenter
     */
    public MessageInboxPresenter(MessageInboxController messageInboxController) {
        this.messageInboxController = messageInboxController;
    }

    public void setView(IView view) {
        this.view = view;
    }

    /**
     * Sets username to that of the currently logged in user.
     *
     * @param currentUser       username of the current user
     */

    public void setLoggedInUser(String currentUser) {
        messageInboxController.setLoggedInUser(currentUser);
    }

    public String getDisplayName(String username) {
        return messageInboxController.getDisplayName(username);
    }

    private ArrayList<ArrayList<String>> sortChatrooms(ArrayList<ArrayList<String>> chatrooms) {
        ArrayList<ArrayList<String>> sortedChatrooms = new ArrayList<>();
        int i = 0;
        for (ArrayList<String> chatroom : chatrooms) {
            while (i < sortedChatrooms.size()) {
                if (Integer.parseInt(sortedChatrooms.get(i).get(2)) < Integer.parseInt(chatroom.get(2))) {
                    sortedChatrooms.add(i, chatroom);
                    break;
                }
                i += 1;
            }
            if (i == sortedChatrooms.size()) {
                sortedChatrooms.add(i, chatroom);
            }
            i = 0;
        }
        return sortedChatrooms;
    }

    public void loadChatroomCanvasView() {
        view.setChatroomCanvasTitle(messageInboxController.getTotalUnread());
        view.clearChatroomOptions();
        for (ArrayList<String> option: sortChatrooms(messageInboxController.getChatroomOptions())) {
            view.setChatroomOption(option);
        }
    }

    public void loadMessageCanvasView(String recipient) {
        messageInboxController.markAllRead(recipient);
        view.clearMessages();
        view.setMessageCanvasTitle(messageInboxController.getDisplayName(recipient));
        view.setChatroomCanvasTitle(messageInboxController.getTotalUnread());
        for (ArrayList<String> messageData : messageInboxController.getMessages(recipient)) {
            view.setMessageArea(messageData);
        }
    }

    public void loadPinnedView(String recipient) {
        view.clearMessages();
        view.setMessageCanvasTitle("Pinned Messages");
        for (ArrayList<String> messageData : messageInboxController.getPinned(recipient)) {
            view.setPinnedMessage(messageData);
        }
    }

    public void sendMessage(String message, String recipient) {
        if (messageInboxController.validateMessage(message)) {
            messageInboxController.sendMessage(recipient, message);
            view.setMessageArea(messageInboxController.getNewestMessage(recipient));
        }
    }

    public void updateChatroomCanvasView(String search) {
        view.clearChatroomOptions();
        String regex = "(?i)" + search + "[\\w]*";
        for (ArrayList<String> option: sortChatrooms(messageInboxController.getChatroomOptions())) {
            if (option.get(0).matches(regex) || option.get(1).matches(regex)) {
                view.setChatroomOption(option);
            }
        }
    }

    public void removeMessage(String id, String recipient) {
        messageInboxController.deleteMessage(recipient, id);
        loadMessageCanvasView(recipient);
    }

    public void pinUnpinMessage(String id, String recipient) {
        messageInboxController.pinUnpinMessage(recipient, id);
    }

    public boolean canDelete(String username) {
        return messageInboxController.canDelete(username);
    }

    public boolean isPinned(String id, String recipient) {
        return messageInboxController.isPinned(id, recipient);
    }

    public boolean canSendAll() {
        return messageInboxController.canSendAll();
    }

    public interface IView {
        void setChatroomOption(ArrayList<String> option);
        void setMessageCanvasTitle(String newTitle);
        void setChatroomCanvasTitle(String newTitle);
        void setMessageArea(ArrayList<String> messageData);
        void setPinnedMessage(ArrayList<String> messageData);
        void clearMessages();
        void clearChatroomOptions();
    }
}
