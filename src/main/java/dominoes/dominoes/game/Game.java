package dominoes.dominoes.game;

import dominoes.dominoes.ai.ArtificialIntelligence;
import dominoes.dominoes.player.Player;
import dominoes.dominoes.tile.Tile;
import dominoes.dominoes.tile.TileGenerator;
import dominoes.dominoes.util.tuple.Pair;

import java.util.*;

public class Game {

    private final Deque<Tile> tiles = new ArrayDeque<>();
    private final Queue<Tile> availableTiles = new LinkedList<>();
    private final GameLayout layout;

    private final ArtificialIntelligence artificialIntelligence;

    private Player player;
    private Player bot;

    public Game(GameLayout layout, ArtificialIntelligence artificialIntelligence) {
        this.layout = layout;
        this.artificialIntelligence = artificialIntelligence;
    }

    public int getCurrentRightTile() {
        Tile currentTile = tiles.peek();

        if (currentTile == null)
            return -1;

        return currentTile.getAvailableSide();
    }

    public int getCurrentLeftTile() {
        Tile currentTile = tiles.element();

        if (currentTile == null)
            return -1;

        return currentTile.getAvailableSide();
    }

    private Player turn;

    public void init() {
        var generatedTiles = TileGenerator.generate(layout.getSize());
        Collections.shuffle(generatedTiles);
        availableTiles.addAll(generatedTiles);

        player = new Player(generateHand());
        bot = new Player(generateHand());

        turn = getFirstPlayer();
        if (turn == player) {

        }else{

        }

    }

    public boolean placeTile(Tile tile, GameDirection gameDirection) {
        if(tiles.isEmpty()){
            tiles.add(tile);
            return true;
        }

        Tile right = tiles.peekFirst();
        Tile left = tiles.peekLast();






    }


    private Pair<Player,Tile> getFirstPlayer() {
        Tile doubleBot = player.getHighestDoubleTile();
        Tile doublePlayer = bot.getHighestDoubleTile();

        if (doubleBot == null && doublePlayer == null) {
            if (player.getHighestTile().getWeight() >= bot.getHighestTile().getWeight()) {
                return Pair.of(player,player.getHighestTile());
            } else {
                return Pair.of(bot,bot.getHighestTile());
            }
        }

        if (doubleBot == null)
            return Pair.of(player,player.getHighestDoubleTile());

        if (doublePlayer == null)
            return Pair.of(bot,bot.getHighestDoubleTile());

        if (doublePlayer.getWeight() >= doubleBot.getWeight()) {
            return Pair.of(player,player.getHighestDoubleTile());
        } else {
            return Pair.of(bot,bot.getHighestDoubleTile());
        }
    }

    private List<Tile> generateHand() {
        List<Tile> hand = new ArrayList<>();
        for (int i = 0; i < layout.getHand(); i++) {
            hand.add(availableTiles.poll());
        }
        return hand;
    }

}
