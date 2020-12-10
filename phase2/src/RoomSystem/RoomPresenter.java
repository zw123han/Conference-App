package RoomSystem;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomPresenter {
    private RoomManager roomManager;

    public RoomPresenter(RoomManager roomManager){
        this.roomManager = roomManager;
    }

    public ArrayList<String> displayRooms(){
        ArrayList<String> roomList = new ArrayList<String>();
        for(Room room: roomManager.getRooms()){
            roomList.add("ID: "+room.getRoomID() + "\n Capacity: " + room.getCapacity());
        }
        return roomList;
    }
}
