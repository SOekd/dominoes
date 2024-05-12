package dominoes.dominoes.ai.impl;

import dominoes.dominoes.ai.ArtificialIntelligence;
import dominoes.dominoes.game.Game;
import dominoes.dominoes.game.GameDirection;
import dominoes.dominoes.player.Player;
import dominoes.dominoes.tile.Tile;
import dominoes.dominoes.tile.TileGenerator;
import dominoes.dominoes.util.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProbabilityBasedArtificialIntelligence implements ArtificialIntelligence {


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


    private Map<Integer, Long> calculatePlayerNumberFrequency(Game game, Player player) {
        var tiles = TileGenerator.generate(game.getLayout().getSize());

        tiles.removeAll(game.getTiles());
        tiles.removeAll(player.getHand());

        return tiles.stream()
                .flatMap(tile -> tile.getLeft() == tile.getRight() ?
                        Stream.of(tile.getRight())
                        : Stream.of(tile.getLeft(), tile.getRight()))
                .collect(Collectors.groupingBy(number -> number, Collectors.counting()));
    }

    private Pair<Tile, GameDirection> findBestMove(Game game, List<Pair<Tile, GameDirection>> nextMoves, Map<Integer, Long> numberFrequency) {
        Pair<Tile, GameDirection> bestMove = null;
        double bestScore = Double.MAX_VALUE;

        System.out.println("Player");
        numberFrequency.forEach((key, value) -> System.out.println(key + " - " + value));

        for (Pair<Tile, GameDirection> move : nextMoves) {
            Tile tile = move.getLeft();
            double score = 0.0;

            var direction = move.getRight();
            Tile scoreTile = (direction == GameDirection.RIGHT) ? game.getTiles().peekLast() : game.getTiles().peekFirst();
            int tileMatchSide = (direction == GameDirection.RIGHT) ? scoreTile.getRight() : scoreTile.getLeft();
            int tileOppositeSide = (direction == GameDirection.RIGHT) ? tile.getLeft() : tile.getRight();

            if (tileMatchSide == tile.getRight()) {
                score = 1.0 / (numberFrequency.getOrDefault(tile.getRight(), 0L) + 1);
            }

            if (tileMatchSide == tile.getLeft()) {
                score = 1.0 / (numberFrequency.getOrDefault(tile.getLeft(), 0L) + 1);
            }

            if (tileOppositeSide == tile.getRight()) {
                score += (1.0 / (numberFrequency.getOrDefault(tile.getRight(), 0L) + 1)) ;
            }

            if (tileOppositeSide == tile.getLeft()) {
                score += (1.0 / (numberFrequency.getOrDefault(tile.getLeft(), 0L) + 1)) ;
            }

            if (bestMove == null || score > bestScore) {

                System.out.println("Best move: " + move + " - " + score);

                bestMove = move;
                bestScore = score;
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

        return findBestMove(game, nextMoves, calculatePlayerNumberFrequency(game, player));
    }

}
