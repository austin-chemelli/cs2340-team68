package combat;

import combat.effect.IEffect;
import javafx.scene.image.Image;

public class Item implements Comparable<Item> {
    private String name;
    private String desc;
    private IEffect effect;
    private Target target;
    private int cost;
    private Image img;

    public Item(String name, String desc, IEffect effect, Target target, int cost, Image img) {
        this.name = name;
        this.desc = desc;
        this.effect = effect;
        this.target = target;
        this.cost = cost;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public IEffect getEffect() {
        return effect;
    }

    public Target getTargetType() {
        return target;
    }

    public int getPrice() {
        return cost;
    }

    public Image getImg() {
        return img;
    }

    @Override
    public int compareTo(Item o) {
        return name.compareTo(o.name);
    }
}
