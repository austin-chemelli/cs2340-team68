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
import javafx.scene.layout.VBox;
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
    private GridPane gridPane;
    private Label infoLabel;
    private Button exitButton;
    private CombatController currController;
    private Button[] cardButtons;

    private Button resetButton;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Button getEndButton() {
        return exitButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public GameScreen(int w, int h, Player player, Dungeon d) {
        width = w;
        height = h;
        dungeon = d;
        room = d.getCurrentRoom();
        this.player = player;
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
    }

    public void setDoorsAndButtons(Direction d) {
        hBox = new HBox(infoLabel, room.getDoor(Direction.NORTH).getDoorButton(), exitButton);
        hBox.setSpacing(170);

        if (room.getRoomType() == RoomType.BOSS) {
            exitButton.setVisible(true);
            borderPane.setCenter(exitButton);
        } else {
            exitButton.setVisible(false);
            borderPane.setCenter(gridPane);
        }

        if (d == null) {
            room.unlockDoor(room.getDoor(Direction.NORTH));
            room.unlockDoor(room.getDoor(Direction.SOUTH));
            room.unlockDoor(room.getDoor(Direction.EAST));
            room.unlockDoor(room.getDoor(Direction.WEST));
        } else {
            room.unlockDoor(room.getDoor(d));
        }
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
        alert.setContentText("You have died. Will you try again?");
        alert.showAndWait();
        borderPane.getChildren().clear();
        borderPane.setCenter(resetButton);
        resetButton.setVisible(true);
    }

    //once player confirms action
    public void playRound(Action playerAction) {
        CombatController controller = ((CombatRoom) room).getController();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("End of round");
        alert.setContentText(controller.endRound(playerAction));
        alert.showAndWait();
        updateCombatUI();
        if (player.getIsDead()) {
            reset();
        } else if ((controller.isCombatEnd())) {
            System.out.println("Enemies dead.");
            gridPane.getChildren().clear();
            room.unlockDoor(room.getDoor(Direction.NORTH));
            room.unlockDoor(room.getDoor(Direction.SOUTH));
            room.unlockDoor(room.getDoor(Direction.EAST));
            room.unlockDoor(room.getDoor(Direction.WEST));
        } else {
            controller.startRound();
            //update ui enemies and deck, display results of previous round
        }
    }

    public void startCombat() {
        CombatController controller = ((CombatRoom) room).getController();
        player = controller.getPlayer();
        ArrayList<Card> deck = player.getDeck();
        cardButtons = new Button[deck.size()];
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
        }
        updateCombatUI();
        borderPane.setCenter(gridPane);
    }

    public void updateCombatUI() {
        if (room.getRoomType() == RoomType.COMBAT) {
            CombatController controller = ((CombatRoom) room).getController();
            player = controller.getPlayer();
            gridPane.getChildren().clear();
            int counter = 0;
            for (Enemy enemy : controller.getEnemies()) {
                gridPane.add(makeEntityPane(enemy), counter, 0);
                counter++;
            }
            for (int i = 0; i < cardButtons.length; i++) {
                gridPane.add(cardButtons[i], i, 2);
            }
            gridPane.add(makeEntityPane(player), 2, 1);
        } else {
            borderPane.setCenter(null);
        }
    }

    private VBox makeEntityPane(Entity entity) {
        Rectangle entityShape = new Rectangle(50, 50, Color.GREEN);
        Label entityInfo = new Label("Entity: " + entity.getName() + "\nHealth: " + entity.getHealth());
        VBox vBox = new VBox(entityShape, entityInfo);
        return vBox;
    }

    public void setOppositeDirection(Direction d) {
        switch (d) {
            case EAST:
                setDoorsAndButtons(Direction.WEST);
                break;
            case NORTH:
                setDoorsAndButtons(Direction.SOUTH);
                break;
            case WEST:
                setDoorsAndButtons(Direction.EAST);
                break;
            case SOUTH:
                setDoorsAndButtons(Direction.NORTH);
                break;
            default:
                break;
        }
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
            if (room.getRoomType() == RoomType.COMBAT) {
                setOppositeDirection(d);
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
                } else {
                    gridPane.getChildren().clear();
                }
            } else if (room.getRoomType() == RoomType.BOSS) {
                setOppositeDirection(d);
                gridPane.getChildren().clear();
            } else {
                setDoorsAndButtons(null);
                gridPane.getChildren().clear();
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

        Rectangle exitButtonRect = new Rectangle(75, 75, Color.GREEN);

        exitButton = new Button("Exit", exitButtonRect);
        exitButton.setTextFill(Color.BLACK);
        exitButton.setContentDisplay(ContentDisplay.CENTER);
        exitButton.setVisible(false);

        resetButton = new Button("Restart Dungeon");
        exitButton.setTextFill(Color.BLACK);
        exitButton.setContentDisplay(ContentDisplay.CENTER);
        exitButton.setVisible(false);

        setDoorsAndButtons(null);

        return new Scene(borderPane, width, height);
    }
}
