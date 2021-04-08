package combat.effect;

import entity.Entity;

import java.util.ArrayList;

public class GainHealthEffect implements IEffect {
    private Entity caster;
    private int baseAmount;

    public GainHealthEffect(int amount) {
        this.baseAmount = amount;
    }

    public void applyEffect(ArrayList<Entity> entities) {
        for (Entity e : entities) {
            e.gainHealth(baseAmount);
        }
    }

    public String toString() {
        return "gained " + baseAmount + " health";
    }
}
