package dungeon;

import combat.Card;
import combat.CardLibrary;
import combat.Item;
import entity.player.Player;
import javafx.scene.control.Alert;

public class ShopController {

    private Player player;
    private Card[] cardItems;
    private Item[] potionItems;
    public static final int NUM_BUYABLE_ITEMS = 3;

    public ShopController(Player player) {
        this.player = player;
        cardItems = new Card[NUM_BUYABLE_ITEMS];
        for (int i = 0; i < cardItems.length; i++) {
            cardItems[i] = CardLibrary.getRandomCard();
        }
        potionItems = new Item[NUM_BUYABLE_ITEMS];
        for (int i = 0; i < potionItems.length; i++) {
            potionItems[i] = CardLibrary.getRandomItem();
        }
    }

    public ShopController(Player player, Card[] cardItems, Item[] potionItems) {
        this.player = player;
        this.cardItems = cardItems;
        this.potionItems = potionItems;
    }

    public Item buyItem(Item item) {
        if (player.getGold() >= item.getPrice()) {
            if (player.canAddItem()) {
                player.addItem(item);
                player.addGold(-1 * item.getPrice());
                return item;
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Purchase Error");
                alert.setContentText("You do not have enough space in your inventory.");
                alert.showAndWait();
                return null;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Purchase Error");
            alert.setContentText("You do not have enough gold to purchase this item.");
            alert.showAndWait();
            return null;
        }
    }

    public Card buyCard(Card card) {
        if (player.getGold() >= card.getPrice()) {
            player.getDeck().addCardToDeck(card);
            player.addGold(-1 * card.getPrice());
            return card;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Purchase Error");
            alert.setContentText("You do not have enough gold to purchase this item.");
            alert.showAndWait();
            return null;
        }
    }

    public Card[] getCardItems() {
        return cardItems;
    }

    public Item[] getPotionItems() {
        return potionItems;
    }
}
