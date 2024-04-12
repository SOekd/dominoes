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


    private boolean turn;

    public void init() {
        var generatedTiles = TileGenerator.generate(layout.getSize());
        Collections.shuffle(generatedTiles);
        availableTiles.addAll(generatedTiles);

        player = new Player(generateHand());
        bot = new Player(generateHand());

        var firstPlayerInformation = getFirstPlayer();

        var firstTile = firstPlayerInformation.getRight();
        var firstPlayer = firstPlayerInformation.getLeft();

        turn = firstPlayer.equals(player);

        placeTile(firstPlayer, firstTile, GameDirection.RIGHT);

        turn = !turn;

    }


    public boolean placeTile(Player currentPlayer, Tile tile, GameDirection gameDirection) {

        if (currentPlayer == bot && turn)
            return false;

        if (currentPlayer == player && !turn)
            return false;

        if (tiles.isEmpty()) {
            tiles.add(tile);
            return true;
        }

        Tile acceptTile = gameDirection == GameDirection.RIGHT ? tiles.peekFirst() : tiles.peekLast();

        if (gameDirection == GameDirection.RIGHT) {
            if (acceptTile.getRight() == tile.getRight()) {
                tile.invert();
                tiles.add(tile);
                return true;
            } else if (acceptTile.getRight() == tile.getLeft()) {
                tiles.add(tile);
                return true;
            }
            return false;
        }


        if (gameDirection == GameDirection.LEFT) {
            if (acceptTile.getLeft() == tile.getLeft()) {
                tile.invert();
                tiles.add(tile);
                return true;
            } else if (acceptTile.getLeft() == tile.getRight()) {
                tiles.add(tile);
                return true;
            }
            return false;
        }

        return false;
    }


    private Pair<Player, Tile> getFirstPlayer() {
        Tile doubleBot = player.getHighestDoubleTile();
        Tile doublePlayer = bot.getHighestDoubleTile();

        System.out.println("Player TIle: " + doublePlayer);
        System.out.println("Bot TIle: " + doubleBot);

        if (doubleBot == null && doublePlayer == null) {
            if (player.getHighestTile().getWeight() >= bot.getHighestTile().getWeight()) {
                return Pair.of(player, player.getHighestTile());
            } else {
                return Pair.of(bot, bot.getHighestTile());
            }
        }

        if (doubleBot == null)
            return Pair.of(player, player.getHighestDoubleTile());

        if (doublePlayer == null)
            return Pair.of(bot, bot.getHighestDoubleTile());


        if (doublePlayer.getWeight() >= doubleBot.getWeight()) {
            return Pair.of(player, doublePlayer);
        } else {
            return Pair.of(bot, doubleBot);
        }
    }

    public void render() {

        System.out.println("Vez atual: " + (turn ? "PLAYER" : "BOT"));

        tiles.forEach(tile -> System.out.printf("-| %s . %s |-", tile.getLeft(), tile.getRight()));

        System.out.println("\n");

        System.out.println("Suas peças:");
        player.getHand().forEach(tile -> System.out.printf("-| %s . %s |-", tile.getLeft(), tile.getRight()));
    }

    private List<Tile> generateHand() {
        List<Tile> hand = new ArrayList<>();
        for (int i = 0; i < layout.getHand(); i++) {
            hand.add(availableTiles.poll());
        }
        return hand;
    }

    public boolean buy(Player player) {
        if (availableTiles.isEmpty())
            return false;

        player.getHand().add(availableTiles.poll());
        return true;
    }

    public Deque<Tile> getTiles() {
        return tiles;
    }
}
