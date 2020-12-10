package DatabaseSystem;

import RoomSystem.Room;
import RoomSystem.RoomManager;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.ArrayList;

public class ParseToRoomManager implements ParserStrategy {

    private Room createRoom(DBObject doc) {
        return new Room((String) doc.get("roomId"), (Integer) doc.get("capacity"));
    }

    @Override
    public Savable parseCollection(DBCollection collection) {
        ArrayList<Room> rooms = new ArrayList<>();
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            rooms.add(createRoom(cursor.next()));
        }
        return new RoomManager(rooms);

    }
}
