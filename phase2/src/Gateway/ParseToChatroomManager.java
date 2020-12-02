package Gateway;

import MessagingSystem.Chatroom;
import MessagingSystem.ChatroomManager;
import MessagingSystem.Message;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class ParseToChatroomManager implements ParserStrategy {

    private Message parseMessage(DBObject doc) {
        Message msg = new Message( (String) doc.get("message"), (String) doc.get("sender"));
        DateTimeFormatter d = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        ZonedDateTime time = LocalDateTime.parse((String) doc.get("time"), d).atZone(ZoneId.of("Canada/Eastern"));
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
