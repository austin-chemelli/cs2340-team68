package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class EndScreen {

    private int width;
    private int height;
    private Button restart;
    private Label title;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Button getRestart() {
        return restart;
    }

    public Label getTitle() {
        return title;
    }

    public EndScreen(int w, int h) {
        width = w;
        height = h;
        restart = new Button("Restart Game");
        title = new Label("Congrats! You won.");
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

        restart.setFont(new Font("Georgia", 25));
        center.getChildren().add(restart);

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(center);

        Scene scene = new Scene(root, width, height);
        return scene;
    }

}
