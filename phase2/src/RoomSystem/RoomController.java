package RoomSystem;

public class RoomController {
    private RoomManager roomManager;

    public RoomController(RoomManager roomManager){
        this.roomManager = roomManager;
    }

    public boolean makeRoom(String roomID, int capacity){
        return roomManager.makeRoom(roomID, capacity);
    }
    public boolean deleteRoom(String roomID){
        return roomManager.deleteRoom(roomID);
    }


}
