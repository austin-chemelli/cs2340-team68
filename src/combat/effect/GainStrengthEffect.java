package combat.effect;

import entity.Entity;

import java.util.ArrayList;

public class GainStrengthEffect implements IEffect {
    private int baseAmount;

    public GainStrengthEffect(int amount) {
        this.baseAmount = amount;
    }

    public void applyEffect(ArrayList<Entity> entities) {
        for (Entity e : entities) {
            e.getStatuses().addStrength(baseAmount);
        }
    }

    public String toString() {
        return "gained " + baseAmount + " strength";
    }
}
