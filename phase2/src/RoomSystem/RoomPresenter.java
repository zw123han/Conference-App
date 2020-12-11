package RoomSystem;
import EventSystem.EventManager;
import java.util.ArrayList;


public class RoomPresenter  {
    /**
     * A presenter class for the room system.
     *
     * @author Tao, Ziwen
     */
    private RoomManager roomManager;
    private EventManager eventManager;
    private RoomInterface ri;

    /**
     * Constructor for RoomPresenter.
     *
     * @param roomManager A RoomManager instance, which is a use case that stores a list of rooms.
     * @param eventManager An EventManager instance, which stores a list of events.
     */
    public RoomPresenter(RoomManager roomManager, EventManager eventManager){
        this.roomManager = roomManager;
        this.eventManager = eventManager;
    }

    /**
     * Sets the interface of this presenter for use with a GUI.
     *
     * @param ri An interface for the GUI.
     */
    public void setInterface(RoomInterface ri) {
        this.ri = ri;
    }

    /**
     * Creates and gives feedback on room creation.
     *
     * @param roomID The roomID of the room to be created.
     * @param capacity The capacity of the room to be created.
     */
    public void createRoom(String roomID, int capacity) {
        RoomController rc = new RoomController(roomManager);
        String message;
        if (rc.makeRoom(roomID, capacity)) {
            message = "Room created";

        } else {
            message = "Room already exists";
        }
        ri.createPopUp(message);

    }

    /**
     * Deletes and gives feedback on room deletion.
     *
     * @param roomID The roomID of the room to be deleted.
     */
    public void deleteRoom(String roomID) {
        RoomController rc = new RoomController(roomManager);
        String message;
        if (eventManager.roomNotUsed(roomID)) {
            rc.deleteRoom(roomID);
            message = "Room deleted";
        } else  {
            message = "There is an event taking place, cannot delete";
        }
        ri.createPopUp(message);
    }

    /**
     * Returns a list of rooms in string format.
     *
     * @return The list of rooms in string format, with roomID and capacity.
     */
    public ArrayList<String> displayRooms(){
        ArrayList<String> roomList = new ArrayList<String>();
        for(Room room: roomManager.getRooms()){
            roomList.add("ID: "+room.getRoomID() + "\n Capacity: " + room.getCapacity());
        }
        return roomList;
    }

    /**
     * Interface for the GUI.
     */
    public interface RoomInterface {
        void createPopUp(String message);
    }

}
