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
        // verifyThat("", NodeMatchers.isNotNull());  To be implemented when config screen is created
    }
}
