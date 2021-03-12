package util;

public class Item {
    private int price;
    private String type; //change this to enum??
    private boolean playerOwns;

    public Item(int price, String type, boolean playerOwns) {
        this.price = price;
        this.type = type;
        this.playerOwns = playerOwns;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean playerOwns() {
        return playerOwns;
    }

    public void setPlayerOwns(boolean playerOwns) {
        this.playerOwns = playerOwns;
    }
}
