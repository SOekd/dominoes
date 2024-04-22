package dominoes.dominoes.ai.impl;

import dominoes.dominoes.ai.ArtificialIntelligence;
import dominoes.dominoes.game.Game;
import dominoes.dominoes.game.GameDirection;
import dominoes.dominoes.player.Player;
import dominoes.dominoes.tile.Tile;
import dominoes.dominoes.util.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProbabilityBasedArtificialIntelligence implements ArtificialIntelligence {

    @Override
    public void nextMove(Game game, Player player) {

        var move = getMove(game, player);

        if (move != null) {
            System.out.printf("O BOT jogou: -| %s . %s |- na %s %n",
                    move.getLeft().getLeft(),
                    move.getLeft().getRight(),
                    move.getRight() == GameDirection.RIGHT ? "direita" : "esquerda");
            game.placeTile(player, move.getLeft(), move.getRight());
        }

        game.changeTurn();
    }

    private Map<Integer, Long> calculateNumberFrequency(Game game) {
        return game.getTiles().stream()
                .flatMap(tile -> tile.getLeft() == tile.getRight() ?
                        Stream.of(tile.getRight())
                        : Stream.of(tile.getLeft(), tile.getRight()))
                .collect(Collectors.groupingBy(number -> number, Collectors.counting()));
    }

    private Pair<Tile, GameDirection> findBestMove(Game game, List<Pair<Tile, GameDirection>> nextMoves, Map<Integer, Long> numberFrequency) {
        Pair<Tile, GameDirection> bestMove = null;
        long bestScore = Long.MAX_VALUE;

        numberFrequency.forEach((key, value) -> System.out.println(key + " - " + value));

//        System.out.println("Next Moves: ");
//        nextMoves.forEach(System.out::println);
//
//        System.out.println("Played Moves:");
//        game.getTiles().forEach(System.out::println);

//        5 0
//        5 1
//        5 2
//        5 3
//        5 4
//        5 5
//        5 6


        for (Pair<Tile, GameDirection> move : nextMoves) {
            Tile tile = move.getLeft();
            long score = 0;

            var direction = move.getRight();
            if (direction == GameDirection.RIGHT) {
                var scoreTile = game.getTiles().peekLast();

                if (scoreTile.getRight() == tile.getRight()) {
                    score = numberFrequency.getOrDefault(tile.getRight(), 0L);
                }

                if (scoreTile.getRight() == tile.getLeft()) {
                    score = numberFrequency.getOrDefault(tile.getLeft(), 0L);
                }

            }

            if (direction == GameDirection.LEFT) {
                var scoreTile = game.getTiles().peekFirst();

                if (scoreTile.getLeft() == tile.getRight()) {
                    score = numberFrequency.getOrDefault(tile.getRight(), 0L);
                }

                if (scoreTile.getLeft() == tile.getLeft()) {
                    score = numberFrequency.getOrDefault(tile.getLeft(), 0L);
                }

            }

            if (bestMove == null || score < bestScore) {

                bestMove = move;
                bestScore = score;

//                System.out.println();
//                System.out.println("Move: " + bestMove);
//                System.out.println("SCore: " + score);
//                System.out.println("Best SCore: " + bestScore);
            }
        }

        return bestMove;
    }


    private Pair<Tile, GameDirection> getMove(Game game, Player player) {
        var nextMoves = game.nextMoves(player);

        if (nextMoves.isEmpty()) {

            while ((nextMoves = game.nextMoves(player)).isEmpty()) {
                if (!game.buy(player))
                    break;
            }

            if (nextMoves.isEmpty()) {
                game.changeTurn();
                return null;
            }

        }

        return findBestMove(game, nextMoves, calculateNumberFrequency(game));
    }

}
