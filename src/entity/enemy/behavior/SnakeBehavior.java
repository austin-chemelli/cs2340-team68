package entity.enemy.behavior;

import combat.Action;
import combat.effect.DamageEffect;
import combat.effect.IEffect;
import entity.enemy.Enemy;
import entity.player.Player;

public class SnakeBehavior implements IEnemyBehavior {
    public Action getAction(Enemy self, Player player) {
        IEffect effect;
        if (Math.random() < 0.75f) {
            effect = new DamageEffect(self, 8);
            return new Action(player, effect);
        } else {
            effect = new DamageEffect(self, 5);
            return new Action(player, effect);
        }
    }

}
