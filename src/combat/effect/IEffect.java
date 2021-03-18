package combat.effect;

import entity.Entity;

import java.util.ArrayList;

public interface IEffect {
    public void applyEffect(ArrayList<Entity> enemies);
}
