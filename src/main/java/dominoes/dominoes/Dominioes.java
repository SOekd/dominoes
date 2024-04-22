package dominoes.dominoes;

import dominoes.dominoes.ai.ArtificialIntelligenceType;
import dominoes.dominoes.game.Game;
import dominoes.dominoes.game.GameDirection;
import dominoes.dominoes.game.GameLayout;
import dominoes.dominoes.tile.Tile;
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

        Game game = new Game(GameLayout.DOUBLE_SIX, ArtificialIntelligenceType.SEARCH.getArtificialIntelligence());

        game.setTurnChange((changedGame, player) -> {

            game.render();

            try (var scanner = new Scanner(System.in)) {
                while (true) {

                    var nextMoves = game.nextMoves(player);

                    if (nextMoves.isEmpty()) {
                        System.out.println("\nVocê não tem jogadas possíveis! Tentando comprar...");

                        while ((nextMoves = game.nextMoves(player)).isEmpty()) {
                            if (!game.buy(player))
                                break;
                        }

                        if (nextMoves.isEmpty()) {
                            System.out.println("Você não tem jogadas possíveis e não pode comprar! Passando a vez...");
                            game.changeTurn();
                            break;
                        }

                        game.render();
                        continue;
                    }

//                    System.out.println("\nPróximas jogadas possíveis: " + nextMoves.stream()
//                            .map(pair -> pair.getLeft() + " - " + pair.getRight())
//                            .reduce((s1, s2) -> s1 + ", " + s2)
//                            .orElse(""));

                    System.out.println("\nSua vez! Coloque o ID da peça que deseja jogar:");
                    int tileIndex = scanner.nextInt() - 1;

                    if (tileIndex < 0 || tileIndex >= player.getHand().size()) {
                        System.out.println("Não existe peça com esse ID");
                        continue;
                    }

                    Tile tile = player.getHand().get(tileIndex);
                    System.out.println("Next Tile: " + tile);

                    System.out.println("Escolha a direção da peça: 1 - Esquerda, 2 - Direita");
                    int direction = scanner.nextInt();

                    if (!game.placeTile(player, tile, direction == 1 ? GameDirection.LEFT : GameDirection.RIGHT)) {
                        System.out.println("Jogada inválida! Tente novamente");
                        continue;
                    }

                    System.out.println("change 1");
                    game.changeTurn();
                    break;
                }

            }

        });

        game.init();
    }

}