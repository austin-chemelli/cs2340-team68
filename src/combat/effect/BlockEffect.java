package combat.effect;

import entity.Entity;

import java.util.ArrayList;

public class BlockEffect implements IEffect {
    private int amount;

    public BlockEffect(int amount) {
        this.amount = amount;
    }

    public void applyEffect(ArrayList<Entity> enemies) {
        for (Entity e : enemies) {
            e.gainBlock(amount);
        }
    }

    public String toString() {
        return "blocked " + amount + " damage";
    }
}
