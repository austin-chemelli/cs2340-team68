package dungeon;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import util.Direction;

public class Door {
    private boolean isLocked;
    private Button doorButton;
    private Rectangle doorButtonRect;

    public Door(Direction direction) {
        this(direction, true);
    }

    public Door(Direction direction, boolean isLocked) {
        this.isLocked = isLocked;

        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            doorButtonRect = new Rectangle(150, 75, Color.RED);
        } else {
            doorButtonRect = new Rectangle(75, 150, Color.RED);
        }

        doorButton = new Button(direction + " exit", doorButtonRect);

        //changes to green if door is unlocked
        if (!isLocked()) {
            doorButtonRect.setFill(Color.GREEN);
        }

        doorButton.setTextFill(Color.BLACK);
        doorButton.setContentDisplay(ContentDisplay.CENTER);
        doorButton.setOnAction(event -> {
            //action only happens when door is open
            if (!isLocked()) {
                System.out.println("Clicked on the " + direction + " exit");
            }
        });
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        if (locked) {
            doorButtonRect.setFill(Color.RED);
        } else {
            doorButtonRect.setFill(Color.GREEN);
        }
        isLocked = locked;
    }

    public Button getDoorButton() {
        return doorButton;
    }
}
