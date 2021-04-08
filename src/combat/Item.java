package combat;

import combat.effect.IEffect;

public class Item implements Comparable<Item> {
    private String name;
    private String desc;
    private IEffect effect;
    private Target target;

    public Item(String name, String desc, IEffect effect, Target target) {
        this.name = name;
        this.desc = desc;
        this.effect = effect;
        this.target = target;
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


    @Override
    public int compareTo(Item o) {
        return name.compareTo(o.name);
    }
}
