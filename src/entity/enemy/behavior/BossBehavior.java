package entity.enemy.behavior;

import combat.Action;
import combat.effect.*;
import entity.enemy.Enemy;
import entity.player.Player;

public class BossBehavior implements IEnemyBehavior {
    public Action getAction(Enemy self, Player player) {
        IEffect effect;
        if (Math.random() < 0.15f) {
            effect = new GainHealthEffect(15);
            return new Action(self, effect);
        } else if (Math.random() >= 0.8) {
            effect = new GainStrengthEffect(1);
            return new Action(self, effect);
        } else {
            effect = new DamageEffect(self, 10);
            return new Action(player, effect);
        }
    }
}
