package dungeon;

public class Dungeon {
    private final int DUNGEON_WIDTH = 4;
    private final int DUNGEON_HEIGHT = 4;
    private Room[][] roomGrid = new Room[DUNGEON_HEIGHT][DUNGEON_WIDTH];

    public Dungeon() {
        for (int r = 0; r < DUNGEON_HEIGHT; r++) {
            for (int c = 0; c < DUNGEON_WIDTH; c++) {
                roomGrid[r][c] = new BasicRoom();
            }
        }
    }
}
