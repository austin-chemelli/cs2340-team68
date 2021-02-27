package dungeon;

public class Door {
    private boolean isLocked;

    public Door() {
        isLocked = true;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
