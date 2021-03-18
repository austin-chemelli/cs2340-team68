package entity.enemy.behavior;

import combat.Action;
import entity.enemy.Enemy;
import entity.player.Player;

public interface IEnemyBehavior {
    public Action getAction(Enemy self, Player player);
}
