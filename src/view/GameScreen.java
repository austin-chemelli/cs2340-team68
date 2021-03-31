package view;

import combat.Action;
import combat.Card;
import combat.CombatController;
import dungeon.CombatRoom;
import dungeon.Dungeon;
import dungeon.Room;
import dungeon.RoomType;
import entity.Entity;
import entity.enemy.Enemy;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import util.Direction;
import entity.player.Player;

import java.util.ArrayList;

public class GameScreen {
    private int width;
    private int height;
    private Room room;
    private Dungeon dungeon;
    private Player player;
    private BorderPane borderPane;
    private HBox hBox;
    private Label infoLabel;
    private Button exitButton;
    private CombatController currController;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Button getEndButton() {
        return exitButton;
    }

    public GameScreen(int w, int h, Player player, Dungeon d) {
        width = w;
        height = h;
        dungeon = d;
        room = d.getCurrentRoom();
        this.player = player;
    }

    public void setDoorsAndButtons() {
        hBox = new HBox(infoLabel, room.getDoor(Direction.NORTH).getDoorButton(), exitButton);
        hBox.setSpacing(170);

        room.unlockDoor(room.getDoor(Direction.NORTH));
        room.unlockDoor(room.getDoor(Direction.WEST));
        room.unlockDoor(room.getDoor(Direction.EAST));
        room.unlockDoor(room.getDoor(Direction.SOUTH));
        
        borderPane.setTop(hBox);
        borderPane.setLeft(room.getDoor(Direction.WEST).getDoorButton());
        borderPane.setRight(room.getDoor(Direction.EAST).getDoorButton());
        borderPane.setBottom(room.getDoor(Direction.SOUTH).getDoorButton());

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

    //not finished yet
    public void reset() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Game Over");
        alert.setContentText("You have died, restarting from last fight");
        alert.showAndWait();
        ((CombatRoom) room).setController(currController);
        startCombat();
    }

    //once player confirms action
    public void playRound(Action playerAction) {
        CombatController controller = ((CombatRoom) room).getController();
        controller.endRound(playerAction);
        if (player.getIsDead()) {
            reset();
        } else if ((controller.isCombatEnd())) {
            System.out.println("Enemies dead.");
            //remove ui and announce victory to player, unlock doors
        } else {
            controller.startRound();
            //update ui enemies and deck, display results of previous round
        }
    }

    public void startCombat() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        CombatController controller = ((CombatRoom) room).getController();
        ArrayList<Card> deck = player.getDeck();
        Button[] cardButtons = new Button[deck.size()];
        for (int i = 0; i < deck.size(); i++) {
            Card card = deck.get(i);
            cardButtons[i] = new Button();
            if (card.getName().equals("Strike")) {
                Image img = new Image("./images/StrikeCard.png");
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(75);
                imgView.setPreserveRatio(true);
                cardButtons[i].setGraphic(imgView);
                cardButtons[i].setOnAction(e -> {
                    Action action = new Action(controller.getEnemies().get(0), card.getEffect());
                    playRound(action);
                });
            } else if (card.getName().equals("Swipe")) {
                Image img = new Image("./images/SwipeCard.png");
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(75);
                imgView.setPreserveRatio(true);
                cardButtons[i].setGraphic(imgView);
                cardButtons[i].setOnAction(e -> {
                    ArrayList<Entity> enemies = new ArrayList<>(controller.getEnemies());
                    Action action = new Action(enemies, card.getEffect());
                    playRound(action);
                });
            } else {
                Image img = new Image("./images/DefendCard.png");
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(75);
                imgView.setPreserveRatio(true);
                cardButtons[i].setGraphic(imgView);
                cardButtons[i].setOnAction(e -> {
                    Action action = new Action(player, card.getEffect());
                    playRound(action);
                });
            }
            gridPane.add(cardButtons[i], i + 1, 0);
        }
        // add player and enemies to gridpane

        borderPane.setCenter(gridPane);
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
            setDoorsAndButtons();
            if (room.getRoomType() == RoomType.COMBAT) {
                if (((CombatRoom) room).getController() == null) {
                    ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
                    int enemyNum = (int) (Math.random() * 3 + 1);
                    for (int i = 0; i < enemyNum; i++) {
                        int enemyType = (int) (Math.random() * 3);
                        if (enemyType == 0) {
                            enemyList.add(new Enemy("Slime"));
                        } else if (enemyType == 1) {
                            enemyList.add(new Enemy("Goblin"));
                        } else {
                            enemyList.add(new Enemy("Snake"));
                        }
                    }
                    ((CombatRoom) room).setController(new CombatController(player, enemyList));
                }
                currController = ((CombatRoom) room).getController();
                if (!((CombatRoom) room).getController().isCombatEnd()) {
                    startCombat();
                }
            }
        }
    }

    public Scene getScene() {
        borderPane = new BorderPane();
        infoLabel = new Label();
        infoLabel.setText("Gold: " + player.getGold()
                + "\nWeapon: " + player.getStartingWeapon() + "\nDifficulty: "
                + player.getPlayerConfig().getDifficultyAsString());
        infoLabel.setFont(new Font("Cambria", 20));

        Rectangle exitButtonRect = new Rectangle(75, 75, Color.YELLOW);

        exitButton = new Button("Exit", exitButtonRect);
        exitButton.setTextFill(Color.BLACK);
        exitButton.setContentDisplay(ContentDisplay.CENTER);
        exitButton.setVisible(false);

        setDoorsAndButtons();

        return new Scene(borderPane, width, height);
    }
}
