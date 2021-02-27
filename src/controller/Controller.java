package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
        Scene scene = start.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();
    }
}
