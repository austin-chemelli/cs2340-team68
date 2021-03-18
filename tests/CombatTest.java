import combat.Action;
import combat.CombatController;
import combat.effect.*;
import entity.enemy.Enemy;
import entity.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class CombatTest {
    //  TODO
    // test initializing combat
    // test damage effect (single, multiple)
    // test block effect

    private static final int TIMEOUT = 500;
    private Random random;

    @Before
    public void setUp() {
        random = new Random();
        random.setSeed(0);
    }

    @Test(timeout = TIMEOUT)
    public void testInitializeCombat() {
        Player player = new Player("John", 1, "");
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));

        CombatController combatController = new CombatController(player, enemies);

        ArrayList<Action> enemyIntents = combatController.getEnemyIntents();

        assertEquals(enemyIntents.size(), 3);
    }

    @Test(timeout = TIMEOUT)
    public void testDamageEffect() {
        Player player = new Player("John", 1, "");
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));

        CombatController combatController = new CombatController(player, enemies);


        int damageAmount = 5;
        IEffect damageEffect = new DamageEffect(damageAmount);

        // single target
        int origHealth = player.getHealth();

        combatController.applyAction(new Action(player, damageEffect));

        assertEquals(player.getHealth() + damageAmount, origHealth);


        // multi target
        int slimeOrig0 = enemies.get(0).getHealth();
        int slimeOrig1 = enemies.get(1).getHealth();
        int slimeOrig2 = enemies.get(2).getHealth();

        combatController.applyAction(new Action(new ArrayList<>(enemies), damageEffect));

        assertEquals(enemies.get(0).getHealth() + damageAmount, slimeOrig0);
        assertEquals(enemies.get(1).getHealth() + damageAmount, slimeOrig1);
        assertEquals(enemies.get(2).getHealth() + damageAmount, slimeOrig2);
    }

    @Test(timeout = TIMEOUT)
    public void testBlockEffect() {
        Player player = new Player("John", 1, "");
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));

        CombatController combatController = new CombatController(player, enemies);


        int blockAmount = 5;
        IEffect blockEffect = new BlockEffect(blockAmount);

        combatController.applyAction(new Action(player, blockEffect));

        assertEquals(player.getBlock(), blockAmount);


        // multi target
        int slimeOrig0 = enemies.get(0).getHealth();
        int slimeOrig1 = enemies.get(1).getHealth();
        int slimeOrig2 = enemies.get(2).getHealth();

        combatController.applyAction(new Action(new ArrayList<>(enemies), blockEffect));

        assertEquals(enemies.get(0).getBlock(), blockAmount);
        assertEquals(enemies.get(1).getBlock(), blockAmount);
        assertEquals(enemies.get(2).getBlock(), blockAmount);
    }
}
