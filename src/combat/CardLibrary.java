package combat;

import combat.effect.*;

import java.util.Hashtable;

public class CardLibrary {
    private Hashtable<String, Card> cardDict = new Hashtable<>();

    public CardLibrary() {
        String cardName;
        String cardDesc;
        int manaCost;
        IEffect cardEffect;
        Target target;

        //
        //  Attacks
        //

        cardName = "Strike";
        cardDesc = "Deal 6 damage";
        manaCost = 1;
        cardEffect = new DamageEffect(6);
        target = Target.SINGLE;
        cardDict.put(cardName, new Card(cardName, cardDesc, manaCost, cardEffect, target));

        cardName = "Swipe";
        cardDesc = "Deal 4 damage to all enemies";
        manaCost = 1;
        cardEffect = new DamageEffect(4);
        target = Target.ENEMIES;
        cardDict.put(cardName, new Card(cardName, cardDesc, manaCost, cardEffect, target));

        //
        //  Defense
        //

        cardName = "Defend";
        cardDesc = "Gain 5 block";
        manaCost = 1;
        cardEffect = new BlockEffect(5);
        target = Target.SELF;
        cardDict.put(cardName, new Card(cardName, cardDesc, manaCost, cardEffect, target));

        //
        //  Utility
        //

//        cardName = "Search";
//        cardDesc = "Draw 2 cards";
//        manaCost = 1;
//        cardEffect = new DrawEffect(2);
//        target = Target.SELF;
//        cardDict.put(cardName, new Card(cardName, cardDesc, manaCost, cardEffect, target));
    }

    public Card get(String name) {
        return cardDict.get(name);
    }
}
