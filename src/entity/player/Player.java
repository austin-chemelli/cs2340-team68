package entity.player;

import combat.Item;
import entity.Entity;
import entity.enemy.Enemy;

import java.util.ArrayList;

public class Player extends Entity {

    // stats
    private int mana;
    private int maxMana = 3;
    public static final int NUM_CARDS_DRAW = 5;
    public static final int MAX_ITEMS = 3;
    public static final int BASE_HEALTH = 50;

    // inventory
    private int gold;
    private PlayerDeck deck;
    private ArrayList<Item> items = new ArrayList<>();

    private String startingWeapon; // temporary

    // meta data-y stuff
    private PlayerConfig playerConfig;

    private static Player instance;

    public Player(String name, int difficulty, String startingWeapon) {
        instance = this;
        Enemy.setPlayer(this);
        this.name = name;
        playerConfig = new PlayerConfig();
        playerConfig.setDifficulty(difficulty);
        this.maxHealth = BASE_HEALTH;
        this.health = maxHealth;
        gold = playerConfig.getStartingGold();

        deck = new PlayerDeck();

        this.startingWeapon = startingWeapon; // remove
    }

    public Player(String name, int difficulty, String startingWeapon, int health) {
        this(name, difficulty, startingWeapon);
        this.health = health;
    }

    public void addGold(int amount) {
        gold += amount;
        if (gold <= 0) {
            throw new RuntimeException("Player has a negative amount of gold!");
        }
    }

    public boolean canAddItem(Item item) {
        return items.size() < 3;
    }

    public void addItem(Item item) {
        if (!canAddItem(item)) {
            throw new RuntimeException("Player has too many items!");
        }
        items.add(item);
    }

    public void die() {
        super.die();
        System.out.println("Player died!");
    }

    public void startCombat() {
        statuses.resetStatuses();
        deck.resetPiles();
    }

    public void startRound() {
        mana = maxMana;
        deck.addCardsToHand(NUM_CARDS_DRAW);
    }

    public void endRound() {
        deck.discardHand();
    }

    public int getGold() {
        return gold;
    }

    public int getNumItems() {
        return items.size();
    }

    public Item getItem(int index) {
        if (index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    public Item removeItem(int index) {
        if (index >= items.size()) {
            return null;
        }
        return items.remove(index);
    }

    public String getStartingWeapon() {
        return startingWeapon;
    }

    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }

    public static Player getInstance() {
        return instance;
    }


    public PlayerDeck getDeck() {
        return deck;
    }

}
