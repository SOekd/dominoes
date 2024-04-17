package dominoes.dominoes;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dominoes.dominoes.ai.ArtificialIntelligence;
import dominoes.dominoes.ai.ArtificialIntelligenceType;
import dominoes.dominoes.game.Game;
import dominoes.dominoes.game.GameLayout;
import dominoes.dominoes.tile.Tile;
import dominoes.dominoes.tile.TileGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class Dominioes extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Dominioes.class.getResource("dominoes-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dominó!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

//        int index = 1;
//        for (Tile tile : TileGenerator.generate(6)) {
//            System.out.println("%s - %s".formatted(index++ , tile));
//        }
//        launch();

        Game game = new Game(GameLayout.DOUBLE_SIX, ArtificialIntelligenceType.RANDOM.getArtificialIntelligence());

        game.setTurnChange((changedGame, player) -> {

            game.render();

            try (var scanner = new Scanner(System.in)) {
                while (true) {

                    var nextMoves = game.nextMoves(player);

                    if (nextMoves.isEmpty()) {
                        System.out.println("Você não tem jogadas possíveis! Tentando comprar...");

                        while ((nextMoves = game.nextMoves(player)).isEmpty()) {
                            if (!game.buy(player))
                                break;
                        }

                        if (nextMoves.isEmpty()) {
                            System.out.println("Você não tem jogadas possíveis e não pode comprar! Passando a vez...");
                            game.changeTurn();
                            break;
                        }

                        continue;
                    }

                    System.out.println("\nSua vez! Coloque o ID da peça que deseja jogar:");
                    int tileIndex = scanner.nextInt() - 1;

                    if (tileIndex < 0 || tileIndex >= player.getHand().size()) {
                        System.out.println("Não existe peça com esse ID");
                        continue;
                    }


                    Tile tile = player.getHand().get(tileIndex);
                    System.out.println("Next Tile: " + tile);
                }

            }

        });

        game.init();

        game.render();


    }

}