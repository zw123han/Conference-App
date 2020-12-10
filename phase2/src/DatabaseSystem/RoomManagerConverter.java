package DatabaseSystem;

import RoomSystem.Room;
import RoomSystem.RoomManager;
import com.mongodb.BasicDBObject;

import java.util.ArrayList;

public class RoomManagerConverter implements ConversionStrategy{

    private BasicDBObject convertRoom(Room room) {
        BasicDBObject doc = new BasicDBObject();
        doc.put("capacity", room.getCapacity());
        doc.put("roomId", room.getRoomID());
        return doc;
    }

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
