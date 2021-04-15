package combat;

import combat.effect.*;
import entity.player.Player;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Hashtable;

public class CardLibrary {
    private static Hashtable<String, Card> cardDict = new Hashtable<>();
    private static ArrayList<Card> cardList = new ArrayList<>();
    private static Hashtable<String, Item> itemDict = new Hashtable<>();
    private static ArrayList<Item> itemList = new ArrayList<>();
    private static Hashtable<String, Weapon> weaponDict = new Hashtable<>();

    private static boolean didInit;

    public CardLibrary() {
        init();
    }

    private void init() {
        if (didInit) {
            return;
        }

        didInit = true;

        initCards();
        initItems();
        initWeapons();
    }

    private static void initCards() {
        String cardName;
        String cardDesc;
        int manaCost;
        IEffect cardEffect;
        Target target;
        Card card;
        int cost;
        Image img;

        //
        //  Attacks
        //

        cardName = "Strike";
        cardDesc = "Deal 6 damage";
        manaCost = 1;
        cardEffect = new DamageEffect(Player.getInstance(), 6);
        target = Target.SINGLE;
        cost = 10;
        img = new Image("./images/StrikeCard.png");
        card = new Card(cardName, cardDesc, manaCost, cardEffect, target, cost, img);
        cardDict.put(cardName, card);
        cardList.add(card);

        cardName = "Swipe";
        cardDesc = "Deal 4 damage to all enemies";
        manaCost = 1;
        cardEffect = new DamageEffect(Player.getInstance(), 4);
        target = Target.ENEMIES;
        cost = 10;
        img = new Image("./images/SwipeCard.png");
        card = new Card(cardName, cardDesc, manaCost, cardEffect, target, cost, img);
        cardDict.put(cardName, card);
        cardList.add(card);

        //
        //  Defense
        //

        cardName = "Defend";
        cardDesc = "Gain 5 block";
        manaCost = 1;
        cardEffect = new BlockEffect(Player.getInstance(), 5);
        target = Target.SELF;
        cost = 10;
        img = new Image("./images/DefendCard.png");
        card = new Card(cardName, cardDesc, manaCost, cardEffect, target, cost, img);
        cardDict.put(cardName, card);
        cardList.add(card);

        //
        //  Utility
        //

        //cardName = "Search";
        //cardDesc = "Draw 2 cards";
        //manaCost = 1;
        //cardEffect = new DrawEffect(2);
        //target = Target.SELF;
        //cardDict.put(cardName, new Card(cardName, cardDesc, manaCost, cardEffect, target));

        cardName = "Inflame";
        cardDesc = "Gain 2 Strength";
        manaCost = 1;
        cardEffect = new GainStrengthEffect(2);
        target = Target.SELF;
        cost = 10;
        img = new Image("./images/InflameCard.png");
        card = new Card(cardName, cardDesc, manaCost, cardEffect, target, cost, img);
        cardDict.put(cardName, card);
        cardList.add(card);

        cardName = "Silver Wind";
        cardDesc = "Gain 2 Dex";
        manaCost = 1;
        cardEffect = new GainDexEffect(2);
        target = Target.SELF;
        cost = 10;
        img = new Image("./images/DexCard.png");
        card = new Card(cardName, cardDesc, manaCost, cardEffect, target, cost, img);
        cardDict.put(cardName, card);
        cardList.add(card);
    }

    private static void initItems() {
        String itemName;
        String itemDesc;
        IEffect itemEffect;
        Target target;
        int cost;
        Item item;
        Image img;

        //
        //  Attacks
        //

        itemName = "Fire Potion";
        itemDesc = "Deal 15 damage to a single enemy";
        itemEffect = new DamageEffect(null, 15);
        target = Target.SINGLE;
        cost = 10;
        img = new Image("./images/FirePotion.png");
        item = new Item(itemName, itemDesc, itemEffect, target, cost, img);
        itemDict.put(itemName, item);
        itemList.add(item);

        itemName = "Explosive Potion";
        itemDesc = "Deal 8 damage to all enemies";
        itemEffect = new DamageEffect(Player.getInstance(), 8);
        target = Target.ENEMIES;
        cost = 10;
        img = new Image("./images/ExplosivePotion.png");
        item = new Item(itemName, itemDesc, itemEffect, target, cost, img);
        itemDict.put(itemName, item);
        itemList.add(item);

        //
        //  Defense
        //

        itemName = "Health Potion";
        itemDesc = "Gain 10 Health";
        itemEffect = new GainHealthEffect(10);
        target = Target.SELF;
        cost = 10;
        img = new Image("./images/HealthPotion.png");
        item = new Item(itemName, itemDesc, itemEffect, target, cost, img);
        itemDict.put(itemName, item);
        itemList.add(item);

        itemName = "Block Potion";
        itemDesc = "Gain 12 block";
        itemEffect = new BlockEffect(Player.getInstance(), 12);
        target = Target.SELF;
        cost = 10;
        img = new Image("./images/BlockPotion.png");
        item = new Item(itemName, itemDesc, itemEffect, target, cost, img);
        itemDict.put(itemName, item);
        itemList.add(item);

        //
        //  Utility
        //

        //cardName = "Swift Potion";
        //cardDesc = "Draw 3 cards";
        //cardEffect = new DrawEffect(3);
        //target = Target.SELF;
        //itemDict.put(cardName, new Item(cardName, cardDesc, cardEffect, target));

        itemName = "Strength Potion";
        itemDesc = "Gain 2 Strength";
        itemEffect = new GainStrengthEffect(2);
        target = Target.SELF;
        cost = 10;
        img = new Image("./images/StrengthPotion.png");
        item = new Item(itemName, itemDesc, itemEffect, target, cost, img);
        itemDict.put(itemName, item);
        itemList.add(item);

        itemName = "Dexterity Potion";
        itemDesc = "Gain 2 Dex";
        itemEffect = new GainDexEffect(2);
        target = Target.SELF;
        cost = 10;
        img = new Image("./images/DexterityPotion.png");
        item = new Item(itemName, itemDesc, itemEffect, target, cost, img);
        itemDict.put(itemName, item);
        itemList.add(item);
    }

    private static void initWeapons() {
        String weaponName;
        int weaponStrength;
        int weaponDex;
        Weapon weapon;

        weaponName = "pistol";
        weaponStrength = 2;
        weaponDex = 4;
        weapon = new Weapon(weaponName, weaponStrength, weaponDex);
        weaponDict.put(weaponName, weapon);

        weaponName = "rifle";
        weaponStrength = 4;
        weaponDex = 2;
        weapon = new Weapon(weaponName, weaponStrength, weaponDex);
        weaponDict.put(weaponName, weapon);

        weaponName = "shotgun";
        weaponStrength = 3;
        weaponDex = 3;
        weapon = new Weapon(weaponName, weaponStrength, weaponDex);
        weaponDict.put(weaponName, weapon);
    }

    public static Card getCard(String name) {
        return cardDict.get(name);
    }

    public static Item getItem(String name) {
        return itemDict.get(name);
    }

    public static Weapon getWeapon(String name) {
        return weaponDict.get(name);
    }

    public static Card getRandomCard() {
        int rand = (int) (Math.random() * cardList.size());
        return cardList.get(rand);
    }

    public static Item getRandomItem() {
        int rand = (int) (Math.random() * itemList.size());
        return itemList.get(rand);
    }
}
