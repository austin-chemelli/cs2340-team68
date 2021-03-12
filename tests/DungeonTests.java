import dungeon.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DungeonTests {
    private static final int TIMEOUT = 5000;

    @Before
    public void setUp() {

    }

    @Test()
    public void testDungeonGeneration() {
        Dungeon dungeon = new Dungeon();
        Room[][] roomGrid = dungeon.GetRoomGrid();

        assertEquals(roomGrid.length, Dungeon.dungeonHeight);
        assertEquals(roomGrid[0].length, Dungeon.dungeonWidth);

        // checking did room types
        int numStart = 0;
        int numCombat = 0;
        int numEvent = 0;
        int numShop = 0;
        int numBoss = 0;
        int numNull = 0;
        for (int x = 0; x < roomGrid.length; x++) {
            for (int y = 0; y < roomGrid[x].length; y++) {
                if (roomGrid[x][y] == null) {
                    numNull += 1;
                    continue;
                }
                switch (roomGrid[x][y].roomType) {
                    case START:
                        numStart += 1;
                        break;
                    case COMBAT:
                        numCombat += 1;
                        break;
                    case EVENT:
                        numEvent += 1;
                        break;
                    case SHOP:
                        numShop += 1;
                        break;
                    case BOSS:
                        numBoss += 1;
                        break;
                }
            }
        }

        assertEquals(numStart, 1);
        assertEquals(numBoss, 1);
        assertEquals(numShop, Dungeon.numShops);

    }
}