package dungeon;

import javafx.scene.control.Alert;
import util.Item;

public class ShopRoom extends Room {
    private Item[] shopItems;

    public ShopRoom() {
        super();
        roomType = RoomType.SHOP;
        shopItems = null;
    }

    public void requestShopItems() {
        //implement later
    }

    public Item buyItem(Item item, int currentGold) {
        if (currentGold >= item.getPrice()) {
            //implement removing item from shop
            //implement deducting gold from player after purchase successful
            return item;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Purchase Error");
            alert.setContentText("You do not have enough gold to purchase this item.");
            alert.showAndWait();
            return null;
        }
    }
}
