package RoomSystem;

import EventSystem.Event;
import EventSystem.EventManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class RoomPresenter  {
    private RoomManager roomManager;
    private EventManager eventManager;
    private RoomInterface ri;
    public RoomPresenter(RoomManager roomManager, EventManager eventManager){
        this.roomManager = roomManager;
        this.eventManager = eventManager;
    }
    public void setInterface(RoomInterface ri) {
        this.ri = ri;
    }
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
