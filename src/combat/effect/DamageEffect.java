package combat.effect;

import entity.Entity;

import java.util.ArrayList;

public class DamageEffect implements IEffect {
    private int amount;

    public DamageEffect(int amount) {
        this.amount = amount;
    }

    public void applyEffect(ArrayList<Entity> enemies) {
        for (Entity e : enemies) {
            e.takeDamage(amount);
        }
    }
}
