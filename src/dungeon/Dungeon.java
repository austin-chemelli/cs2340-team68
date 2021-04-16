package dungeon;

import util.Direction;

public class Dungeon {
    // constants
    public static final int DUNGEON_WIDTH = 4; // min 3
    public static final int DUNGEON_HEIGHT = 4; // min 3

    public static final int NUM_SHOPS = 2;
    public static final double CHANCE_EVENT = 0.3f;
    public static final double CHANCE_EMPTY = 0.2f;


    private Room[][] roomGrid = new Room[DUNGEON_HEIGHT][DUNGEON_WIDTH]; // Room[x][y]
    private int posX;
    private int posY;

    public Dungeon() {
        generateDungeon();
        posX = 0;
        posY = 0;
    }

    public Dungeon(Room[][] roomGrid) {
        this.roomGrid = roomGrid;
        posX = 0; // assume start room is bottom left still
        posY = 0;
    }

    public boolean canMove(Direction dir) {
        // check moving to boundaries and null tiles
        switch (dir) {
        case EAST:
            if (posX == roomGrid.length - 1 || roomGrid[posX + 1][posY] == null) {
                return false;
            }
            break;
        case NORTH:
            if (posY == roomGrid[0].length - 1 || roomGrid[posX][posY + 1] == null) {
                return false;
            }
            break;
        case WEST:
            if (posX == 0 || roomGrid[posX - 1][posY] == null) {
                return false;
            }
            break;
        case SOUTH:
            if (posY == 0 || roomGrid[posX][posY - 1] == null) {
                return false;
            }
            break;
        default:
            return true;
        }
        return true;
    }

    public void move(Direction dir) {
        switch (dir) {
        case EAST:
            posX += 1;
            break;
        case NORTH:
            posY += 1;
            break;
        case WEST:
            posX -= 1;
            break;
        case SOUTH:
            posY -= 1;
            break;
        default:
            break;
        }
    }

    public Room getCurrentRoom() {
        return roomGrid[posX][posY];
    }

    public Room[][] getRoomGrid() {
        return roomGrid;
    }


    private void generateDungeon() {
        // ? ? $ B      pick one of $ to be empty, one to be combat
        // ? ? ? $
        // C ? ? ?      in ?, pick numShops to be a shop
        // S C ? ?          remaining filled with combat/event/empty rooms

        int numSpots = DUNGEON_WIDTH * DUNGEON_HEIGHT;

        //  Filling ?
        // C D # #
        // 8 9 A #
        // # 5 6 7
        // # # 2 3

        // shops
        if (NUM_SHOPS >= numSpots - 6) {
            throw new RuntimeException("Too many shops!");
        }
        for (int i = 0; i < NUM_SHOPS; i++) {
            int randSpot = (int) (Math.random() * numSpots);
            while (!generalRoomCanPlace(randSpot)) {
                randSpot = (int) (Math.random() * numSpots);
            }
            // add to grid
            addToGrid(new ShopRoom(), randSpot);
            addToGrid(new ShopRoom(), randSpot);
        }

        // other general rooms
        if (CHANCE_EMPTY + CHANCE_EVENT > 1) {
            throw new RuntimeException("Chances don't add up bruh");
        }
        for (int i = 0; i < numSpots; i++) {
            if (!generalRoomCanPlace(i)) {
                continue;
            }
            double rand = Math.random();
            if (rand <= CHANCE_EVENT) {
                addToGrid(new SafeRoom(), i);
                continue;
            }
            rand -= CHANCE_EVENT;
            if (rand <= CHANCE_EMPTY) {
                // empty is null
                continue;
            } else {
                addToGrid(new CombatRoom(), i);
            }
        }


        // filling start and end parts
        addToGrid(new StartRoom(), 0);
        addToGrid(new CombatRoom(), 1);
        addToGrid(new CombatRoom(), DUNGEON_WIDTH);

        if (Math.random() < 0.5) {
            addToGrid(new CombatRoom(), numSpots - 2);
        } else {
            addToGrid(new CombatRoom(), numSpots - DUNGEON_WIDTH - 1);
        }

        addToGrid(new BossRoom(), numSpots - 1);
    }

    private boolean generalRoomCanPlace(int index) {
        // already placed
        if (roomGrid[index % DUNGEON_WIDTH][index / DUNGEON_WIDTH] != null) {
            return false;
        }

        // start rooms
        if (index == 0 || index == 1 || index == DUNGEON_WIDTH) {
            return false;
        }

        // end rooms
        int numSpots = DUNGEON_WIDTH * DUNGEON_HEIGHT;
        if (index == numSpots - 1 || index == numSpots - 2
                || index == numSpots - DUNGEON_WIDTH - 1) {
            return false;
        }

        return true;
    }

    private void addToGrid(Room room, int x, int y) {
        roomGrid[x][y] = room;
    }

    private void addToGrid(Room room, int index) {
        addToGrid(room, index % DUNGEON_WIDTH, index / DUNGEON_WIDTH);
    }

    public String toString() {
        String ret = "";
        for (int i = DUNGEON_HEIGHT - 1; i >= 0; i--) {
            for (int j = 0; j < DUNGEON_WIDTH; j++) {
                if (roomGrid[j][i] == null) {
                    ret += "NULL ";
                } else {
                    ret += roomGrid[j][i].getRoomType().name() + " ";
                }
            }
            ret += "\n";
        }
        return ret;
    }
}
