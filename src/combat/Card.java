package combat;

import combat.effect.IEffect;
import javafx.scene.image.Image;

public class Card implements Comparable<Card> {
    private String name;
    private String desc;
    private int energyCost;
    private IEffect effect;
    private Target target;
    private int cost;
    private Image img;

    public Card(String name, String desc, int energyCost, IEffect effect, Image img) {
        this(name, desc, energyCost, effect, Target.SINGLE, 10, img);
    }

    public Card(String name, String desc, int energyCost, IEffect effect,
                Target target, int cost, Image img) {
        this.name = name;
        this.desc = desc;
        this.energyCost = energyCost;
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

    public int getEnergyCost() {
        return energyCost;
    }

    public IEffect getEffect() {
        return effect;
    }

    public Target getTargetType() {
        return target;
    }

    public Image getImg() {
        return img;
    }

    public int getPrice() {
        return cost;
    }

    @Override
    public int compareTo(Card o) {
        return name.compareTo(o.name);
    }
}
