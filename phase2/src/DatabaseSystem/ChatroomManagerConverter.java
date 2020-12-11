package DatabaseSystem;

import MessagingSystem.Chatroom;
import MessagingSystem.ChatroomManager;
import MessagingSystem.Message;
import com.mongodb.BasicDBObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class is responsible for converting a ChatroomManager object into a list of MongoDB documents to be added to a
 * collection in the database.
 *
 * @author Jesse
 */
public class ChatroomManagerConverter implements ConversionStrategy {

    private BasicDBObject convertMessage(Message msg) {
        BasicDBObject doc = new BasicDBObject();
        doc.put("message", msg.getMessage());
        doc.put("sender", msg.getSender());
        doc.put("time", msg.getDate());
        doc.put("read", msg.isRead());
        doc.put("pinned", msg.isPinned());

        return doc;
    }

    private BasicDBObject convertOne(ArrayList<String> users, Chatroom chatroom) {
        BasicDBObject doc = new BasicDBObject();
        doc.put("users", users);
        ArrayList<BasicDBObject> messages = new ArrayList<>();
        for (Message message : chatroom.getAllMessages()) {
            messages.add(convertMessage(message));
        }

        doc.put("history", messages);

        return doc;
    }

    /**
     * Returns an array list of documents to be added to a collection based on the given ChatroomManager object.
     *
     * @param sv    A ChatroomManager object.
     * @return      A list of MongoDB documents.
     */
    @Override
    public ArrayList<BasicDBObject> convertAll(Savable sv) {
        ChatroomManager chatroomManager = (ChatroomManager) sv;
        ArrayList<BasicDBObject> documentList = new ArrayList<>();
        for (Map.Entry element : chatroomManager.getChatrooms().entrySet()) {

            documentList.add(convertOne((ArrayList<String>) element.getKey(), (Chatroom) element.getValue()));
        }

        return documentList;
    }
}
