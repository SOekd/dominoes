package dominoes.dominoes.ai.impl;

import dominoes.dominoes.ai.ArtificialIntelligence;
import dominoes.dominoes.game.Game;
import dominoes.dominoes.game.GameDirection;
import dominoes.dominoes.player.Player;
import dominoes.dominoes.tile.Tile;
import dominoes.dominoes.util.tuple.Pair;

import java.util.Random;

public class RandomBasedArtificialIntelligence implements ArtificialIntelligence {

    private final Random random = new Random();

    @Override
    public void nextMove(Game game, Player player) {

        Pair<Tile, GameDirection> randomMove = getRandomMove(game, player);

        if (randomMove != null) {
            game.placeTile(player, randomMove.getLeft(), randomMove.getRight());
        }

        game.changeTurn();
    }

    private Pair<Tile, GameDirection> getRandomMove(Game game, Player player) {
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

        return nextMoves.get(random.nextInt(nextMoves.size()));
    }


}
