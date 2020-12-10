package RoomSystem;

public class Room {
    public int capacity;
    public String roomID;

    public Room(String roomID, int capacity){
        this.roomID = roomID;
        this.capacity = capacity;
    }
    public int getCapacity(){
        return this.capacity;
    }
    public String getRoomID(){
        return this.roomID;
    }
    public void setCapacity(int newCapacity){
        this.capacity = newCapacity;
    }
}
