package entity.enemy;

import combat.Action;
import entity.Entity;
import entity.enemy.behavior.*;

public class Enemy extends Entity {
    private final IEnemyBehavior behavior;

    // ***************************************
    // to-do: figure out how to store entity.enemy data. maybe a json or classes
    // ***************************************
    public Enemy(String type) {
        if (type.equals("Slime")) {
            maxHealth = 10;
            name = type;
            health = maxHealth;
            behavior = new SlimeBehavior();
        } else {
            throw new RuntimeException("Enemy doesn't exist!");
        }
    }

    public void die() {
        super.die();
        System.out.println(name + " died! " + getIsDead());
    }

    public Action getAction() {
        return behavior.getAction(this, null );
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }
}
