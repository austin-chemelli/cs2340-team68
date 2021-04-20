package dungeon;

import util.Direction;

public abstract class Room {
    private RoomType roomType = null;

    private Door eastDoor;
    private Door northDoor;
    private Door westDoor;
    private Door southDoor;

    public Room() {
        eastDoor = new Door(Direction.EAST);
        northDoor = new Door(Direction.NORTH);
        westDoor = new Door(Direction.WEST);
        southDoor = new Door(Direction.SOUTH);
    }

    public void setDoor(Direction direction, Door door) {
        switch (direction) {
        case EAST:
            eastDoor = door;
            break;
        case NORTH:
            northDoor = door;
            break;
        case WEST:
            westDoor = door;
            break;
        default:
            southDoor = door;
            break;
        }
    }

    public Door getDoor(Direction direction) {
        switch (direction) {
        case EAST:
            return eastDoor;
        case NORTH:
            return northDoor;
        case WEST:
            return westDoor;
        case SOUTH:
            return southDoor;
        default:
            return null;
        }
    }

    public void unlockDoor(Door door) {
        door.setLocked(false);
    }

    public void lockDoor(Door door) {
        door.setLocked(true);
    }
    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String toString() {
        return "Currently in a " + getRoomType().toString().toLowerCase() + " room";
    }
}
