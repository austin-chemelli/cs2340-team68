import dungeon.basicRoom;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class RoomTest {

    private BasicRoom basicRoom = new BasicRoom();

    @Before
    public void setUp() {

    }

    @Test
    public void testDoorLock() {
        assertEquals(basicRoom.getRoom(Direction.EAST).isLocked(), true);

        assertEquals(basicRoom.getRoom(Direction.NORTH).isLocked(), false);

        assertEquals(basicRoom.getRoom(Direction.WEST).isLocked(), true);

        assertEquals(basicRoom.getRoom(Direction.SOUTH).isLocked(), true);
    }
}
