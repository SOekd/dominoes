package dominoes.dominoes.ui.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class MenuView extends Scene {
    public MenuView(double screenWidth, double screenHeight, ViewManager viewManager) {
        super(viewManager, screenWidth, screenHeight);
        Button playButton = new Button();
        playButton.setText("Novo Jogo");
        Button continueButton = new Button();
        continueButton.setText("Continuar");
        Button exitButton = new Button();
        exitButton.setText("Sair");
        VBox optionsContainer = new VBox();
        Label title = new Label("Dominoes");

        Group root = new Group();
        VBox background = new VBox();

        optionsContainer.getChildren().add(playButton);
        optionsContainer.getChildren().add(continueButton);
        optionsContainer.getChildren().add(exitButton);
        background.getChildren().add(title);
        background.getChildren().add(optionsContainer);

        playButton.setMinWidth(screenWidth / 3);
        continueButton.setMinWidth(screenWidth / 3);
        exitButton.setMinWidth(screenWidth / 3);
        background.setPrefSize(screenWidth, screenHeight);
        optionsContainer.setPrefSize((screenWidth / 3) + 20, (screenHeight / 3) + 20);

        optionsContainer.setAlignment(Pos.CENTER);
        playButton.setAlignment(Pos.TOP_CENTER);
        continueButton.setAlignment(Pos.CENTER);
        exitButton.setAlignment(Pos.BOTTOM_CENTER);
        title.setAlignment(Pos.TOP_CENTER);
        background.setAlignment(Pos.CENTER);

        optionsContainer.setBackground(Background.fill(Paint.valueOf("#41F062")));
        playButton.setBackground(Background.fill(Paint.valueOf("#3E49F0")));
        continueButton.setBackground(Background.fill(Paint.valueOf("#3E49F0")));
        exitButton.setBackground(Background.fill(Paint.valueOf("#F04933")));
        background.setBackground(Background.fill(Paint.valueOf("#A1EFA7")));
        title.setTextFill(Paint.valueOf("#FFFFFF"));

        title.setFont(new Font("Arial", 80));

        optionsContainer.setSpacing(20.0);
        optionsContainer.setPadding(new Insets(10, 10, 10, 10));
        background.setPadding(new Insets(30, 10, 30, 10));
        background.setSpacing(20.0);

        root.getChildren().add(background);

        playButton.setOnAction(event -> {
            viewManager.setGameScene(new GameView(screenWidth, screenHeight, viewManager));
            viewManager.changeToGame();
        });

        continueButton.setOnAction(event -> {
            viewManager.changeToGame();
        });

        exitButton.setOnAction(event -> viewManager.close());

        this.setRoot(root);
    }
}
