package view;

import combat.*;
import dungeon.*;
import entity.Entity;
import entity.enemy.Enemy;
import entity.player.PlayerDeck;
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
import javafx.scene.text.TextAlignment;
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
    private Button[] cardButtons;
    private Button reset;
    private Button inventoryButton;
    private Button challengeButton;
    private Button quitButton;
    private Button resetButton;
    private Font font;

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

    public Button getQuitButton() {
        return quitButton;
    }

    public GameScreen(int w, int h, Player player, Dungeon d) {
        width = w;
        height = h;
        dungeon = d;
        room = d.getCurrentRoom();
        this.player = player;
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        reset = new Button("Reset :(");
        font = new Font("Georgia", 20);
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
        alert.setContentText("You have fallen.\nWill you keel over and die,"
                + "or will you restart your journey?");
        alert.showAndWait();
        Label statistics = new Label("Before your demise you:\ndefeated "
                + player.getEnemiesKilled() + " enemies,\n" + "took "
                + player.getDamageTaken() + " damage,\nblocked "
                + player.getDamageBlocked() + " damage,\n" + "and spent "
                + player.getGoldSpent() + " gold.");
        statistics.setFont(font);
        statistics.setTextAlignment(TextAlignment.CENTER);
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(resetButton, quitButton);
        vb.getChildren().addAll(statistics, hb);
        borderPane.getChildren().clear();
        borderPane.setCenter(vb);
        inventoryButton.setVisible(false);
        resetButton.setVisible(true);
        quitButton.setVisible(true);
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
            if (room.getRoomType() == RoomType.BOSS) {
                exitButton.setVisible(true);
                borderPane.setCenter(exitButton);
            } else if (room.getRoomType() == RoomType.CHALLENGE) {
                player.addGold(30);
                infoLabel.setText("Gold: " + player.getGold()
                        + "\nWeapon: " + player.getEquippedWeapon().getName() + "\nDifficulty: "
                        + player.getPlayerConfig().getDifficultyAsString());
                Label label = new Label("Congrats! \n +30 gold.");
                borderPane.setCenter(label);
            }
        } else {
            controller.startRound();
            //update ui enemies and deck, display results of previous round
            updateCombatUI();
        }
    }

    public void startCombat() {
        CombatController controller = ((CombatRoom) room).getController();

        player = controller.getPlayer();
        player.startCombat();

        player.startRound(); // idk where to put this
        PlayerDeck deck = player.getDeck();
        int handSize = deck.getHand().size();
        cardButtons = new Button[handSize];

        for (int i = 0; i < handSize; i++) {
            Card card = deck.getCardFromHand(i);
            if (card == null) {
                continue;
            }
            cardButtons[i] = new Button();
            Image img = card.getImg();
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(75);
            imgView.setPreserveRatio(true);
            cardButtons[i].setGraphic(imgView);
            cardButtons[i].setOnAction(e -> {
                deck.removeCardFromHand(card);
                Action action;
                if (card.getTargetType() == Target.SINGLE) {
                    action = new Action(controller.getEnemies().get(0), card.getEffect());
                } else if (card.getTargetType() == Target.ENEMIES) {
                    ArrayList<Entity> enemies = new ArrayList<>(controller.getEnemies());
                    action = new Action(enemies, card.getEffect());
                } else {
                    action = new Action(player, card.getEffect());
                }
                playRound(action);
            });
            cardButtons[i].setId("card" + i);
        }
        updateCombatUI();
        borderPane.setCenter(gridPane);
    }

    private void updateCardButtonsUI() {
        CombatController controller = ((CombatRoom) room).getController();

        PlayerDeck deck = player.getDeck();
        int handSize = deck.getHand().size();

        cardButtons = new Button[handSize];

        for (int i = 0; i < handSize; i++) {
            Card card = deck.getCardFromHand(i);
            if (card == null) {
                continue;
            }
            cardButtons[i] = new Button();
            Image img = card.getImg();
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(75);
            imgView.setPreserveRatio(true);
            cardButtons[i].setGraphic(imgView);
            cardButtons[i].setOnAction(e -> {
                deck.removeCardFromHand(card);
                Action action;
                if (card.getTargetType() == Target.SINGLE) {
                    action = new Action(controller.getEnemies().get(0), card.getEffect());
                } else if (card.getTargetType() == Target.ENEMIES) {
                    ArrayList<Entity> enemies = new ArrayList<>(controller.getEnemies());
                    action = new Action(enemies, card.getEffect());
                } else {
                    action = new Action(player, card.getEffect());
                }
                playRound(action);
            });
            cardButtons[i].setId("card" + i);
        }
    }

    public void updateCombatUI() {
        if (room.getRoomType() == RoomType.COMBAT || room.getRoomType() == RoomType.BOSS
                || room.getRoomType() == RoomType.CHALLENGE) {
            CombatController controller = ((CombatRoom) room).getController();
            player = controller.getPlayer();
            gridPane.getChildren().clear();
            updateCardButtonsUI();
            int counter = 0;
            for (Enemy enemy : controller.getEnemies()) {
                gridPane.add(makeEntityPane(enemy), counter, 0);
                counter++;
            }
            for (int i = 0; i < cardButtons.length; i++) {
                gridPane.add(cardButtons[i], i, 2);
            }
            gridPane.add(makeEntityPane(player), 2, 1);

            gridPane.add(inventoryButton, 3, 1);
            borderPane.setCenter(gridPane);
        } else {
            borderPane.setCenter(null);
        }
    }

    public void updateShopUI() {
        ShopController controller = ((ShopRoom) room).getController();
        Item[] items = controller.getPotionItems();
        Card[] cards = controller.getCardItems();
        Button[] itemShopButtons = new Button[items.length];
        Button[] cardShopButtons = new Button[cards.length];
        for (int i = 0; i < itemShopButtons.length; i++) {
            itemShopButtons[i] = new Button();
            Image img = items[i].getImg();
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(75);
            imgView.setPreserveRatio(true);
            itemShopButtons[i].setGraphic(imgView);
            int index = i;
            itemShopButtons[i].setId("Potion" + i);
            itemShopButtons[i].setOnAction(e -> {
                Item transaction = controller.buyItem(items[index]);
                if (transaction != null) {
                    infoLabel.setText("Gold: " + player.getGold()
                            + "\nWeapon: " + player.getEquippedWeapon().getName() + "\nDifficulty: "
                            + player.getPlayerConfig().getDifficultyAsString());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Purchase Completed");
                    alert.setContentText(items[index].getName() + " added to inventory.");
                    alert.showAndWait();
                }
            });
            gridPane.add(itemShopButtons[i], i, 0);
        }
        for (int i = 0; i < cardShopButtons.length; i++) {
            cardShopButtons[i] = new Button();
            Image img = cards[i].getImg();
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(75);
            imgView.setPreserveRatio(true);
            cardShopButtons[i].setGraphic(imgView);
            int index = i;
            cardShopButtons[i].setId("Card" + i);
            cardShopButtons[i].setOnAction(e -> {
                Card transaction = controller.buyCard(cards[index]);
                if (transaction != null) {
                    infoLabel.setText("Gold: " + player.getGold()
                            + "\nWeapon: " + player.getEquippedWeapon().getName() + "\nDifficulty: "
                            + player.getPlayerConfig().getDifficultyAsString());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Purchase Completed");
                    alert.setContentText(cards[index].getName() + " added to deck.");
                    alert.showAndWait();
                }
            });
            gridPane.add(cardShopButtons[i], i, 1);
        }
        borderPane.setCenter(gridPane);
    }

    public void updateInventoryUI() {
        Label attributeLabel = new Label("Health: " + player.getHealth()
                + " ; Gold: " + player.getGold());
        attributeLabel.setFont(font);

        Label statusesLabel = new Label(player.getStatuses().toString());
        statusesLabel.setFont(font);

        HBox weapons = new HBox();
        for (int i = 0; i < player.getNumWeapons(); i++) {
            Weapon currWeapon = player.getWeapon(i);
            Button weaponButton = new Button("Weapon: " + currWeapon.getName());
            weaponButton.setFont(font);
            weaponButton.setOnAction(e -> {
                player.setEquippedWeapon(currWeapon);
                statusesLabel.setText(player.getStatuses().toString());
            });
            weapons.getChildren().add(weaponButton);
        }
        weapons.setAlignment(Pos.CENTER);

        HBox items = new HBox();
        for (int i = 0; i < player.getNumItems(); i++) {
            CombatController controller = ((CombatRoom) room).getController();
            int currIndex = i;
            Item currItem = player.getItem(i);
            Button itemButton = new Button("Item: " + currItem.getName());
            itemButton.setId("item" + i);
            itemButton.setFont(font);
            itemButton.setOnAction(e -> {
                Action action;
                if (currItem.getTargetType() == Target.SINGLE) {
                    action = new Action(controller.getEnemies().get(0), currItem.getEffect());
                } else if (currItem.getTargetType() == Target.ENEMIES) {
                    ArrayList<Entity> enemies = new ArrayList<>(controller.getEnemies());
                    action = new Action(enemies, currItem.getEffect());
                } else {
                    action = new Action(player, currItem.getEffect());
                }
                action.applyEffect();
                statusesLabel.setText(player.getStatuses().toString());
                attributeLabel.setText("Health: " + player.getHealth()
                        + " ; Gold: " + player.getGold());
                player.removeItem(currIndex);
                updateInventoryUI();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potion used!");
                alert.setContentText("Drank " + currItem.getName());
                alert.showAndWait();
            });
            items.getChildren().add(itemButton);
        }
        items.setAlignment(Pos.CENTER);

        Button combatButton = new Button("Go back to combat");
        combatButton.setFont(font);
        combatButton.setOnAction(e -> {
            updateCombatUI();
        });

        VBox inventoryVBox = new VBox(attributeLabel, statusesLabel, weapons, items, combatButton);
        inventoryVBox.setAlignment(Pos.CENTER);

        borderPane.setCenter(inventoryVBox);
    }

    private VBox makeEntityPane(Entity entity) {
        Rectangle entityShape = new Rectangle(50, 50, Color.GREEN);
        Label entityInfo = new Label("Entity: " + entity.getName()
                + "\nHealth: " + entity.getHealth());
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
                if (!((CombatRoom) room).getController().isCombatEnd()) {
                    startCombat();
                } else {
                    gridPane.getChildren().clear();
                }
            } else if (room.getRoomType() == RoomType.BOSS) {
                setOppositeDirection(d);
                if (((BossRoom) room).getController() == null) {
                    ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
                    enemyList.add(new Enemy("Boss"));
                    ((BossRoom) room).setController(new CombatController(player, enemyList));
                }
                if (!((BossRoom) room).getController().isCombatEnd()) {
                    System.out.println("Combat started");
                    startCombat();
                } else {
                    exitButton.setVisible(true);
                    borderPane.setCenter(exitButton);
                }
            } else if (room.getRoomType() == RoomType.SHOP) {
                setDoorsAndButtons(null);
                if (((ShopRoom) room).getController() == null) {
                    ((ShopRoom) room).setController(new ShopController(player));
                }
                gridPane.getChildren().clear();
                updateShopUI();
            } else if (room.getRoomType() == RoomType.CHALLENGE) {
                setDoorsAndButtons(null);
                challengeButton = new Button("Accept the challenge?");
                challengeButton.setOnAction(e -> {
                    ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
                    for (int i = 0; i < 5; i++) {
                        int enemyType = (int) (Math.random() * 3);
                        if (enemyType == 0) {
                            enemyList.add(new Enemy("Slime"));
                        } else if (enemyType == 1) {
                            enemyList.add(new Enemy("Goblin"));
                        } else {
                            enemyList.add(new Enemy("Snake"));
                        }
                    }
                    ((ChallengeRoom) room).setController(new CombatController(player, enemyList));
                    room.lockDoor(room.getDoor(Direction.EAST));
                    room.lockDoor(room.getDoor(Direction.WEST));
                    room.lockDoor(room.getDoor(Direction.NORTH));
                    room.lockDoor(room.getDoor(Direction.SOUTH));
                    startCombat();
                });
                if (((ChallengeRoom) room).getController() == null) {
                    borderPane.setCenter(challengeButton);
                } else {
                    Label label = new Label("Congrats! \n +5 Strength \n + 5 Dex \n +30 gold");
                    borderPane.setCenter(label);
                }

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
                + "\nWeapon: " + player.getEquippedWeapon().getName() + "\nDifficulty: "
                + player.getPlayerConfig().getDifficultyAsString());
        infoLabel.setFont(new Font("Cambria", 20));

        Rectangle exitButtonRect = new Rectangle(75, 75, Color.GREEN);

        exitButton = new Button("Exit Dungeon", exitButtonRect);
        exitButton.setTextFill(Color.BLACK);
        exitButton.setContentDisplay(ContentDisplay.CENTER);
        exitButton.setVisible(false);

        quitButton = new Button("Quit Game");
        quitButton.setTextFill(Color.BLACK);
        quitButton.setContentDisplay(ContentDisplay.CENTER);
        quitButton.setVisible(false);

        resetButton = new Button("Restart Dungeon");
        resetButton.setTextFill(Color.BLACK);
        resetButton.setContentDisplay(ContentDisplay.CENTER);
        resetButton.setVisible(false);

        inventoryButton = new Button("Go to inventory");
        inventoryButton.setOnAction(e -> {
            updateInventoryUI();
        });

        setDoorsAndButtons(null);

        return new Scene(borderPane, width, height);
    }
}
