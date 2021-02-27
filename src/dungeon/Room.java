package dungeon;

import util.Direction;

// TODO:
//   Add a MoveTo(Direction direction) class
public abstract class Room {
    private Door eastDoor;
    private Door northDoor;
    private Door westDoor;
    private Door southDoor;

    public Room() {
        eastDoor = new Door();
        northDoor = new Door();
        westDoor = new Door();
        southDoor = new Door();
    }

    public void setDoor(Direction direction, Door door) {
        switch (direction) {
            case EAST:
                eastDoor = door;
            case NORTH:
                northDoor = door;;
            case WEST:
                westDoor = door;;
            case SOUTH:
                southDoor = door;;
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
        }
        return null;
    }
}
