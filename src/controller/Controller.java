package controller;

import dungeon.Dungeon;
import dungeon.Room;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import entity.player.Player;
import view.ConfigScreen;
import view.EndScreen;
import view.GameScreen;
import view.StartScreen;

public class Controller extends Application {

    private Stage mainWindow;
    private final int width = 800;
    private final int height = 450;
    private Player player = null;
    private Dungeon dungeon;

    public Controller() {
        dungeon = new Dungeon();
    }

    public Controller(Room[][] grid) {
        dungeon = new Dungeon(grid);
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(dungeon);
        mainWindow = stage;
        mainWindow.setTitle("Dungeon Crawler");
        initStartScreen();
    }

    private void initStartScreen() {
        StartScreen start = new StartScreen(width, height);
        Button startButton = start.getStart();
        startButton.setOnAction(e -> initConfigScreen());
        Scene scene = start.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();
    }

    private void initConfigScreen() {
        ConfigScreen config = new ConfigScreen(width, height);
        Button next = config.getNext();
        next.setOnAction(e -> {
            String name = config.getName();
            if (name == null || name.trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Name Error");
                alert.setContentText("Please enter a valid name.");
                alert.showAndWait();
            } else {
                player = new Player(config.getName(),
                        config.getDifficultyAsInt(), config.getWeapon());
                initGameScreen();
            }
        });
        Scene scene = config.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();
    }

    public void initGameScreen() {
        GameScreen gameScreen = new GameScreen(width, height, player, dungeon);
        Scene gameScene = gameScreen.getScene();
        Button exitButton = gameScreen.getEndButton();
        exitButton.setOnAction(e -> initEndScreen());
        Button resetButton = gameScreen.getResetButton();
        resetButton.setOnAction(e -> {
            dungeon = new Dungeon();
            try {
                mainWindow.close();
                start(new Stage());
            } catch (Exception exception) {
                System.out.println("Restart didn't work.");
            }
        });
        mainWindow.setScene(gameScene);
        mainWindow.show();
    }

    public void initEndScreen() {
        EndScreen end = new EndScreen(width, height);
        Button restartButton = end.getRestart();
        restartButton.setOnAction(e -> initStartScreen());
        Scene scene = end.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();
    }
}
