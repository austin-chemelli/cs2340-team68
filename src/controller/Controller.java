package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import player.PlayerData;
import view.ConfigScreen;
import view.GameScreen;
import view.StartScreen;

public class Controller extends Application {

    private Stage mainWindow;
    private final int width = 800;
    private final int height = 450;
    private PlayerData playerData = null;

    @Override
    public void start(Stage stage) throws Exception {
        mainWindow = stage;
        mainWindow.setTitle("Dungeon Crawler");
        initStartScreen();
    }

    private void initStartScreen() {
        StartScreen start = new StartScreen(width, height);
        Button startButton = start.getStart();
        startButton.setOnAction(e -> configScreen());
        Scene scene = start.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();
    }

    private void configScreen() {
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
                playerData = new PlayerData(config.getName(), config.getDifficultyAsInt(), config.getWeapon());
                initGameScreen();
            }
        });
        Scene scene = config.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();
    }

    public void initGameScreen() {
        GameScreen gameScreen = new GameScreen(width, height, playerData);
        Scene gameScene = gameScreen.getScene();
        mainWindow.setScene(gameScene);
        mainWindow.show();
    }
}
