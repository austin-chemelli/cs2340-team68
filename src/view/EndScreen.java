package view;

import entity.player.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class EndScreen {

    private int width;
    private int height;
    private Button restart;
    private Button quit;
    private Label title;
    private Player player;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Button getRestart() {
        return restart;
    }

    public Button getQuit() {
        return quit;
    }

    public Label getTitle() {
        return title;
    }

    public EndScreen(int w, int h, Player p) {
        width = w;
        height = h;
        restart = new Button("Restart Game");
        quit = new Button("Quit Game");
        title = new Label("Congrats! You won.");
        player = p;
    }

    public Scene getScene() {
        StackPane top = new StackPane();
        VBox center = new VBox(20);
        HBox hb = new HBox(10);
        top.setPadding(new Insets(50, 0, 0, 0));
        top.setAlignment(Pos.CENTER);
        center.setAlignment(Pos.CENTER);

        title.setFont(new Font("Georgia", 50));
        title.setAlignment(Pos.CENTER);
        top.getChildren().add(title);

        Label statistics = new Label("Along your journey you:\ndefeated " + player.getEnemiesKilled()
                + " enemies,\n" + "took " + player.getDamageTaken() + " damage,\nblocked " + player.getDamageBlocked()
                + " damage,\n" + "and spent " + player.getGoldSpent() + " gold.");
        statistics.setFont(new Font("Georgia", 20));
        statistics.setTextAlignment(TextAlignment.CENTER);
        restart.setFont(new Font("Georgia", 25));
        quit.setFont(new Font("Georgia", 25));
        hb.getChildren().addAll(restart, quit);
        hb.setAlignment(Pos.CENTER);
        center.getChildren().addAll(statistics, hb);

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(center);

        Scene scene = new Scene(root, width, height);
        return scene;
    }

}
