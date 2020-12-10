package RoomSystem;

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
    public int getRoomCapacity(String roomId){
        return this.getRoom(roomId).getCapacity();
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

    public boolean deleteRoom(String roomID){
        if(!roomExists(roomID)){
            return false;
        }
        rooms.remove(getRoom(roomID));
        return true;
    }

    }

