package entity.player;

import combat.Card;
import combat.CardLibrary;
import entity.Entity;
import entity.enemy.Enemy;

import java.util.ArrayList;

public class Player extends Entity {

    // stats
    private int mana;
    private int maxMana = 3;
    public static final int BASE_HEALTH = 30;

    // inventory
    private int gold;
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;

    private String startingWeapon; // temporary

    // meta data-y stuff
    private PlayerConfig playerConfig;

    public Player(String name, int difficulty, String startingWeapon) {
        Enemy.setPlayer(this);
        this.name = name;
        playerConfig = new PlayerConfig();
        playerConfig.setDifficulty(difficulty);
        maxHealth = BASE_HEALTH;
        health = maxHealth;
        gold = playerConfig.getStartingGold();

        // default deck, this code is super temporary
        deck = new ArrayList<>();
        CardLibrary library = new CardLibrary();
        deck.add(library.get("Strike"));
        deck.add(library.get("Strike"));
        //deck.add(library.get("Strike"));
        //deck.add(library.get("Strike"));
        //deck.add(library.get("Strike"));
        deck.add(library.get("Defend"));
        deck.add(library.get("Defend"));
        //deck.add(library.get("Defend"));
        //deck.add(library.get("Defend"));
        //deck.add(library.get("Defend"));
        deck.add(library.get("Swipe"));
        //deck.add(library.get("Swipe"));
        //deck.add(library.get("Search"));

        this.startingWeapon = startingWeapon;
    }

    public void addGold(int amount) {
        gold += amount;
        if (gold <= 0) {
            throw new RuntimeException("Player has a negative amount of gold!");
        }
    }

    public void die() {
        super.die();
        System.out.println("Player died!");
    }

    public void startRound() {
        mana = maxMana;
        // get new cards
    }

    public int getGold() {
        return gold;
    }

    public String getStartingWeapon() {
        return startingWeapon;
    }

    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
}
