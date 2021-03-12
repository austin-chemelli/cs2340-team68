package dungeon;

import util.Direction;

public class Dungeon {
    // constants
    public static final int dungeonWidth = 4; // min 3
    public static final int dungeonHeight = 4; // min 3

    public static int numShops = 2;
    public static double chanceEvent = 0.3f;
    public static double chanceEmpty = 0.2f;


    private Room[][] roomGrid = new Room[dungeonHeight][dungeonWidth]; // Room[x][y]
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

    public boolean CanMove(Direction dir) {
        // check moving to boundaries and null tiles
        switch (dir) {
            case EAST:
                if (posX == dungeonWidth - 1 || roomGrid[posX + 1][posY] == null)
                    return false;
                break;
            case NORTH:
                if (posY == dungeonHeight - 1 || roomGrid[posX][posY + 1] == null)
                    return false;
                break;
            case WEST:
                if (posX == 0 || roomGrid[posX - 1][posY] == null)
                    return false;
                break;
            case SOUTH:
                if (posY == 0 || roomGrid[posX][posY - 1] == null)
                    return false;
                break;
        }
        return true;
    }

    public void Move(Direction dir) {
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
        }
    }

    public Room GetCurrentRoom() {
        return roomGrid[posX][posY];
    }

    public Room[][] GetRoomGrid() {
        return roomGrid;
    }


    private void generateDungeon() {
        // ? ? $ B      pick one of $ to be empty, one to be combat
        // ? ? ? $
        // C ? ? ?      in ?, pick numShops to be a shop
        // S C ? ?          remaining filled with combat/event/empty rooms

        int numSpots = dungeonWidth * dungeonHeight;

        //  Filling ?
        // C D # #
        // 8 9 A #
        // # 5 6 7
        // # # 2 3

        // shops
        if (numShops >= numSpots - 6) {
            throw new RuntimeException("Too many shops!");
        }
        for (int i = 0; i < numShops; i++) {
            int randSpot = (int)(Math.random() * numSpots);
            while (!generalRoomCanPlace(randSpot))
                randSpot = (int)(Math.random() * numSpots);
            // add to grid
            addToGrid(new ShopRoom(), randSpot);
            addToGrid(new ShopRoom(), randSpot);
        }

        // other general rooms
        if (chanceEmpty + chanceEvent > 1) {
            throw new RuntimeException("Chances don't add up bruh");
        }
        for (int i = 0; i < numSpots; i++) {
            if (!generalRoomCanPlace(i)) {
                continue;
            }
            double rand = Math.random();
            if (rand <= chanceEvent) {
                addToGrid(new EventRoom(), i);
                continue;
            }
            rand -= chanceEvent;
            if (rand <= chanceEmpty) {
                // empty is null
                continue;
            }
            else {
                addToGrid(new CombatRoom(), i);
            }
        }


        // filling start and end parts
        addToGrid(new StartRoom(), 0);
        addToGrid(new CombatRoom(), 1);
        addToGrid(new CombatRoom(), dungeonWidth);

        if (Math.random() < 0.5) {
            addToGrid(new CombatRoom(), numSpots - 2);
        }
        else {
            addToGrid(new CombatRoom(), numSpots - dungeonWidth - 1);
        }

        addToGrid(new BossRoom(), numSpots - 1);
    }

    private boolean generalRoomCanPlace(int index) {
        // already placed
        if (roomGrid[index % dungeonWidth][index / dungeonWidth] != null)
            return false;

        // start rooms
        if (index == 0 || index == 1 || index == dungeonWidth)
            return false;

        // end rooms
        int numSpots = dungeonWidth * dungeonHeight;
        if (index == numSpots - 1 || index == numSpots - 2 || index == numSpots - dungeonWidth - 1)
            return false;

        return true;
    }

    private void addToGrid(Room room, int x, int y) {
        roomGrid[x][y] = room;
    }

    private void addToGrid(Room room, int index) {
        addToGrid(room, index % dungeonWidth, index / dungeonWidth);
    }
}
