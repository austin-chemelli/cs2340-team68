package entity.enemy.behavior;

import combat.Action;
import combat.effect.DamageEffect;
import combat.effect.IEffect;
import entity.enemy.Enemy;
import entity.player.Player;

public class GoblinBehavior implements IEnemyBehavior {
    public Action getAction(Enemy self, Player player) {
        IEffect effect;
        if (Math.random() < 0.5f) {
            effect = new DamageEffect(3);
            return new Action(player, effect);
        } else {
            effect = new DamageEffect(3);
            return new Action(self, effect);
        }
    }
}
