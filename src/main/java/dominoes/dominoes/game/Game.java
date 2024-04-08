package dominoes.dominoes.game;

import dominoes.dominoes.ai.ArtificialIntelligence;
import dominoes.dominoes.player.Player;
import dominoes.dominoes.tile.Tile;
import dominoes.dominoes.tile.TileGenerator;

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

    private boolean turn;

    public void init() {
        var generatedTiles = TileGenerator.generate(layout.getSize());
        Collections.shuffle(generatedTiles);
        availableTiles.addAll(generatedTiles);

        player = new Player(generateHand());
        bot = new Player(generateHand());

        turn = getFirstPlayer();
        // true = vez do player
        // false = bot
        if (!turn) {

        }

    }

    public void play(Tile tile) {

        if (turn) {
            turn =
        }

    }


    private Player getFirstPlayer() {
        Tile doubleBot = player.getHighestDoubleTile();
        Tile doublePlayer = bot.getHighestDoubleTile();

        if (doubleBot == null && doublePlayer == null) {
            if (player.getHighestTile().getWeight() >= bot.getHighestTile().getWeight()) {
                return player;
            } else {
                return bot;
            }
        }

        if (doubleBot == null)
            return player;

        if (doublePlayer == null)
            return bot;

        if (doublePlayer.getWeight() >= doubleBot.getWeight()) {
            return player;
        } else {
            return bot;
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
