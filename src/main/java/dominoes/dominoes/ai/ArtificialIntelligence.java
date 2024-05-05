package dominoes.dominoes.ai;

import dominoes.dominoes.game.Game;
import dominoes.dominoes.game.GameDirection;
import dominoes.dominoes.player.Player;
import dominoes.dominoes.tile.Tile;
import dominoes.dominoes.util.tuple.Pair;

import java.util.function.BiConsumer;

public interface ArtificialIntelligence {

    void nextMove(Game game, Player player);

    void setListener(BiConsumer<Game, Pair<Tile, GameDirection>> listener);

}
