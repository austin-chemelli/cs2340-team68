import dungeon.BasicRoom;

import org.junit.Before;
import org.junit.Test;
import util.Direction;

import static org.junit.Assert.assertEquals;


public class RoomTest {
    BasicRoom basicRoom = new BasicRoom();

    @Before
    public void setUp() {

    }

    @Test
    public void testDoorLock() {
        assertEquals(basicRoom.getDoor(Direction.EAST).isLocked(), true);

        assertEquals(basicRoom.getDoor(Direction.NORTH).isLocked(), false);

        assertEquals(basicRoom.getDoor(Direction.WEST).isLocked(), true);

        assertEquals(basicRoom.getDoor(Direction.SOUTH).isLocked(), true);
    }
}
