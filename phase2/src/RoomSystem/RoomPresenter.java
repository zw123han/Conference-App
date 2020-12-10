package RoomSystem;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomPresenter  {
    private RoomManager roomManager;
    private RoomInterface ri;
    public RoomPresenter(RoomManager roomManager){
        this.roomManager = roomManager;
    }
    public void setInterface(RoomInterface ri) {
        this.ri = ri;
    }
    public void createRoom(String roomID, int capacity) {
        RoomController rc = new RoomController(roomManager);
        if (rc.makeRoom(roomID, capacity)) {
            String message = "Room created";
            ri.createPopUp(message);

        } else {
            String message = "Room already exists";
            ri.createPopUp(message);

        }

    }
    public void deleteRoom(String roomID) {
        RoomController rc = new RoomController(roomManager);
        if (rc.deleteRoom(roomID)) {
            String message = "Room deleted";
            ri.createPopUp(message);
        } else {
            String message = "Room does not exists";
            ri.createPopUp(message);
        }

    }
    public ArrayList<String> displayRooms(){
        ArrayList<String> roomList = new ArrayList<String>();
        for(Room room: roomManager.getRooms()){
            roomList.add("ID: "+room.getRoomID() + "\n Capacity: " + room.getCapacity());
        }
        return roomList;
    }

    public interface RoomInterface {
        void createPopUp(String message);
    }

}
