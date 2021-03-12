import controller.Controller;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class ControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Controller controller = new Controller();
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
        clickOn("open north door");
        clickOn("NORTH exit");
        verifyThat("You opened the north door", NodeMatchers.isNotNull());
    }
}
