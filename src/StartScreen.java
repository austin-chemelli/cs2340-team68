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

public class StartScreen extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        StackPane top = new StackPane();
        FlowPane center = new FlowPane();
        top.setPadding(new Insets(50, 0, 0, 0));
        top.setAlignment(Pos.CENTER);
        center.setAlignment(Pos.CENTER);


        Label title = new Label("Team 68 Dungeon Crawler");
        title.setFont(new Font("Georgia", 50));
        title.setAlignment(Pos.CENTER);
        top.getChildren().add(title);

        Button start = new Button("Start Game");
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("poggers");
            }
        });
        start.setFont(new Font("Georgia", 25));
        center.getChildren().add(start);

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(center);

        Scene scene = new Scene(root, 800, 450);
        stage.setScene(scene);
        stage.setTitle("Dungeon Crawler");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
