package entity.player;

public class PlayerConfig {
    private int difficulty; // 0 - Easy, 1 - Medium, 2 - Hard

    public static final int STARTING_GOLD_EASY = 200;
    public static final int STARTING_GOLD_MEDIUM = 150;
    public static final int STARTING_GOLD_HARD = 100;

    public PlayerConfig() {
        this.difficulty = -1;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }


    public String getDifficultyAsString() {
        switch (difficulty) {
        case 0:
            return "Easy";
        case 1:
            return "Medium";
        case 2:
            return "Hard";
        default:
            return null;
        }
    }

    public int getStartingGold() {
        switch (difficulty) {
        case 0:
            return STARTING_GOLD_EASY;
        case 1:
            return STARTING_GOLD_MEDIUM;
        case 2:
            return STARTING_GOLD_HARD;
        default:
            return 0;
        }
    }
}
