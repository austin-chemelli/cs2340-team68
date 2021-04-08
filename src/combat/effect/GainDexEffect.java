package combat.effect;

import entity.Entity;

import java.util.ArrayList;

public class GainDexEffect implements IEffect {
    private int baseAmount;

    public GainDexEffect(int amount) {
        this.baseAmount = amount;
    }

    public void applyEffect(ArrayList<Entity> entities) {
        for (Entity e : entities) {
            e.getStatuses().addDex(baseAmount);
        }
    }

    public String toString() {
        return "gained " + baseAmount + " dex";
    }
}
