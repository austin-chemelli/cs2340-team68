package combat.effect;

import entity.Entity;

import java.util.ArrayList;

public class DamageEffect implements IEffect {
    private Entity caster;
    private int baseAmount;

    public DamageEffect(Entity caster, int amount) {
        this.caster = caster;
        this.baseAmount = amount;
    }

    public void applyEffect(ArrayList<Entity> entities) {
        int damage = calculateDamage();
        for (Entity e : entities) {
            e.takeDamage(damage);
        }
    }

    private int calculateDamage() {
        if (caster == null) {
            return baseAmount;
        }
        return baseAmount + caster.getStatuses().getStrength();
    }

    public String toString() {
        return "has taken " + calculateDamage() + " damage";
    }
}
