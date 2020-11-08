public class ChatroomController {
    private Chatroom chatroom;

    ChatroomController(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public void send(Message message) {
        this.chatroom.sendMessage(message);
    }

    public Chatroom getChatroom() {
        return this.chatroom;
    }
}
