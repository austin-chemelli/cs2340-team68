package combat;

public class Weapon {
    private String name;
    private int strength;
    private int dex;

    public Weapon(String name, int strength, int dex) {
        this.name = name;
        this.strength = strength;
        this.dex = dex;
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public int getDex() {
        return dex;
    }
}
