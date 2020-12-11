package RoomSystem;

import DatabaseSystem.*;

import java.util.ArrayList;

/**
 * A use case which stores a list of rooms at this conference.
 *
 * @author Jesse, Ziwen
 */
public class RoomManager implements Savable {
    private ArrayList<Room> rooms;

    /**
     * Constructor for RoomManager.
     *
     * @param rooms An arraylist of rooms at this conference.
     */
    public RoomManager(ArrayList<Room> rooms){
        this.rooms = rooms;
    }

    /**
     * No args-constructor for RoomManger, for database use.
     */
    public RoomManager() {
        this.rooms = new ArrayList<Room>();
    }

    /**
     * Gets the collection name of this object.
     *
     * @return "rooms"
     */
    @Override
    public String getCollectionName() {
        return "rooms";
    }

    /**
     * Gets the conversion strategy for this object.
     *
     * @return An instance of RoomManagerConverter
     */
    @Override
    public ConversionStrategy getConversionStrategy() {
        return new RoomManagerConverter();
    }

    /**
     * Gets the parser strategy for this object.
     *
     * @return An instance of ParseToRoomManager
     */
    @Override
    public ParserStrategy getDocumentParserStrategy() {
        return new ParseToRoomManager();
    }

    /**
     * Gets the list of rooms at this conference.
     *
     * @return The list of rooms.
     */
    public ArrayList<Room> getRooms(){
        return this.rooms;
    }

    /**
     * Gets a room at this conference.
     *
     * @param roomID The roomID of the room.
     * @return A room with roomID, or null if there is none.
     */
    public Room getRoom(String roomID){
        for(Room room: this.rooms){
            if(room.getRoomID().equals(roomID)){
                return room;
            }
        }
        return null;
    }

    /**
     * Gets the capacity of a room at this conference.
     *
     * @param roomId The roomID of the room.
     * @return The capacity of the room with roomID. Returns 0 if no such room exists.
     */
    public int getRoomCapacity(String roomId){
        if(this.getRoom(roomId)==null){
            return 0;
        }
        return this.getRoom(roomId).getCapacity();
    }

    /**
     * Checks if a room exists at this conference.
     *
     * @param roomID The roomID of the room to check/
     * @return True if and only if a room with roomID exists at this conference.
     */
    public boolean roomExists(String roomID){
        return getRoom(roomID) != null;}

    /**
     * Makes a room and adds it to this conference.
     *
     * @param roomID The ID of the new room to be created.
     * @param capacity The capacity of the new room to be created. Must be a non-negative integer.
     * @return True if and only if a room with roomID and capacity was created.
     */
    public boolean makeRoom(String roomID, int capacity){
        if(roomExists(roomID)|capacity<0){
            return false;
        }
        rooms.add(new Room(roomID, capacity));
        return true;
    }

    /**
     * Deletes a room with roomID from this conference.
     *
     * @param roomID The roomID to be deleted.
     */
    public void deleteRoom(String roomID){
        rooms.remove(getRoom(roomID));

    }

    }

