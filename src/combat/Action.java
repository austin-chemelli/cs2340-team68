package combat;

/*import combat.effect.DamageEffect;
import entity.enemy.Enemy;
import javafx.scene.effect.Effect;
 */
import combat.effect.IEffect;
import entity.Entity;


import java.util.ArrayList;
import java.util.Arrays;

public class Action {
    private final ArrayList<Entity> targets;  // usually just one target, list in case AoE effects
    private final IEffect effect;

    public Action(ArrayList<Entity> targets, IEffect effect) {
        this.targets = targets;
        this.effect = effect;
    }

    public Action(Entity target, IEffect effect) {
        this(new ArrayList<Entity>(Arrays.asList(target)), effect);
    }

    public void applyEffect() {
        effect.applyEffect(targets);
    }

    public String toString() {
        String holder = "";
        for (int i = 0; i < targets.size(); i++) {
            Entity target = targets.get(i);
            holder += target.getName();
            if (i != targets.size() - 1) {
                holder += ", ";
            }
        }
        holder += " " + effect.toString();
        return holder;
    }
}
