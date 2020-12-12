package RoomSystem;

/**
 * A controller which gives options to modify the list of rooms.
 *
 * @author Ziwen
 */
public class RoomController {
    private RoomManager roomManager;

    /**
     * Constructor for RoomController.
     *
     * @param roomManager The roomManager which stores a list of rooms.
     */
    public RoomController(RoomManager roomManager){
        this.roomManager = roomManager;
    }

    /**
     * Attempts to add a room to this conference.
     *
     * @param roomID The name of the room to be made.
     * @param capacity The capacity of the room to be made.
     * @return True if and only if a new room with roomID and capacity was added to this conference.
     */
    public boolean makeRoom(String roomID, int capacity){
        return roomManager.makeRoom(roomID, capacity);
    }

    /**
     * Attempts to delete a room from this conference.
     *
     * @param roomID The roomID to be deleted.
     */
    public void deleteRoom(String roomID){
        roomManager.deleteRoom(roomID);
    } // when deleting rooms in menu, check if any events have such a room. if yes, do not delete.
}
