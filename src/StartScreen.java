import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StartScreen extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        StackPane top = new StackPane();
        BorderPane root = new BorderPane();
        Label label = new Label("pog");
        label.setAlignment(Pos.CENTER);
        top.getChildren().add(label);
        root.setTop(top);
        Scene scene = new Scene(root, 1000, 800);
        stage.setScene(scene);
        stage.setTitle("Dungeon Crawler");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
