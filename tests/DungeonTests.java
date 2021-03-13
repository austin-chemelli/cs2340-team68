import dungeon.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Timeout;
import util.Direction;

import static org.junit.Assert.*;

public class DungeonTests {
    private static final int TIMEOUT = 500;

    @Before
    public void setUp() {

    }

    @Test(timeout = TIMEOUT)
    public void testDungeonGeneration() {
        Dungeon dungeon = new Dungeon();
        Room[][] roomGrid = dungeon.getRoomGrid();

        assertEquals(roomGrid.length, Dungeon.DUNGEON_HEIGHT);
        assertEquals(roomGrid[0].length, Dungeon.DUNGEON_WIDTH);

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
                switch (roomGrid[x][y].getRoomType()) {
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
                default:
                    break;
                }
            }
        }

        assertEquals(numStart, 1);
        assertEquals(numBoss, 1);
        assertEquals(numShop, Dungeon.NUM_SHOPS);
    }

    @Test(timeout = TIMEOUT)
    public void testMovement() {
        /*
            Co Co Bo
            Co ## Ev
            St Co Sh
         */

        Room[][] rooms = {
                {new StartRoom(), new CombatRoom(), new CombatRoom()},
                {new CombatRoom(), null, new CombatRoom()},
                {new ShopRoom(), new EventRoom(), new BossRoom()}
        };
        Dungeon dungeon = new Dungeon(rooms);

        // At 0, 0
        assertEquals(dungeon.getCurrentRoom().getRoomType(), RoomType.START);
        assertTrue(dungeon.canMove(Direction.EAST));
        assertTrue(dungeon.canMove(Direction.NORTH));
        assertFalse(dungeon.canMove(Direction.WEST));
        assertFalse(dungeon.canMove(Direction.SOUTH));

        // Move to
        dungeon.move(Direction.EAST);

        // At 1, 0
        assertEquals(dungeon.getCurrentRoom().getRoomType(), RoomType.COMBAT);
        assertTrue(dungeon.canMove(Direction.EAST));
        assertFalse(dungeon.canMove(Direction.NORTH));
        assertTrue(dungeon.canMove(Direction.WEST));
        assertFalse(dungeon.canMove(Direction.SOUTH));

        // Move to
        dungeon.move(Direction.EAST);

        // At 2, 0
        assertEquals(dungeon.getCurrentRoom().getRoomType(), RoomType.SHOP);
        assertFalse(dungeon.canMove(Direction.EAST));
        assertTrue(dungeon.canMove(Direction.NORTH));
        assertTrue(dungeon.canMove(Direction.WEST));
        assertFalse(dungeon.canMove(Direction.SOUTH));

        // Move to
        dungeon.move(Direction.NORTH);

        // At 2, 1
        assertEquals(dungeon.getCurrentRoom().getRoomType(), RoomType.EVENT);
        assertFalse(dungeon.canMove(Direction.EAST));
        assertTrue(dungeon.canMove(Direction.NORTH));
        assertFalse(dungeon.canMove(Direction.WEST));
        assertTrue(dungeon.canMove(Direction.SOUTH));

        // Move to
        dungeon.move(Direction.NORTH);

        // At 2, 2
        assertEquals(dungeon.getCurrentRoom().getRoomType(), RoomType.BOSS);
        assertFalse(dungeon.canMove(Direction.EAST));
        assertFalse(dungeon.canMove(Direction.NORTH));
        assertTrue(dungeon.canMove(Direction.WEST));
        assertTrue(dungeon.canMove(Direction.SOUTH));
    }
}