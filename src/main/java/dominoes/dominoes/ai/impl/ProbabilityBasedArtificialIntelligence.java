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
            System.out.printf("O BOT jogou: -| %s . %s |-- na %s %n",
                    move.getLeft().getLeft(),
                    move.getLeft().getRight(),
                    move.getRight() == GameDirection.RIGHT ? "direita" : "esquerda");
            game.placeTile(player, move.getLeft(), move.getRight());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        game.changeTurn();
    }

    private Map<Integer, Long> calculateNumberFrequency(Game game) {
        return game.getTiles().stream()
                .flatMap(tile -> Stream.of(tile.getLeft(), tile.getRight()))
                .collect(Collectors.groupingBy(number -> number, Collectors.counting()));
    }

    private Pair<Tile, GameDirection> findBestMove(List<Pair<Tile, GameDirection>> nextMoves, Map<Integer, Long> numberFrequency) {
        Pair<Tile, GameDirection> bestMove = null;
        long bestScore = Long.MAX_VALUE;

        for (Pair<Tile, GameDirection> move : nextMoves) {
            Tile tile = move.getLeft();
            long score = numberFrequency.getOrDefault(tile.getLeft(), 0L) + numberFrequency.getOrDefault(tile.getRight(), 0L);

            if (bestMove == null || score < bestScore) {
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

        return findBestMove(nextMoves, calculateNumberFrequency(game));
    }

}
