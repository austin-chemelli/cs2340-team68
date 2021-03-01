package view;

import dungeon.BasicRoom;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import util.Direction;

public class GameScreen {
    private int width;
    private int height;
    private String difficulty;
    private BasicRoom room;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameScreen(int w, int h, String difficulty) {
        width = w;
        height = h;
        this.difficulty = difficulty;
        room = new BasicRoom();
    }

    public Scene getScene() {
        BorderPane borderPane = new BorderPane();

        Label goldLabel = new Label();
        switch (difficulty) {
        case "easy":
            goldLabel.setText("Current gold: 200");
            break;
        case "medium":
            goldLabel.setText("Current gold: 150");
            break;
        case "hard":
            goldLabel.setText("Current gold: 100");
            break;
        default:
            goldLabel.setText("CURRENT DIFFICULTY NOT SUPPORTED");
        }
        goldLabel.setFont(new Font("Cambria", 20));

        HBox hBox = new HBox(goldLabel, room.getDoor(Direction.NORTH).getDoorButton());
        hBox.setSpacing(175);

        borderPane.setTop(hBox);
        borderPane.setLeft(room.getDoor(Direction.WEST).getDoorButton());
        borderPane.setRight(room.getDoor(Direction.EAST).getDoorButton());
        borderPane.setBottom(room.getDoor(Direction.SOUTH).getDoorButton());

        borderPane.setAlignment(room.getDoor(Direction.NORTH).getDoorButton(), Pos.CENTER);
        borderPane.setAlignment(room.getDoor(Direction.WEST).getDoorButton(), Pos.CENTER);
        borderPane.setAlignment(room.getDoor(Direction.EAST).getDoorButton(), Pos.CENTER);
        borderPane.setAlignment(room.getDoor(Direction.SOUTH).getDoorButton(), Pos.CENTER);

        return new Scene(borderPane, width, height);
    }
}
