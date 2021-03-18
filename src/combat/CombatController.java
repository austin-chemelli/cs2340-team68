package combat;

import entity.enemy.Enemy;
import entity.player.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class CombatController {
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Action> enemyIntents;

    public CombatController(Player player, ArrayList<Enemy> enemies) {
        this.player = player;
        this.enemies = new ArrayList<Enemy>(enemies);
        startRound();
    }

    public void startRound() {
        player.startRound();
        // show new cards in player.startRound()

        enemyIntents = new ArrayList<Action>();
        for (Enemy e : enemies) {
            enemyIntents.add(e.getAction());
        }
    }

    public void endRound() {
        for (Action a : enemyIntents) {
            applyAction(a);
        }
        //startRound(); not sure if call here or some higher level manager
    }

    public void applyAction(Action action) {
        action.applyEffect();

        if (player.getIsDead()) {
            // endCombat(won=false);
        }

        ArrayList<Enemy> newEnemies = new ArrayList<>();
        for (Enemy e : enemies) {
            if (!e.getIsDead()) {
                newEnemies.add(e);
            }
        }
        enemies = newEnemies;

        if (enemies.size() == 0) {
            // endCombat(won=true);
        }
    }


    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Action> getEnemyIntents() {
        return enemyIntents;
    }
}
