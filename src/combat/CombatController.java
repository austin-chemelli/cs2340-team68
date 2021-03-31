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

    public String endRound(Action playerAction) {
        String actionLog = "";
        actionLog += applyAction(playerAction) + "\n";
        int counter = 1;
        for (Action a : enemyIntents) {
            if (!combatEnd) {
                actionLog += applyAction(a) + "\n";
                counter++;
            }
        }
        return actionLog;
    }

    public String applyAction(Action action) {
        action.applyEffect();
        String deadEntities = "";
        Boolean enemyDied = false;

        if (player.getIsDead()) {
            combatEnd = true;
            return action.toString() + "\n" + player.getName() + " has died";
        }

        ArrayList<Enemy> newEnemies = new ArrayList<>();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if (!e.getIsDead()) {
                newEnemies.add(e);
            } else {
                enemyDied = true;
                deadEntities += e.getName();
                if (newEnemies.size() != i) {
                    deadEntities += ", ";
                }
            }
        }
        enemies = newEnemies;

        if (enemies.size() == 0) {
            combatEnd = true;
            return action.toString() + "\n" + "All enemies are dead!";
        } else {
            if (enemyDied) {
                return action.toString() + "\n" + deadEntities + " has died";
            } else {
                return action.toString();
            }

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
