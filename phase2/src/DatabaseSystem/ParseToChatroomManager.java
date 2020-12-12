package DatabaseSystem;

import MessagingSystem.Chatroom;
import MessagingSystem.ChatroomManager;
import MessagingSystem.Message;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is responsible for parsing through a database collection that stores chatroom information and converting
 * that information back into a ChatroomManager instance.
 *
 * @author Jesse
 */
public class ParseToChatroomManager implements ParserStrategy {

    private Message parseMessage(DBObject doc) {
        Message msg = new Message( (String) doc.get("message"), (String) doc.get("sender"));

        ZonedDateTime time = ZonedDateTime.parse((String) doc.get("time"));
        msg.setDate(time);
        if ((Boolean) doc.get("read") == true) {
            msg.read();
        }
        msg.setPinned((Boolean) doc.get("pinned"));

        return msg;
    }

    private Chatroom parseChatroom(DBObject document) {
        Chatroom chatroom = new Chatroom();
        ArrayList<DBObject> history = (ArrayList<DBObject>) document.get("history");
        for (DBObject doc : history) {
            chatroom.sendMessage(parseMessage(doc));
        }

        return chatroom;
    }

    /**
     * Returns a new ChatroomManager instance based on the given collection.
     *
     * @param collection    The collection that stores chatroom information.
     * @return              A new ChatroomManager instance.
     */
    @Override
    public Savable parseCollection(DBCollection collection) {
        HashMap<ArrayList<String>, Chatroom> chatrooms = new HashMap<>();
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            chatrooms.put((ArrayList<String>) doc.get("users"), parseChatroom(doc));
        }


        return new ChatroomManager(chatrooms);
    }
}
