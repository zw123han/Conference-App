package RoomSystem;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RoomManager {
    private ArrayList<Room> rooms;

    public RoomManager(ArrayList<Room> rooms){
        this.rooms = rooms;
    }

    public ArrayList<Room> getRooms(){
        return this.rooms;
    }

    public Room getRoom(String roomID){
        for(Room room: this.rooms){
            if(room.getRoomID().equals(roomID)){
                return room;
            }
        }
        return null;
    }
    public boolean roomExists(String roomID){
        return getRoom(roomID) != null;}

    public boolean makeRoom(String roomID, int capacity){
        if(roomExists(roomID)){
            return false;
        }
        rooms.add(new Room(roomID, capacity));
        return true;
    }

    public boolean changeCapacity(String roomID, int newCapacity){
        if(!roomExists(roomID)){
            return false;
        }
        getRoom(roomID).setCapacity(newCapacity);
        return true;
    }
    }

