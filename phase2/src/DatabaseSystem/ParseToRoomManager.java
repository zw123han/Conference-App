package DatabaseSystem;

import RoomSystem.Room;
import RoomSystem.RoomManager;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.ArrayList;

/**
 * This class is responsible for parsing through a database collection that stores room information and converting
 * that information back into a RoomManager instance.
 *
 * @author Jesse
 */
public class ParseToRoomManager implements ParserStrategy {

    private Room createRoom(DBObject doc) {
        return new Room((String) doc.get("roomId"), (Integer) doc.get("capacity"));
    }

    /**
     * Returns a new RoomManager instance based on the given collection.
     *
     * @param collection    The collection that stores room information.
     * @return              A new RoomManager instance.
     */
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
