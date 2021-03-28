package combat;

import entity.enemy.Enemy;
import entity.player.Player;

import java.util.ArrayList;
//import java.util.Arrays;

public class CombatController {
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Action> enemyIntents;
    private boolean combatEnd;

    public CombatController(Player player, ArrayList<Enemy> enemies) {
        this.player = player;
        this.enemies = new ArrayList<Enemy>(enemies);
        combatEnd = false;
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

    public void endRound(Action playerAction) {
        applyAction(playerAction);
        for (Action a : enemyIntents) {
            if (!combatEnd) {
                applyAction(a);
            }
        }
        //startRound(); not sure if call here or some higher level manager
    }

    public void applyAction(Action action) {
        action.applyEffect();

        if (player.getIsDead()) {
            combatEnd = true;
            return;
        }

        ArrayList<Enemy> newEnemies = new ArrayList<>();
        for (Enemy e : enemies) {
            if (!e.getIsDead()) {
                newEnemies.add(e);
            }
        }
        enemies = newEnemies;

        if (enemies.size() == 0) {
            combatEnd = true;
        }
    }


    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Action> getEnemyIntents() {
        return enemyIntents;
    }

    public boolean isCombatEnd() {
        return combatEnd;
    }
}
