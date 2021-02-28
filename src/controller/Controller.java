package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.ConfigScreen;
import view.StartScreen;

public class Controller extends Application {

    private Stage mainWindow;
    private final int width = 800;
    private final int height = 450;

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
                initialGameScreen();
            }
        });
        Scene scene = config.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();
    }

    private void initialGameScreen() {
        mainWindow.close();
    }
}
