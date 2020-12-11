package RoomSystem;

public class RoomController {
    private RoomManager roomManager;

    public RoomController(RoomManager roomManager){
        this.roomManager = roomManager;
    }

    public boolean makeRoom(String roomID, int capacity){
        return roomManager.makeRoom(roomID, capacity);
    }
    public void deleteRoom(String roomID){
        roomManager.deleteRoom(roomID);
    } // when deleting rooms in menu, check if any events have such a room. if yes, do not delete.
}
