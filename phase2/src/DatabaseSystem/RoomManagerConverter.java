package DatabaseSystem;

import RoomSystem.Room;
import RoomSystem.RoomManager;
import com.mongodb.BasicDBObject;

import java.util.ArrayList;

/**
 * This class is responsible for converting a RoomManager object into a list of MongoDB documents to be added to a
 * collection in the database.
 *
 * @author Jesse
 */
public class RoomManagerConverter implements ConversionStrategy{

    private BasicDBObject convertRoom(Room room) {
        BasicDBObject doc = new BasicDBObject();
        doc.put("capacity", room.getCapacity());
        doc.put("roomId", room.getRoomID());
        return doc;
    }

    /**
     * Returns an array list of documents to be added to a collection based on the given RoomManager object.
     *
     * @param sv    A RoomManager object.
     * @return      A list of MongoDB documents.
     */
    @Override
    public ArrayList<BasicDBObject> convertAll(Savable sv) {
        RoomManager roomManager = (RoomManager) sv;
        ArrayList<BasicDBObject> rooms = new ArrayList<>();
        for (Room room : roomManager.getRooms()) {
            rooms.add(convertRoom(room));
        }

        return rooms;
    }
}
