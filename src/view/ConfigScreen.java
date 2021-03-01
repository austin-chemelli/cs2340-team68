package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ConfigScreen {
    private int width;
    private int height;
    private Label title;
    private String difficulty;
    private String weapon;
    private String name;
    private TextField nameField;
    private Button easy;
    private Button medium;
    private Button hard;
    private Button pistol;
    private Button rifle;
    private Button shotgun;
    private Button next;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Button getNext() {
        return next;
    };

    public String getName() {
        return name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public ConfigScreen(int width, int height) {
        this.width = width;
        this.height = height;
        difficulty = "";
        weapon = "";
        title = new Label("Configuration Screen");
        nameField = new TextField();
        easy = new Button("Easy");
        medium = new Button("Medium");
        hard = new Button("Hard");
        pistol = new Button("Pistol");
        rifle = new Button("Rifle");
        shotgun = new Button("Shotgun");
        next = new Button("Next");
    }

    public Scene getScene() {
        StackPane top = new StackPane();
        top.setPadding(new Insets(25, 0, 25, 0));
        top.setAlignment(Pos.CENTER);
        title.setFont(new Font("Georgia", 50));
        title.setAlignment(Pos.CENTER);
        top.getChildren().add(title);

        VBox items = new VBox();
        items.setAlignment(Pos.CENTER);

        HBox nameStuff = new HBox();
        nameStuff.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Name: ");
        nameLabel.setFont(new Font("Georgia", 25));
        nameField.setPrefWidth(width / 2);
        nameField.setFont(new Font("Georgia", 25));
        nameField.setId("nameField");
        Button setName = new Button("Set");
        setName.setFont(new Font("Georgia", 25));
        setName.setOnAction(e -> {
            name = nameField.getText();
            if (name.trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Name Error");
                alert.setContentText("Please enter a valid name.");

                alert.showAndWait();
            }
        });
        nameStuff.getChildren().addAll(nameLabel, nameField, setName);
        nameStuff.setSpacing(5);

        Text difficultyText = new Text("");
        difficultyText.setFont(new Font("Georgia", 25));
        HBox difficultyButtons = new HBox();
        difficultyButtons.setAlignment(Pos.CENTER);
        difficultyButtons.setPadding(new Insets(10, 5, 10, 5));
        easy.setOnAction(e -> {
            difficulty = "easy";
            difficultyText.setText("Your difficulty is " + difficulty);
        });
        easy.setFont(new Font("Georgia", 25));
        medium.setOnAction(e -> {
            difficulty = "medium";
            difficultyText.setText("Your difficulty is " + difficulty);
        });
        medium.setFont(new Font("Georgia", 25));
        hard.setOnAction(e -> {
            difficulty = "hard";
            difficultyText.setText("Your difficulty is " + difficulty);
        });
        hard.setFont(new Font("Georgia", 25));
        difficultyButtons.getChildren().addAll(easy, medium, hard);

        Text weaponText = new Text("");
        weaponText.setFont(new Font("Georgia", 25));
        HBox weaponButtons = new HBox();
        weaponButtons.setAlignment(Pos.CENTER);
        weaponButtons.setPadding(new Insets(10, 5, 10, 5));
        pistol.setOnAction(e -> {
            weapon = "pistol";
            weaponText.setText("Your weapon is a " + weapon);
        });
        pistol.setFont(new Font("Georgia", 25));
        rifle.setOnAction(e -> {
            weapon = "rifle";
            weaponText.setText("Your weapon is a " + weapon);
        });
        rifle.setFont(new Font("Georgia", 25));
        shotgun.setOnAction(e -> {
            weapon = "shotgun";
            weaponText.setText("Your weapon is a " + weapon);
        });
        shotgun.setFont(new Font("Georgia", 25));
        weaponButtons.getChildren().addAll(pistol, rifle, shotgun);

        next.setFont(new Font("Georgia", 25));
        items.getChildren().addAll(nameStuff, difficultyButtons, difficultyText,
                weaponButtons, weaponText, next);

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(items);
        Scene scene = new Scene(root, width, height);

        return scene;
    }

}
