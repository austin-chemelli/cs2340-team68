package player;

public class PlayerData {

    // meta data-y stuff
    private String name;
    private PlayerConfig playerConfig;

    // stats
    private int health;
    private int maxHealth;
    public static final int BASE_HEALTH = 10;

    // inventory
    private int gold;
    private String startingWeapon; // temporary

    public PlayerData(String name, int difficulty, String startingWeapon) {
        this.name = name;
        playerConfig = new PlayerConfig();
        playerConfig.setDifficulty(difficulty);
        maxHealth = BASE_HEALTH;
        health = maxHealth;
        gold = playerConfig.getStartingGold();
        this.startingWeapon = startingWeapon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getGold() {
        return gold;
    }

    public String getStartingWeapon() {
        return startingWeapon;
    }

    public void setStartingWeapon(String startingWeapon) {
        this.startingWeapon = startingWeapon;
    }



    public void addHealth(int amount) {
        health += amount;

        if (health > maxHealth) {
            health = maxHealth;
        } else if (health <= 0) {
            System.out.println("I'm dead!");
            // Call something
        }
    }

    public void addGold(int amount) {
        gold += amount;
        if (gold <= 0) {
            throw new RuntimeException("Player has a negative amount of gold!");
        }
    }
}
