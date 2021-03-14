package view;

import dungeon.Dungeon;
import dungeon.Room;
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
    private Room room;
    private Dungeon dungeon;
    private PlayerData playerData;
    private BorderPane borderPane;
    private HBox hBox;
    private Label infoLabel;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameScreen(int w, int h, PlayerData playerData, Dungeon d) {
        width = w;
        height = h;
        dungeon = d;
        room = d.getCurrentRoom();
        this.playerData = playerData;
    }

    public void setDoors() {
        hBox = new HBox(infoLabel, room.getDoor(Direction.NORTH).getDoorButton());
        hBox.setSpacing(170);

        borderPane.setTop(hBox);
        borderPane.setLeft(room.getDoor(Direction.WEST).getDoorButton());
        room.unlockDoor(room.getDoor(Direction.WEST));
        borderPane.setRight(room.getDoor(Direction.EAST).getDoorButton());
        room.unlockDoor(room.getDoor(Direction.EAST));
        borderPane.setBottom(room.getDoor(Direction.SOUTH).getDoorButton());
        room.unlockDoor(room.getDoor(Direction.SOUTH));

        //borderPane.setAlignment(hBox, Pos.CENTER);
        borderPane.setAlignment(room.getDoor(Direction.NORTH).getDoorButton(), Pos.CENTER);
        borderPane.setAlignment(room.getDoor(Direction.WEST).getDoorButton(), Pos.CENTER);
        borderPane.setAlignment(room.getDoor(Direction.EAST).getDoorButton(), Pos.CENTER);
        borderPane.setAlignment(room.getDoor(Direction.SOUTH).getDoorButton(), Pos.CENTER);

        room.getDoor(Direction.NORTH).getDoorButton().setOnAction(event -> {
            if (!room.getDoor(Direction.NORTH).isLocked()) {
                changeRoom(Direction.NORTH);
            }
        });

        room.getDoor(Direction.SOUTH).getDoorButton().setOnAction(event -> {
            if (!room.getDoor(Direction.SOUTH).isLocked()) {
                changeRoom(Direction.SOUTH);
            }
        });

        room.getDoor(Direction.WEST).getDoorButton().setOnAction(event -> {
            if (!room.getDoor(Direction.WEST).isLocked()) {
                changeRoom(Direction.WEST);
            }
        });

        room.getDoor(Direction.EAST).getDoorButton().setOnAction(event -> {
            if (!room.getDoor(Direction.EAST).isLocked()) {
                changeRoom(Direction.EAST);
            }
        });
    }

    public void changeRoom(Direction d) {
        if (!dungeon.canMove(d)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Dead End");
            alert.setContentText("This door leads to nothing!");
            alert.showAndWait();
        } else {
            dungeon.move(d);
            room = dungeon.getCurrentRoom();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Door Confirmation");
            alert.setContentText("You opened the " + d.toString().toLowerCase()
                    + " door!\n" + room.toString());
            alert.showAndWait();
            setDoors();
        }
    }

    public Scene getScene() {
        borderPane = new BorderPane();
        infoLabel = new Label();
        infoLabel.setText("Gold: " + playerData.getGold()
                + "\nWeapon: " + playerData.getStartingWeapon() + "\nDifficulty: "
                + playerData.getPlayerConfig().getDifficultyAsString());
        infoLabel.setFont(new Font("Cambria", 20));

        Button testButton = new Button("open north door");
        borderPane.setCenter(testButton);
        testButton.setOnAction(event -> room.unlockDoor(room.getDoor(Direction.NORTH)));
        borderPane.setAlignment(testButton, Pos.CENTER);
        setDoors();

        return new Scene(borderPane, width, height);
    }
}
