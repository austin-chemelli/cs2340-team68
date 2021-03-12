package view;

import dungeon.StartRoom;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import util.Direction;
import player.PlayerData;

public class GameScreen {
    private int width;
    private int height;
    private String difficulty;
    private StartRoom room;
    private PlayerData playerData;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameScreen(int w, int h, PlayerData playerData) {
        width = w;
        height = h;
        this.difficulty = difficulty;
        room = new StartRoom();
        this.playerData = playerData;
    }

    public Scene getScene() {
        BorderPane borderPane = new BorderPane();
        Label infoLabel = new Label();
        infoLabel.setText("Gold: " + playerData.getGold()
                          + "\nWeapon: " + playerData.getStartingWeapon()
                          + "\nDifficulty: " + playerData.getPlayerConfig().getDifficultyAsString());
        infoLabel.setFont(new Font("Cambria", 20));

        Button testButton = new Button("open north door");
        testButton.setOnAction(event -> room.unlockDoor(room.getDoor(Direction.NORTH)));

        HBox hBox = new HBox(infoLabel, room.getDoor(Direction.NORTH).getDoorButton());
        hBox.setSpacing(170);

        borderPane.setTop(hBox);
        borderPane.setLeft(room.getDoor(Direction.WEST).getDoorButton());
        borderPane.setRight(room.getDoor(Direction.EAST).getDoorButton());
        borderPane.setBottom(room.getDoor(Direction.SOUTH).getDoorButton());
        borderPane.setCenter(testButton);

        //borderPane.setAlignment(hBox, Pos.CENTER);
        borderPane.setAlignment(room.getDoor(Direction.NORTH).getDoorButton(), Pos.CENTER);
        borderPane.setAlignment(room.getDoor(Direction.WEST).getDoorButton(), Pos.CENTER);
        borderPane.setAlignment(room.getDoor(Direction.EAST).getDoorButton(), Pos.CENTER);
        borderPane.setAlignment(room.getDoor(Direction.SOUTH).getDoorButton(), Pos.CENTER);
        borderPane.setAlignment(testButton, Pos.CENTER);

        room.getDoor(Direction.NORTH).getDoorButton().setOnAction(event -> {
            if (!room.getDoor(Direction.NORTH).isLocked()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Door Confirmation");
                alert.setContentText("You opened the north door");
                alert.showAndWait();
            }
        });

        return new Scene(borderPane, width, height);
    }
}
