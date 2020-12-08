package DatabaseSystem;

import EventSystem.Event;
import EventSystem.EventManager;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ParseToEventManager implements ParserStrategy {

    private Event createEvent(DBObject doc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime local = LocalDateTime.parse((String) doc.get("time"), formatter);

        Event evt = new Event((String) doc.get("name"),
                (String) doc.get("room"),
                local,
                Long.parseLong((String) doc.get("duration")),
                (ArrayList<String>) doc.get("speaker"),
                (Integer) doc.get("capacity"));
        for (String userName: (ArrayList<String>) doc.get("signedUp")) {
            evt.addUser(userName);
        }
        evt.setId(Long.parseLong((String) doc.get("id")));
        return evt;
    }

    @Override
    public Savable parseCollection(DBCollection collection) {
        ArrayList<Event> events = new ArrayList<>();
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            events.add(createEvent(cursor.next()));
        }
        return new EventManager(events);
    }
}
