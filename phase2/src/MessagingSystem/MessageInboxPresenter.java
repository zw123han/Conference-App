package MessagingSystem;

import java.util.*;

/**
 * The Presenter for the Message Inbox.
 * Handles model-side interactions upon request by the Message Inbox View (GUI) and
 * updates the View with the updated data requested from Message Inbox Controller.
 *
 * @author Chrisee
 */
public class MessageInboxPresenter {
    private MessageInboxController messageInboxController;
    private MessageInboxView view;

    /**
     * Initiates a new InboxController
     *
     * @param messageInboxController        MessageInboxController
     */
    public MessageInboxPresenter(MessageInboxController messageInboxController) {
        this.messageInboxController = messageInboxController;
    }

    /**
     * Sets this presenter's MessageInboxView to view.
     *
     * @param view       the view associated with this presenter
     */
    public void setView(MessageInboxView view) {
        this.view = view;
    }

    /**
     * Sets username of the controller to that of the currently logged in user.
     *
     * @param currentUser       username of the current user
     */
    public void setLoggedInUser(String currentUser) {
        messageInboxController.setLoggedInUser(currentUser);
    }

    /**
     * Gets the display name associated with the senderUsername.
     *
     * @param username         String of username
     * @return                 String of display name
     */
    public String getDisplayName(String username) {
        return messageInboxController.getDisplayName(username);
    }

    /**
     * Updates view with the chatrooms available for the logged in user.
     */
    public void loadChatroomCanvasView() {
        view.setChatroomCanvasTitle(messageInboxController.getTotalUnread());
        view.clearChatroomOptions();
        for (ArrayList<String> option: sortChatrooms(messageInboxController.getChatroomOptions())) {
            view.setChatroomOption(option);
        }
    }

    /**
     * Marks all messages in the logged in user's chatroom with recipient as read.
     *
     * @param recipient   username of recipient
     */
    public void markAllUnread(String recipient) {
        messageInboxController.markAllRead(recipient);
    }

    /**
     * Updates view with the messages in the logged in user's chatroom with recipient.
     */
    public void loadMessageCanvasView(String recipient) {
        if (!recipient.equals("")) {
            view.clearMessages();
            view.setMessageCanvasTitle(messageInboxController.getDisplayName(recipient));
            view.setChatroomCanvasTitle(messageInboxController.getTotalUnread());
            for (ArrayList<String> messageData : messageInboxController.getMessages(recipient)) {
                view.setMessageArea(messageData);
            }
        }
    }

    /**
     * Updates view with the pinned messages in the logged in user's chatroom with recipient.
     */
    public void loadPinnedView(String recipient) {
        view.clearMessages();
        view.setMessageCanvasTitle("Pinned Messages");
        for (ArrayList<String> messageData : messageInboxController.getPinned(recipient)) {
            view.setMessageArea(messageData);
        }
    }

    /**
     * Sends a message to recipient and updates view with the newest message.
     */
    public void sendMessage(String message, String recipient) {
        if (messageInboxController.validateMessage(message)) {
            messageInboxController.sendMessage(recipient, message);
            view.setMessageArea(messageInboxController.getNewestMessage(recipient));
        }
    }

    /**
     * Updates view with chatrooms with recipients whose display names or usernames
     * partially or completely match searchInput. For example, if the search input is "hen" then:
     * - Display names or usernames such as henry, Henry, or Henricks would match.
     * - Display names or usernames such as twohens or he would NOT match.
     */
    public void updateChatroomCanvasView(String searchInput) {
        view.clearChatroomOptions();
        String regex = "(?i)" + searchInput + "[^.]*";
        for (ArrayList<String> option: sortChatrooms(messageInboxController.getChatroomOptions())) {
            if (option.get(0).matches(regex) || option.get(1).matches(regex)) {
                view.setChatroomOption(option);
            }
        }
    }

    /**
     * Removes message with id from chatroom with recipient and updates view.
     */
    public void removeMessage(String id, String recipient) {
        messageInboxController.deleteMessage(recipient, id);
        loadMessageCanvasView(recipient);
    }

    /**
     * Pins a message with id in the logged in user's chatroom with recipient if the message is pinned.
     * Otherwise, the message will be unpinned.
     * The request is passed directly onto the messageInboxController.
     *
     * @param recipient     username of the recipient
     * @param id            index of the message
     */
    public void pinUnpinMessage(String id, String recipient) {
        messageInboxController.pinUnpinMessage(recipient, id);
    }

    /**
     * Marks a message with id in the logged in user's chatroom with recipient as read if the message is unread.
     * Otherwise, the message will be marked as unread.
     *
     * @param recipient     username of the recipient
     * @param id            index of the message
     */
    public void markReadUnread(String id, String recipient) {
        messageInboxController.markReadUnread(recipient, id);
        loadChatroomCanvasView();
    }

    /**
     * Checks whether the logged in user can delete a message. They can delete a message when:
     * - The logged in user is the sender;
     * - The logged in user is an admin.
     *
     * @param username      String of the message's sender's username
     * @return boolean      True if length is not 0, false otherwise
     */
    public boolean canDelete(String username) {
        return messageInboxController.canDelete(username);
    }

    /**
     * Checks whether the message with id in the logged in user's chatroom with
     * recipient is a pinned message.
     *
     * @param id         id of the message
     * @param recipient  username of the recipient
     * @return    True if the message is pinned.
     */
    public boolean isPinned(String id, String recipient) {
        return messageInboxController.isPinned(id, recipient);
    }

    /**
     * Checks whether the message with id in the logged in user's chatroom with
     * recipient is read already.
     *
     * @param id         id of the message
     * @param recipient  username of the recipient
     * @return    True if the message is read.
     */
    public boolean isRead(String id, String recipient) {
        return messageInboxController.isRead(id, recipient);
    }

    /**
     * Checks whether the logged in user can send a group message. They can send a group message
     * when the logged in user is an admin, an organizer, or a speaker.
     *
     * @return boolean      True if the logged in user can send a group message.
     */
    public boolean canGroupMessage() {
        return messageInboxController.canGroupMessage();
    }

    /**
     * Checks whether the logged in user can mark a message sent by sender as read/unread.
     *
     * @param sender        username of the message's sender
     * @return boolean      True if the logged in user can mark the sender's message as read/unread.
     */
    public boolean canMarkReadUnread(String sender) {
        return messageInboxController.canMarkReadUnread(sender);
    }

    // ===================
    //   PRIVATE HELPERS
    // ===================

    // Sorts the chatrooms such that the chatroom with the most # of unread messages
    // is displayed first in the view.
    private ArrayList<ArrayList<String>> sortChatrooms(ArrayList<ArrayList<String>> chatrooms) {
        ArrayList<ArrayList<String>> sortedChatrooms = new ArrayList<>();
        for (ArrayList<String> chatroom : chatrooms) {
            int i = 0;
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
        }
        return sortedChatrooms;
    }

    /**
     * Interface for the MessageInbox's view.
     */
    public interface MessageInboxView {

        /**
         * Adds options containing chatroom data to the chatroom options area in the chatroom canvas.
         * Each set of chatroom data is stored in an ArrayList in the following form:
         * [Display name of the other user, username of the other user, number of unread messages]
         *
         * @param chatroomData  an Arraylist of chatroom data
         */
        void setChatroomOption(ArrayList<String> chatroomData);

        /**
         * Sets the title of the message canvas to be the recipient's display name.
         *
         * @param recipientName  String of the display name of the recipient
         */
        void setMessageCanvasTitle(String recipientName);

        /**
         * Sets the title of the inbox to include the number of total unread messages.
         *
         * @param numberUnread  String of number of total unread messages
         */
        void setChatroomCanvasTitle(String numberUnread);

        /**
         * Adds a message containing message data to the message display area of the message canvas.
         * The message data is stored in an Arraylist in the following form:
         * [Sender username, local date time, filtered message, message id]
         *
         * @param messageData  an Arraylist of message data
         */
        void setMessageArea(ArrayList<String> messageData);

        /**
         * Clears all messages from message canvas display.
         */
        void clearMessages();

        /**
         * Clears all chatrooms from chatroom canvas display.
         */
        void clearChatroomOptions();
    }
}
