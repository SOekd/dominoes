package dominoes.dominoes.ai.impl;

import dominoes.dominoes.ai.ArtificialIntelligence;
import dominoes.dominoes.game.Game;
import dominoes.dominoes.game.GameDirection;
import dominoes.dominoes.player.Player;
import dominoes.dominoes.tile.Tile;
import dominoes.dominoes.util.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProbabilityBasedArtificialIntelligence implements ArtificialIntelligence {

    private static final double COEFFICIENT = 2;

    private BiConsumer<Game, Pair<Tile, GameDirection>> listener = null;

    @Override
    public void nextMove(Game game, Player player) {

        var move = getMove(game, player);

        if (listener != null)
            listener.accept(game, move);
    }

    @Override
    public void setListener(BiConsumer<Game, Pair<Tile, GameDirection>> listener) {
        this.listener = listener;
    }

    private Map<Integer, Long> calculateNumberFrequency(Game game, Player player) {
        return Stream.concat(game.getTiles().stream(), player.getHand().stream())
                .flatMap(tile -> tile.getLeft() == tile.getRight() ?
                        Stream.of(tile.getRight())
                        : Stream.of(tile.getLeft(), tile.getRight()))
                .collect(Collectors.groupingBy(number -> number, Collectors.counting()));
    }

    private Pair<Tile, GameDirection> findBestMove(Game game, List<Pair<Tile, GameDirection>> nextMoves, Map<Integer, Long> numberFrequency) {
        Pair<Tile, GameDirection> bestMove = null;
        double bestScore = Double.MAX_VALUE;

        numberFrequency.forEach((key, value) -> System.out.println(key + " - " + value));


        for (Pair<Tile, GameDirection> move : nextMoves) {
            Tile tile = move.getLeft();
            double score = 0.0;

            var direction = move.getRight();
            if (direction == GameDirection.RIGHT) {
                var scoreTile = game.getTiles().peekLast();

                if (scoreTile.getRight() == tile.getRight()) {
                    score = numberFrequency.getOrDefault(tile.getRight(), 0L) * COEFFICIENT
                            + numberFrequency.getOrDefault(tile.getLeft(), 0L);
                }

                if (scoreTile.getRight() == tile.getLeft()) {
                    score = numberFrequency.getOrDefault(tile.getLeft(), 0L) * COEFFICIENT
                            + numberFrequency.getOrDefault(tile.getRight(), 0L);
                }

            }

            if (direction == GameDirection.LEFT) {
                var scoreTile = game.getTiles().peekFirst();

                if (scoreTile.getLeft() == tile.getRight()) {
                    score = numberFrequency.getOrDefault(tile.getRight(), 0L) * COEFFICIENT
                            + numberFrequency.getOrDefault(tile.getLeft(), 0L);
                }

                if (scoreTile.getLeft() == tile.getLeft()) {
                    score = numberFrequency.getOrDefault(tile.getLeft(), 0L) * COEFFICIENT
                            + numberFrequency.getOrDefault(tile.getRight(), 0L);
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

        return findBestMove(game, nextMoves, calculateNumberFrequency(game, player));
    }

}
