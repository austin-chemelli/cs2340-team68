package entity;

public class Statuses {

    private int numStrength;
    private int numDex;

    public Statuses() {

    }

    public void resetStatuses() {
        numStrength = 0;
        numDex = 0;
    }

    public void addStrength(int dif) {
        numStrength += dif;
    }

    public int getStrength() {
        return numStrength;
    }

    public void addDex(int dif) {
        numDex += dif;
    }

    public int getDex() {
        return numDex;
    }
}
