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
        verifyThat("Your difficulty is easy", NodeMatchers.isNotNull());
    }

    @Test
    public void testWeapon() {
        clickOn("Start Game");
        clickOn("Pistol");
        verifyThat("Your weapon is a pistol", NodeMatchers.isNotNull());
    }

    @Test
    public void testDifficultyMoney() {
        controller.initialGameScreen("easy");
        verifyThat("Current gold: 200", NodeMatchers.isNotNull());

        controller.initialGameScreen("medium");
        verifyThat("Current gold: 150", NodeMatchers.isNotNull());

        controller.initialGameScreen("hard");
        verifyThat("Current gold: 100", NodeMatchers.isNotNull());

        controller.initialGameScreen("nonsense");
        verifyThat("CURRENT DIFFICULTY NOT SUPPORTED", NodeMatchers.isNotNull());
    }
}
