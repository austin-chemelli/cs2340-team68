import combat.CardLibrary;
import entity.player.*;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PlayerTests extends ApplicationTest {
    //  TODO
    // test block
    // change weapon test to starting deck test

    private static final int TIMEOUT = 500;

    @Before
    public void setUp() {

    }


    @Test(timeout = TIMEOUT)
    public void testInstanceFields() {
        String name = "Johnny Test";
        String weapon = "pistol";

        Player playerEasy = new Player(name, 0, weapon);

        assertEquals(playerEasy.getName(), name);
        assertEquals(playerEasy.getEquippedWeapon().getName(), weapon);
        assertEquals(playerEasy.getHealth(), Player.BASE_HEALTH);

        assertEquals(playerEasy.getGold(), PlayerConfig.STARTING_GOLD_EASY);


        Player playerMedium = new Player(name, 1, weapon);

        assertEquals(playerMedium.getGold(), PlayerConfig.STARTING_GOLD_MEDIUM);


        Player playerHard = new Player(name, 2, weapon);

        assertEquals(playerHard.getGold(), PlayerConfig.STARTING_GOLD_HARD);
    }

    @Test(timeout = TIMEOUT)
    public void testAddingHealth() {
        Player player = new Player("Test Name", 0, "pistol");

        int playerMaxHealth = Player.BASE_HEALTH;

        assertEquals(player.getHealth(), playerMaxHealth);
        assertEquals(player.getMaxHealth(), playerMaxHealth);

        // Losing health
        player.gainHealth(-1);

        assertEquals(player.getHealth(), playerMaxHealth - 1);
        assertEquals(player.getMaxHealth(), playerMaxHealth);

        // Gaining health
        player.gainHealth(1);

        assertEquals(player.getHealth(), playerMaxHealth);
        assertEquals(player.getMaxHealth(), playerMaxHealth);

        // Going over limit
        player.gainHealth(1);

        assertEquals(player.getHealth(), playerMaxHealth);
        assertEquals(player.getMaxHealth(), playerMaxHealth);

        // entity.player.addHealth(1);

        // assertEquals(entity.player.getHealth(), playerMaxHealth);
        // assertEquals(entity.player.getMaxHealth(), playerMaxHealth);
    }

    @Test(timeout = TIMEOUT, expected = RuntimeException.class)
    public void testAddingGold() {
        Player player = new Player("Test Name", 0, "pistol");

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

    @Test(timeout = TIMEOUT, expected = RuntimeException.class)
    public void testAddingItem() {
        // *** assuming player can only have 3 items ***

        Player player = new Player("Test Name", 0, "pistol");

        int numStartingItems = player.getNumItems();

        assertEquals(player.getNumItems(), 0);

        CardLibrary lib = new CardLibrary();

        // Adding Fire Potion
        player.addItem(CardLibrary.getItem("Fire Potion"));

        assertEquals(player.getNumItems(), numStartingItems + 1);
        assertEquals(CardLibrary.getItem("Fire Potion"), player.getItem(0));

        // Test can add
        player.addItem(CardLibrary.getItem("Explosive Potion"));
        player.addItem(CardLibrary.getItem("Health Potion"));

        assertEquals(player.getNumItems(), numStartingItems + 3);
        assertFalse(player.canAddItem());
        assertEquals(CardLibrary.getItem("Explosive Potion"), player.getItem(1));
        assertEquals(CardLibrary.getItem("Health Potion"), player.getItem(2));

        // Item overflow
        //   Should throw RuntimeException
        player.addItem(CardLibrary.getItem("Block Potion"));
    }

    @Test(timeout = TIMEOUT)
    public void testEquipWeapon() {
        Player player = new Player("Test Name", 0, "pistol");
        CardLibrary lib = new CardLibrary();

        assertEquals(CardLibrary.getWeapon("pistol"), player.getEquippedWeapon());

        //equip new weapon
        player.setEquippedWeapon(CardLibrary.getWeapon("rifle"));
        assertEquals(CardLibrary.getWeapon("rifle"), player.getEquippedWeapon());
    }

    @Test(timeout = TIMEOUT)
    public void testWeaponStatuses() {
        Player player = new Player("Test Name", 0, "pistol");

        int pistolStrength = CardLibrary.getWeapon("pistol").getStrength();
        int pistolDex = CardLibrary.getWeapon("pistol").getDex();

        assertEquals(pistolStrength, player.getStatuses().getStrength());
        assertEquals(pistolDex, player.getStatuses().getDex());
    }

    @Test(timeout = TIMEOUT)
    public void testPlayerStatistics() {
        Player player = new Player("Test Name", 0, "pistol");
        player.gainBlock(10);
        player.takeDamage(20);
        assertEquals(10, player.getDamageBlocked());
        assertEquals(10, player.getDamageTaken());
    }
}
