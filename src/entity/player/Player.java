package entity.player;

import combat.CardLibrary;
import combat.Item;
import combat.Weapon;
import entity.Entity;
import entity.enemy.Enemy;

import java.util.ArrayList;

public class Player extends Entity {

    // stats
    private int mana;
    private int maxMana = 3;
    public static final int NUM_CARDS_DRAW = 5;
    public static final int MAX_ITEMS = 3;
    public static final int BASE_HEALTH = 100;

    // inventory
    private int gold;
    private PlayerDeck deck;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Weapon> weapons = new ArrayList<>();

    private Weapon equippedWeapon;

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

        equippedWeapon = CardLibrary.getWeapon(startingWeapon);
        setEquippedWeapon(equippedWeapon);
        addWeapon(equippedWeapon);
    }

    public Player(String name, int difficulty, String startingWeapon, int health) {
        this(name, difficulty, startingWeapon);
        this.health = health;
    }

    public void addGold(int amount) {
        gold += amount;
        if (gold < 0) {
            throw new RuntimeException("Player has a negative amount of gold!");
        }
    }

    public boolean canAddItem() {
        return items.size() < 3;
    }

    public void addItem(Item item) {
        if (!canAddItem()) {
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
        deck.discardHand();
    }

    public void startRound() {
        System.out.println("Starting Player round!");
        mana = maxMana;
        deck.addCardsToHand(NUM_CARDS_DRAW);
        System.out.println("Player has " + deck.getHand().size() + " cards in hand.");
        System.out.println(deck.handAsString());
    }

    public void endRound() {
        System.out.println("Ending Player round.");
        deck.discardHand();
        System.out.println("Player has " + deck.getHand().size() + " cards in hand.");
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

    public int getNumWeapons() {
        return weapons.size();
    }

    public Weapon getWeapon(int index) {
        if (index >= weapons.size()) {
            return null;
        }
        return weapons.get(index);
    }

    public void addWeapon(Weapon weapon) {
        if (!weapons.contains(weapon)) {
            weapons.add(weapon);
        }
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
        this.getStatuses().setWeaponStrength(equippedWeapon.getStrength());
        this.getStatuses().setWeaponDex(equippedWeapon.getDex());
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
