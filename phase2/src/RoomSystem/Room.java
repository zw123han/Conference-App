package RoomSystem;

public class Room {
    /**
     * Room is an entity which represents a room at this conference.
     *
     * @author Ziwen
     */
    public int capacity;
    public String roomID;

    /**
     * Constructor for the room class.
     *
     * @param roomID The name or ID of the room.
     * @param capacity The capacity of the room.
     */
    public Room(String roomID, int capacity){
        this.roomID = roomID;
        this.capacity = capacity;
    }

    /**
     * Getter for the capacity of this room.
     *
     * @return The capacity of this room.
     */
    public int getCapacity(){
        return this.capacity;
    }

    /**
     * Getter for the name/ID of this room.
     *
     * @return The name of this room.
     */
    public String getRoomID(){
        return this.roomID;
    }
}
