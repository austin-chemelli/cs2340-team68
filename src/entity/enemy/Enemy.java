package entity.enemy;

import combat.Action;
import entity.Entity;
import entity.enemy.behavior.*;
import entity.player.Player;

public class Enemy extends Entity {
    private final IEnemyBehavior behavior;
    private static Player player;
    // ***************************************
    // to-do: figure out how to store entity.enemy data. maybe a json or classes
    // ***************************************
    public Enemy(String type) {
        if (type.equals("Slime")) {
            maxHealth = 8;
            name = type;
            health = maxHealth;
            behavior = new SlimeBehavior();
        } else if (type.equals("Goblin")) {
            maxHealth = 12;
            name = type;
            health = maxHealth;
            behavior = new GoblinBehavior();
        } else if (type.equals("Snake")) {
            maxHealth = 10;
            name = type;
            health = maxHealth;
            behavior = new SnakeBehavior();
        } else {
            throw new RuntimeException("Enemy doesn't exist!");
        }
    }

    public void die() {
        super.die();
        System.out.println(name + " died! " + getIsDead());
    }

    public Action getAction() {
        return behavior.getAction(this, player);
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public static void setPlayer(Player player1) {
        player = player1;
    }
}
