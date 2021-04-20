package entity.enemy.behavior;

import combat.Action;
import combat.effect.BlockEffect;
import combat.effect.DamageEffect;
import combat.effect.IEffect;
import entity.enemy.Enemy;
import entity.player.Player;

public class BossBehavior implements IEnemyBehavior {
    public Action getAction(Enemy self, Player player) {
        IEffect effect;
        if (Math.random() < 0.5f) {
            effect = new BlockEffect(self, 5);
            return new Action(self, effect);
        } else {
            effect = new DamageEffect(self, 8);
            return new Action(player, effect);
        }
    }
}
