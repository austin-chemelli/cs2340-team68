package entity;

import entity.player.Player;

public abstract class Entity {
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int block;
    protected boolean isDead;

    protected Statuses statuses;

    public Entity() {
        statuses = new Statuses();
    }

    public void takeDamage(int amount) {
        if (block > 0) {
            if (block > amount) {
                if (this instanceof Player) {
                    ((Player) this).addDamageBlocked(amount);
                }
                block -= amount;
            } else {
                if (this instanceof Player) {
                    ((Player) this).addDamageBlocked(block);
                }
                gainHealth(-amount + block);
                block = 0;
            }
            return;
        }
        gainHealth(-amount);
    }

    public void gainHealth(int amount) {
        if (this instanceof Player && amount < 0) {
            ((Player) this).addDamageTaken(-amount);
        }
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        } else if (health <= 0) {
            die();
        }
    }

    public void die() {
        isDead = true;
    }

    public void gainBlock(int amount) {
        block += amount;
    }

    public void resetBlock() {
        block = 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getBlock() {
        return block;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public Statuses getStatuses() {
        return statuses;
    }
}
