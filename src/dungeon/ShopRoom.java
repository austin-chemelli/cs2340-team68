package dungeon;

public class ShopRoom extends Room {

    private ShopController controller = null;

    public ShopRoom() {
        super();
        setRoomType(RoomType.SHOP);
    }

    public ShopController getController() {
        return controller;
    }

    public void setController(ShopController controller) {
        this.controller = controller;
    }
}
