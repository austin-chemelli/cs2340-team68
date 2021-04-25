import controller.Controller;
import dungeon.*;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

public class ControllerTest extends ApplicationTest {

    private Controller controller;

    @Override
    public void start(Stage stage) throws Exception {
        Room[][] roomGrid = new Room[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0 && j == 0) {
                    roomGrid[i][j] = new StartRoom();
                } else if (i == 0 && j == 1) {
                    roomGrid[i][j] = new CombatRoom();
                } else if (i == 1 && j == 0) {
                    roomGrid[i][j] = new ChallengeRoom();
                } else if (i == 3 && j == 3) {
                    roomGrid[i][j] = new BossRoom();
                }  else if (i == 1 && j == 1) {
                    roomGrid[i][j] = new ShopRoom();
                } else {
                    roomGrid[i][j] = new SafeRoom();
                }
            }
        }
        controller = new Controller(roomGrid);
        controller.start(stage);
    }

    @Test
    public void testStart() {
        verifyThat("Team 68 Dungeon Crawler", NodeMatchers.isNotNull());
        verifyThat("Start Game", NodeMatchers.isNotNull());
    }

    @Test
    public void testConfig() {
        clickOn("Start Game");
        verifyThat("Configuration Screen", NodeMatchers.isNotNull());
    }

    @Test
    public void testDifficulty() {
        clickOn("Start Game");
        clickOn("Easy");
        verifyThat("Your difficulty is Easy", NodeMatchers.isNotNull());
    }

    @Test
    public void testWeapon() {
        clickOn("Start Game");
        clickOn("Pistol");
        verifyThat("Your weapon is a pistol", NodeMatchers.isNotNull());
    }

    @Test
    public void testFieldDisplay() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Medium");
        clickOn("Shotgun");
        clickOn("Next");
        verifyThat("Gold: 150\nWeapon: shotgun\nDifficulty: Medium", NodeMatchers.isNotNull());
    }

    @Test
    public void testNameField() {
        clickOn("Start Game");
        clickOn("Set");
        verifyThat("Please enter a valid name.", NodeMatchers.isNotNull());
    }

    @Test
    public void testDefaultDifficulty() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        verifyThat("Gold: 200\nWeapon: pistol\nDifficulty: Easy", NodeMatchers.isNotNull());
    }

    @Test
    public void testOpeningDoor() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        clickOn("NORTH exit");
        verifyThat("Confirmation", NodeMatchers.isNotNull());
    }

    @Test
    public void testDeadEnd() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        clickOn("SOUTH exit");
        verifyThat("This door leads to nothing!", NodeMatchers.isNotNull());
        clickOn("OK");
        clickOn("WEST exit");
        verifyThat("This door leads to nothing!", NodeMatchers.isNotNull());
    }

    @Test
    public void testReturnToRoom() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        clickOn("EAST exit");
        clickOn("OK");
        clickOn("WEST exit");
        clickOn("OK");
        clickOn("WEST exit");
        verifyThat("This door leads to nothing!", NodeMatchers.isNotNull());
        clickOn("OK");
        clickOn("SOUTH exit");
        verifyThat("This door leads to nothing!", NodeMatchers.isNotNull());
    }

    /**@Test
    public void testExitAppears() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        for (int i = 0; i < 6; i++) {
            if (i < 3) {
                clickOn("EAST exit");
            } else {
                clickOn("NORTH exit");
            }
            clickOn("OK");
        }
        verifyThat("Exit", NodeMatchers.isNotNull());
    }**/

    /**@Test
    public void testEndScreen() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        for (int i = 0; i < 6; i++) {
            if (i < 3) {
                clickOn("EAST exit");
            } else {
                clickOn("NORTH exit");
            }
            clickOn("OK");
        }
        clickOn("Exit");
        verifyThat("Congrats! You won.", NodeMatchers.isNotNull());
    }**/

    @Test
    public void testCombatDoors() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        clickOn("NORTH exit");
        clickOn("OK");
        clickOn("NORTH exit");
        clickOn("WEST exit");
        clickOn("EAST exit");
        clickOn("SOUTH exit");
        verifyThat("Confirmation", NodeMatchers.isNotNull());
        clickOn("OK");
        clickOn("SOUTH exit");
        verifyThat("This door leads to nothing!", NodeMatchers.isNotNull());
    }

    @Test public void testCombatCardAppears() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        clickOn("NORTH exit");
        clickOn("OK");
        verifyThat("#card0", NodeMatchers.isNotNull());
    }

    @Test
    public void testCombatCard() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        clickOn("NORTH exit");
        clickOn("OK");
        clickOn("#card0");
        verifyThat("Message", NodeMatchers.isNotNull());
    }

    @Test
    public void testBuyFromShop() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        clickOn("EAST exit");
        clickOn("OK");
        clickOn("NORTH exit");
        clickOn("OK");
        clickOn("#Potion1");
        verifyThat("Message", NodeMatchers.isNotNull());
        verifyThat("Gold: 190\nWeapon: pistol\nDifficulty: Easy", NodeMatchers.isNotNull());
    }

    @Test
    public void testCombatInventory() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        clickOn("NORTH exit");
        clickOn("OK");
        clickOn("Go to inventory");
        verifyThat("Go back to combat", NodeMatchers.isNotNull());
        clickOn("Go back to combat");
        verifyThat("#card0", NodeMatchers.isNotNull());
    }

    @Test
    public void testUsePotion() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        clickOn("EAST exit");
        clickOn("OK");
        clickOn("NORTH exit");
        clickOn("OK");
        clickOn("#Potion0");
        clickOn("OK");
        clickOn("WEST exit");
        clickOn("OK");
        clickOn("Go to inventory");
        verifyThat("#item0", NodeMatchers.isNotNull());
        clickOn("#item0");
        verifyThat("Confirmation", NodeMatchers.isNotNull());
    }

    @Test
    public void testSpendGold() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        clickOn("EAST exit");
        clickOn("OK");
        clickOn("NORTH exit");
        clickOn("OK");
        clickOn("#Potion0");
        clickOn("OK");
        assertEquals(10, controller.getPlayer().getGoldSpent());
    }

    @Test
    public void testChallengeRoom() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        clickOn("EAST exit");
        clickOn("OK");
        clickOn("Accept the challenge?");
        clickOn("#card0");
        verifyThat("Message", NodeMatchers.isNotNull());
    }

    @Test
    public void testChallengeDoorsLock() {
        clickOn("Start Game");
        clickOn("#nameField").write("Test name");
        clickOn("Set");
        clickOn("Pistol");
        clickOn("Next");
        clickOn("EAST exit");
        clickOn("OK");
        clickOn("Accept the challenge?");
        clickOn("EAST exit");
        clickOn("WEST exit");
        clickOn("NORTH exit");
        clickOn("SOUTH exit");
        clickOn("#card0");
        verifyThat("Message", NodeMatchers.isNotNull());
    }
}
