package dominoes.dominoes.ai.impl;

import dominoes.dominoes.ai.ArtificialIntelligence;
import dominoes.dominoes.game.Game;
import dominoes.dominoes.player.Player;
import dominoes.dominoes.tile.Tile;
import dominoes.dominoes.tile.TileGenerator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class SearchBasedArtificialIntelligence implements ArtificialIntelligence {

    @Override
    public void nextMove(Game game, Player player) {

        var nextMoves = game.nextMoves(player);

        System.out.println("Next moves: " + getMostChanceTiles(game, player)
                .stream()
                .map(Tile::toString)
                .toList());

    }


    public List<Tile> getMostChanceTiles(Game game, Player player) {
        var allTiles = TileGenerator.generate(game.getLayout().getSize());

        var boardTiles = game.getTiles().stream().toList();

        var nextMoves = game.nextMoves(player);


    }


}
