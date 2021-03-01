package dungeon;

public class Dungeon {
    private final int dungeonWidth = 4;
    private final int dungeonHeight = 4;
    private Room[][] roomGrid = new Room[dungeonHeight][dungeonWidth];

    public Dungeon() {
        for (int r = 0; r < dungeonHeight; r++) {
            for (int c = 0; c < dungeonWidth; c++) {
                roomGrid[r][c] = new BasicRoom();
            }
        }
    }
}
