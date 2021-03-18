package combat;

import combat.effect.IEffect;

public class Card {
    private String name;
    private String desc;
    private int energyCost;
    private IEffect effect;
    private Target target;

    public Card(String name, String desc, int energyCost, IEffect effect) {
        this(name, desc, energyCost, effect, Target.SINGLE);
    }

    public Card(String name, String desc, int energyCost, IEffect effect, Target target) {
        this.name = name;
        this.desc = desc;
        this.energyCost = energyCost;
        this.effect = effect;
        this.target = target;
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

    public Target getTarget() {
        return target;
    }
}
