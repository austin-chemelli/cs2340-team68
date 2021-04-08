package entity.player;

import combat.Card;
import combat.CardLibrary;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerDeck {
    private ArrayList<Card> deck;

    private ArrayList<Card> drawPile;
    private ArrayList<Card> discardPile;
    private ArrayList<Card> hand;

    public PlayerDeck() {

        // default deck, this code is super temporary
        deck = new ArrayList<Card>();
        CardLibrary library = new CardLibrary();
        deck.add(library.getCard("Strike"));
        deck.add(library.getCard("Strike"));
        //deck.add(library.get("Strike"));
        //deck.add(library.get("Strike"));
        //deck.add(library.get("Strike"));
        deck.add(library.getCard("Defend"));
        deck.add(library.getCard("Defend"));
        //deck.add(library.get("Defend"));
        //deck.add(library.get("Defend"));
        //deck.add(library.get("Defend"));
        deck.add(library.getCard("Swipe"));
        //deck.add(library.get("Swipe"));
        //deck.add(library.get("Search"));

        drawPile = new ArrayList<Card>();
        discardPile = new ArrayList<Card>();
        hand = new ArrayList<Card>();
    }

    // === Deck Building ===

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void addCardToDeck(Card card) {
        deck.add(card);
    }

    public Card removeCardFromDeck(int index) {
        return hand.remove(index);
    }

    public boolean removeCardFromDeck(Card card) {
        return deck.remove(card);
    }

    // === Combat ===

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Card getCardFromHand(int index) {
        if (index >= hand.size()) {
            return null;
        }
        return hand.get(index);
    }

    public Card removeCardFromHand(int index) {
        return hand.remove(index);
    }

    public boolean removeCardFromHand(Card card) {
        return hand.remove(card);
    }

    public void resetPiles() {
        //System.out.println("reseting piles");
        drawPile.clear();
        discardPile.clear();
        drawPile.addAll(deck);

        Collections.shuffle(drawPile);
        //System.out.println(" Deck size: " + deck.size() + " Draw size: " +
        //        drawPile.size() + " Discard size: " + discardPile.size());
    }

    public void addCardsToHand(int num) {
        for (int i = 0; i < num; i++) {
            Card card = drawCard();
            if (card != null) {
                //System.out.println("Added " + card.getName() + " to hand!");
                hand.add(card);
            }
            //else {
            //    //System.out.println("Didn't draw anything...");
            //}
        }
    }

    public void discardHand() {
        while (hand.size() > 0) {
            discardPile.add(hand.remove(0));
        }
    }


    private Card drawCard() {
        if (drawPile.size() == 0) {
            shuffleDiscard();
            if (drawPile.size() == 0) {
                return null;
            }
        }

        return drawPile.remove(0);
    }

    private void shuffleDiscard() {
        drawPile.addAll(discardPile);
        discardPile.clear();

        Collections.shuffle(drawPile);
    }
}
