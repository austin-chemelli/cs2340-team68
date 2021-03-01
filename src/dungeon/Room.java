package dungeon;

import util.Direction;

public abstract class Room {
    private Door eastDoor;
    private Door northDoor;
    private Door westDoor;
    private Door southDoor;

    public Room() {
        eastDoor = new Door(Direction.EAST);
        northDoor = new Door(Direction.NORTH, false);
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

}
