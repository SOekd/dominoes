package dominoes.dominoes.game;

import dominoes.dominoes.ai.ArtificialIntelligence;
import dominoes.dominoes.player.Player;
import dominoes.dominoes.tile.Tile;
import dominoes.dominoes.tile.TileGenerator;
import dominoes.dominoes.util.tuple.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

public class Game {

    private final Deque<Tile> tiles = new ArrayDeque<>();
    private final Queue<Tile> availableTiles = new LinkedList<>();
    private final GameLayout layout;


    private final ArtificialIntelligence artificialIntelligence;

    private BiConsumer<Game, Player> onTurnChange = null;

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


        System.out.println("First Tile: " + firstTile);

        placeTile(firstPlayer, firstTile, GameDirection.RIGHT);

        changeTurn();
    }

    public void changeTurn() {
        turn = !turn;

        if (onTurnChange != null && isPlayerTurn())
            onTurnChange.accept(this, player);

        if (isBotTurn()) {
            artificialIntelligence.nextMove(this, bot);
        }
    }

    public boolean isFinished() {
        return player.getHand().isEmpty() || bot.getHand().isEmpty();
    }

    private boolean isPlayerTurn() {
        return turn;
    }

    private boolean isBotTurn() {
        return !turn;
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

    public void setTurnChange(BiConsumer<Game, Player> onTurnChange) {
        this.onTurnChange = onTurnChange;
    }

    public List<Pair<Tile, GameDirection>> nextMoves(Player player) {
        List<Pair<Tile, GameDirection>> moves = new ArrayList<>();

        for (Tile tile : player.getHand()) {

            Tile firstTile = tiles.peekFirst();
            Tile lastTile = tiles.peekLast();

            if (firstTile.getLeft() == tile.getLeft() || firstTile.getLeft() == tile.getRight()) {
                moves.add(Pair.of(tile, GameDirection.LEFT));
            }

            if (firstTile.getRight() == tile.getLeft() || firstTile.getRight() == tile.getRight()) {
                moves.add(Pair.of(tile, GameDirection.RIGHT));
            }

            if (lastTile.getLeft() == tile.getLeft() || lastTile.getLeft() == tile.getRight()) {
                moves.add(Pair.of(tile, GameDirection.LEFT));
            }

            if (lastTile.getRight() == tile.getLeft() || lastTile.getRight() == tile.getRight()) {
                moves.add(Pair.of(tile, GameDirection.RIGHT));
            }
        }

        return moves;
    }

    private Pair<Player, Tile> getFirstPlayer() {
        Tile doubleBot = bot.getHighestDoubleTile();
        Tile doublePlayer = player.getHighestDoubleTile();

        System.out.println("Double Bot: " + doubleBot);
        System.out.println("Double Player: " + doublePlayer);

        if (doubleBot == null && doublePlayer == null) {
            if (player.getHighestTile().getWeight() >= bot.getHighestTile().getWeight()) {
                System.out.println("Player tem a maior peça");
                return Pair.of(player, player.getHighestTile());
            } else {
                System.out.println("Bot tem a maior peça");
                return Pair.of(bot, bot.getHighestTile());
            }
        }

        if (doubleBot == null) {
            System.out.println("Bot tem a maior peça2");
            return Pair.of(player, player.getHighestDoubleTile());
        }

        if (doublePlayer == null) {
            System.out.println("Player tem a maior peça2");
            return Pair.of(bot, bot.getHighestDoubleTile());
        }


        if (doublePlayer.getWeight() >= doubleBot.getWeight()) {
            System.out.println("Player tem a maior peça3");
            return Pair.of(player, doublePlayer);
        } else {
            System.out.println("Bot tem a maior peça3");
            return Pair.of(bot, doubleBot);
        }
    }

    public void render() {
        System.out.println("Vez atual: " + (turn ? "PLAYER" : "BOT"));

        tiles.forEach(tile -> System.out.printf("-| %s . %s |-", tile.getLeft(), tile.getRight()));

        System.out.println("\n");

        System.out.println("Suas peças:");

        AtomicInteger index = new AtomicInteger(1);
        player.getHand().forEach(tile -> System.out.printf("-| %s . %s (id: %s) |-", tile.getLeft(), tile.getRight(), index.getAndIncrement()));
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
