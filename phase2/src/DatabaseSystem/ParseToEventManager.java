package DatabaseSystem;

import EventSystem.Event;
import EventSystem.EventManager;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class ParseToEventManager implements ParserStrategy {

    private Event createEvent(DBObject doc) {
        Date date = (Date) doc.get("time");
        LocalDateTime local = new java.sql.Timestamp(date.getTime()).toLocalDateTime();

        Event evt = new Event((String) doc.get("name"),
                (String) doc.get("room"),
                local,
                (Long) doc.get("duration"),
                (ArrayList<String>) doc.get("speaker"),
                (Integer) doc.get("capacity"));
        for (String userName: (ArrayList<String>) doc.get("signedUp")) {
            evt.addUser(userName);
        }
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
