import combat.Action;
import combat.CombatController;
import combat.effect.*;
import entity.enemy.Enemy;
import entity.player.Player;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class CombatTest extends ApplicationTest {

    private static final int TIMEOUT = 10000;
    private Random random;

    @Before
    public void setUp() {
        random = new Random();
        random.setSeed(0);
    }

    @Test(timeout = TIMEOUT)
    public void testInitializeCombat() {
        Player player = new Player("John", 1, "pistol");
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));

        CombatController combatController = new CombatController(player, enemies);

        ArrayList<Enemy> origEnemies = combatController.getEnemies();
        assertEquals(3, enemies.size());

        ArrayList<Action> enemyIntents = combatController.getEnemyIntents();
        assertEquals(0, enemyIntents.size());
    }

    @Test(timeout = TIMEOUT)
    public void testEnemyRemoved() {
        Player player = new Player("John", 1, "pistol");
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));

        CombatController combatController = new CombatController(player, enemies);

        ArrayList<Enemy> origEnemies = combatController.getEnemies();
        assertEquals(3, origEnemies.size());

        IEffect damageEffect = new DamageEffect(Player.getInstance(), 999999);
        combatController.applyAction(new Action(enemies.get(0), damageEffect));

        ArrayList<Enemy> newEnemies = combatController.getEnemies();
        assertEquals(2, newEnemies.size());

        assertFalse(player.getIsDead());
    }

    @Test(timeout = TIMEOUT)
    public void testDamageEffect() {
        Player player = new Player("John", 1, "pistol");
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));

        CombatController combatController = new CombatController(player, enemies);


        int damageAmount = 5;
        IEffect damageEffect = new DamageEffect(Player.getInstance(), damageAmount);

        // single target
        int origHealth = player.getHealth();

        combatController.applyAction(new Action(player, damageEffect));

        assertEquals(origHealth, player.getHealth() + damageAmount
                + player.getStatuses().getStrength());


        // multi target
        int slimeOrig0 = enemies.get(0).getHealth();
        int slimeOrig1 = enemies.get(1).getHealth();
        int slimeOrig2 = enemies.get(2).getHealth();

        combatController.applyAction(new Action(new ArrayList<>(enemies), damageEffect));

        assertEquals(slimeOrig0, enemies.get(0).getHealth() + damageAmount
                + player.getStatuses().getStrength());
        assertEquals(slimeOrig1, enemies.get(1).getHealth() + damageAmount
                + player.getStatuses().getStrength());
        assertEquals(slimeOrig2, enemies.get(2).getHealth() + damageAmount
                + player.getStatuses().getStrength());
    }

    @Test(timeout = TIMEOUT)
    public void testBlockEffect() {
        Player player = new Player("John", 1, "pistol");
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));

        CombatController combatController = new CombatController(player, enemies);


        int blockAmount = 5;
        IEffect blockEffect = new BlockEffect(Player.getInstance(), blockAmount);

        combatController.applyAction(new Action(player, blockEffect));

        assertEquals(blockAmount, player.getBlock() - player.getStatuses().getDex());


        // multi target
        int slimeOrig0 = enemies.get(0).getHealth();
        int slimeOrig1 = enemies.get(1).getHealth();
        int slimeOrig2 = enemies.get(2).getHealth();

        combatController.applyAction(new Action(new ArrayList<>(enemies), blockEffect));

        assertEquals(blockAmount, enemies.get(0).getBlock() - player.getStatuses().getDex());
        assertEquals(blockAmount, enemies.get(1).getBlock() - player.getStatuses().getDex());
        assertEquals(blockAmount, enemies.get(2).getBlock() - player.getStatuses().getDex());
    }

    @Test(timeout = TIMEOUT)
    public void testPlayerDies() {
        Player player = new Player("John", 1, "pistol", 1);
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));

        CombatController combatController = new CombatController(player, enemies);

        int damageAmount = 5;
        IEffect damageEffect = new DamageEffect(Player.getInstance(), damageAmount);
        combatController.applyAction(new Action(player, damageEffect));

        assertTrue(player.getIsDead());
    }

    @Test(timeout = TIMEOUT)
    public void testEnemyDamaged() {
        Player player = new Player("Player", 1, "pistol", 100);
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy("Slime"));
        CombatController combatController = new CombatController(player, enemies);
        int damageAmount = 5;
        IEffect damageEffect = new DamageEffect(Player.getInstance(), damageAmount);
        combatController.applyAction(new Action(enemies.get(0), damageEffect));
        assertEquals(combatController.getEnemies().get(0).getHealth(), 1);
    }

    @Test(timeout = TIMEOUT)
    public void testPlayerDamaged() {
        Player player = new Player("Player", 1, "pistol", 75);
        player.setMaxHealth(100);
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy("Slime"));
        CombatController combatController = new CombatController(player, enemies);
        int damageAmount = 50;
        IEffect damageEffect = new DamageEffect(Player.getInstance(), damageAmount);
        combatController.applyAction(new Action(combatController.getPlayer(), damageEffect));
        assertEquals(combatController.getPlayer().getHealth()
                + player.getStatuses().getStrength(), 25);
    }

    @Test(timeout = TIMEOUT)
    public void testStrengthEffect() {
        Player player = new Player("John", 1, "pistol");
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));

        CombatController combatController = new CombatController(player, enemies);


        int damageAmount = 5;
        IEffect damageEffect = new DamageEffect(Player.getInstance(), damageAmount);

        // single target
        int origHealth = player.getHealth();

        combatController.applyAction(new Action(player, damageEffect));

        assertEquals(origHealth, player.getHealth() + damageAmount
                + player.getStatuses().getStrength());


        // buff strength
        assertEquals(player.getStatuses().getStrength(), 2);

        int strengthAmount = 5;
        IEffect strengthEffect = new GainStrengthEffect(strengthAmount);
        combatController.applyAction(new Action(player, strengthEffect));

        assertEquals(player.getStatuses().getStrength(), 7);


        // single target with strength
        origHealth = player.getHealth();

        combatController.applyAction(new Action(player, damageEffect));

        assertEquals(origHealth, player.getHealth() + damageAmount
                + player.getStatuses().getStrength());
    }

    @Test(timeout = TIMEOUT)
    public void testDexEffect() {
        Player player = new Player("John", 1, "pistol");
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));
        enemies.add(new Enemy("Slime"));

        CombatController combatController = new CombatController(player, enemies);


        int blockAmount = 5;
        IEffect blockEffect = new BlockEffect(Player.getInstance(), blockAmount);

        // single target
        int origBlock = player.getBlock();

        combatController.applyAction(new Action(player, blockEffect));

        assertEquals(origBlock, player.getBlock() - blockAmount - player.getStatuses().getDex());


        // buff dex
        assertEquals(player.getStatuses().getDex(), 4);

        int dexAmount = 5;
        IEffect dexEffect = new GainDexEffect(dexAmount);
        combatController.applyAction(new Action(player, dexEffect));

        assertEquals(player.getStatuses().getDex(), 9);


        // single target with block
        origBlock = player.getBlock();

        combatController.applyAction(new Action(player, blockEffect));

        assertEquals(origBlock, player.getBlock() - blockAmount - player.getStatuses().getDex());
    }
}
