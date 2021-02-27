package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartScreen {

    private int width;
    private int height;
    private Button start;
    private Label title;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Button getStart() {
        return start;
    }

    public Label getTitle() {
        return title;
    }

    public StartScreen(int w, int h) {
        width = w;
        height = h;
        start = new Button("Start Game");
        title = new Label("Team 68 Dungeon Crawler");
    }

    public Scene getScene() {
        StackPane top = new StackPane();
        FlowPane center = new FlowPane();
        top.setPadding(new Insets(50, 0, 0, 0));
        top.setAlignment(Pos.CENTER);
        center.setAlignment(Pos.CENTER);

        title.setFont(new Font("Georgia", 50));
        title.setAlignment(Pos.CENTER);
        top.getChildren().add(title);

        start.setFont(new Font("Georgia", 25));
        center.getChildren().add(start);

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(center);

        Scene scene = new Scene(root, width, height);
        return scene;
    }

}
