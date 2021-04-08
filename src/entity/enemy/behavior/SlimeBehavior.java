package entity.enemy.behavior;

import combat.Action;
import combat.effect.DamageEffect;
import combat.effect.IEffect;
import entity.enemy.Enemy;
import entity.player.Player;

public class SlimeBehavior implements IEnemyBehavior {
    public Action getAction(Enemy self, Player player) {
        IEffect effect;
        /*if (Math.random() < 0.5f) {
            // random between 5 and 7
            int rand = (int) (Math.random() * 3) + 5;
            effect = new DamageEffect(rand);
            return new Action(player, effect);
        } else {
            // random between 4 and 6
            int rand = (int) (Math.random() * 3) + 4;
            effect = new BlockEffect(rand);
            return new Action(self, effect);
        }*/
        int rand = (int) (Math.random() * 3) + 5;
        effect = new DamageEffect(self, rand);
        return new Action(player, effect);
    }
}
