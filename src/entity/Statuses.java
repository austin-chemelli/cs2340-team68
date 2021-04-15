package entity;

public class Statuses {

    private int numStrength;
    private int numDex;
    private int weaponStrength;
    private int weaponDex;

    public Statuses() {

    }

    public void resetStatuses() {
        numStrength = weaponStrength;
        numDex = weaponDex;
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

    public String toString() {
        return "Strength: " + numStrength + " ; Dexterity: " + numDex;
    }

    public int getWeaponStrength() {
        return weaponStrength;
    }

    public void setWeaponStrength(int weaponStrength) {
        addStrength(weaponStrength - getWeaponStrength());
        this.weaponStrength = weaponStrength;
    }

    public int getWeaponDex() {
        return weaponDex;
    }

    public void setWeaponDex(int weaponDex) {
        addDex(weaponDex - getWeaponDex());
        this.weaponDex = weaponDex;
    }
}
