import player.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTests {
    private static final int TIMEOUT = 500;

    @Before
    public void setUp() {

    }


    @Test(timeout = TIMEOUT)
    public void testInstanceFields() {
        String name = "Johnny Test";
        String weapon = "Testistious";

        PlayerData playerEasy = new PlayerData(name, 0, weapon);

        assertEquals(playerEasy.getName(), name);
        assertEquals(playerEasy.getStartingWeapon(), weapon);
        assertEquals(playerEasy.getHealth(), PlayerData.BASE_HEALTH);

        assertEquals(playerEasy.getGold(), PlayerConfig.STARTING_GOLD_EASY);


        PlayerData playerMedium = new PlayerData(name, 1, weapon);

        assertEquals(playerMedium.getGold(), PlayerConfig.STARTING_GOLD_MEDIUM);


        PlayerData playerHard = new PlayerData(name, 2, weapon);

        assertEquals(playerHard.getGold(), PlayerConfig.STARTING_GOLD_HARD);
    }

    @Test(timeout = TIMEOUT)
    public void testAddingHealth() {
        PlayerData player = new PlayerData("Test Name", 0, "Test Weapon");

        int playerMaxHealth = PlayerData.BASE_HEALTH;

        assertEquals(player.getHealth(), playerMaxHealth);
        assertEquals(player.getMaxHealth(), playerMaxHealth);

        // Losing health
        player.addHealth(-1);

        assertEquals(player.getHealth(), playerMaxHealth - 1);
        assertEquals(player.getMaxHealth(), playerMaxHealth);

        // Gaining health
        player.addHealth(1);

        assertEquals(player.getHealth(), playerMaxHealth);
        assertEquals(player.getMaxHealth(), playerMaxHealth);

        // Going over limit
        player.addHealth(1);

        assertEquals(player.getHealth(), playerMaxHealth);
        assertEquals(player.getMaxHealth(), playerMaxHealth);

        // player.addHealth(1);

        // assertEquals(player.getHealth(), playerMaxHealth);
        // assertEquals(player.getMaxHealth(), playerMaxHealth);
    }

    @Test(timeout = TIMEOUT, expected = RuntimeException.class)
    public void testAddingGold() {
        PlayerData player = new PlayerData("Test Name", 0, "Test Weapon");

        int startingGold = PlayerConfig.STARTING_GOLD_EASY;

        assertEquals(player.getGold(), startingGold);

        // Adding gold
        player.addGold(10);

        assertEquals(player.getGold(), startingGold + 10);

        // Subtracting gold
        player.addGold(-10);

        assertEquals(player.getGold(), startingGold);

        // Negative gold
        //   Should throw RuntimeException
        player.addGold(-1000000);
    }
}
