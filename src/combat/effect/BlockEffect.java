package combat.effect;

import entity.Entity;

import java.util.ArrayList;

public class BlockEffect implements IEffect {
    private Entity caster;
    private int baseAmount;

    public BlockEffect(Entity caster, int amount) {
        this.caster = caster;
        this.baseAmount = amount;
    }

    public void applyEffect(ArrayList<Entity> entities) {
        int block = calculateBlock();
        for (Entity e : entities) {
            e.gainBlock(block);
        }
    }

    private int calculateBlock() {
        if (caster == null) {
            return baseAmount;
        }
        return baseAmount + caster.getStatuses().getDex();
    }

    public String toString() {
        return "blocked " + calculateBlock() + " damage";
    }
}
