package dominoes.dominoes.ui.views;

import dominoes.dominoes.game.EndGameState;
import dominoes.dominoes.game.Game;
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

public class ScoreView extends Scene {

    public ScoreView(Game game, double screenWidth, double screenHeight, ViewManager viewManager, EndGameState endGameState) {
        super(viewManager, screenWidth, screenHeight);

        Button playButton = new Button();
        playButton.setText("Novo Jogo");
        Button menuButton = new Button();
        menuButton.setText("Menu");
        VBox optionsContainer = new VBox();
        Label title = new Label();

        if (endGameState.equals(EndGameState.BOT_WIN)) {
            title.setText("DERROTA");
        } else if (endGameState.equals(EndGameState.PLAYER_WIN)) {
            title.setText("VITORIA");
        } else {
            int playerWin = game.getPlayer().getHand().stream()
                    .mapToInt(tile -> tile.getLeft() + tile.getRight())
                    .sum();
            int botWin = game.getBot().getHand().stream()
                    .mapToInt(tile -> tile.getLeft() + tile.getRight())
                    .sum();

            if (playerWin > botWin) {
                title.setText("DERROTA POR PONTUAÇÃO: " + playerWin + " x " + botWin);
            } else if (playerWin < botWin) {
                title.setText("VITORIA POR PONTUAÇÃO: " + playerWin + " x " + botWin);
            } else {
                title.setText("EMPATE");
            }
        }
        Group root = new Group();
        VBox background = new VBox();

        optionsContainer.getChildren().add(playButton);
        optionsContainer.getChildren().add(menuButton);
        background.getChildren().add(title);
        background.getChildren().add(optionsContainer);

        playButton.setMinWidth(screenWidth / 3);
        menuButton.setMinWidth(screenWidth / 3);
        background.setPrefSize(screenWidth, screenHeight);
        optionsContainer.setPrefSize((screenWidth / 3) + 20, (screenHeight / 3) + 20);

        optionsContainer.setAlignment(Pos.CENTER);
        playButton.setAlignment(Pos.TOP_CENTER);
        title.setAlignment(Pos.TOP_CENTER);
        background.setAlignment(Pos.CENTER);

        optionsContainer.setStyle("""
                -fx-border-color: #8B4513;
                -fx-border-width: 6;
                -fx-border-radius: 10;
                -fx-background-radius: 10;
                -fx-background-color: #193326;
        """);
        playButton.setBackground(Background.fill(Paint.valueOf("#3E49F0")));
        background.setBackground(Background.fill(Paint.valueOf("#247542")));
        title.setTextFill(Paint.valueOf("#FFFFFF"));

        title.setFont(new Font("Arial", endGameState == EndGameState.TIE ? 20 : 60));

        optionsContainer.setSpacing(20.0);
        optionsContainer.setPadding(new Insets(10, 10, 10, 10));
        background.setPadding(new Insets(30, 10, 30, 10));
        background.setSpacing(20.0);

        root.getChildren().add(background);
        this.setRoot(root);

        playButton.setOnAction(event -> {
            viewManager.setGameScene(new GameView(screenWidth, screenHeight, viewManager));
            viewManager.changeToGame();
        });

        menuButton.setOnAction(event -> viewManager.changeToMenu());


    }
}
