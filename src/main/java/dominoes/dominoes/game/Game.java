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

    private EndGameState endGameState = null;

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

        changeTurn();
    }

    public boolean changeTurn() {
        turn = !turn;

        if (isFinished()) {
            var winner = player.getHand().isEmpty() ? "PLAYER" : "BOT";
            if (player.getHand().isEmpty()) {
                endGameState = EndGameState.PLAYER_WIN;
            } else {
                endGameState = EndGameState.BOT_WIN;
            }
            return false;
        }

        if (tied()) {
            endGameState = EndGameState.TIE;
            return false;
        }

        if (isBotTurn()) {
            artificialIntelligence.nextMove(this, bot);
        }

        return true;
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

    public ArtificialIntelligence getArtificialIntelligence() {
        return artificialIntelligence;
    }

    public boolean placeTile(Player currentPlayer, Tile tile, GameDirection gameDirection) {

        if (currentPlayer == bot && turn)
            return false;

        if (currentPlayer == player && !turn)
            return false;

        if (tiles.isEmpty()) {
            tiles.add(tile);
            currentPlayer.getHand().remove(tile);
            return true;
        }

        Tile acceptTile = gameDirection == GameDirection.LEFT ? tiles.peekFirst() : tiles.peekLast();

        if (gameDirection == GameDirection.RIGHT) {
            if (acceptTile.getRight() == tile.getRight()) {
                tile.invert();
                tiles.addLast(tile);
                currentPlayer.getHand().remove(tile);
                return true;
            } else if (acceptTile.getRight() == tile.getLeft()) {
                tiles.addLast(tile);
                currentPlayer.getHand().remove(tile);
                return true;
            }
            return false;
        }


        if (gameDirection == GameDirection.LEFT) {
            if (acceptTile.getLeft() == tile.getLeft()) {
                tile.invert();
                tiles.addFirst(tile);
                currentPlayer.getHand().remove(tile);
                return true;
            } else if (acceptTile.getLeft() == tile.getRight()) {
                tiles.addFirst(tile);
                currentPlayer.getHand().remove(tile);
                return true;
            }
            return false;
        }

        return false;
    }

    public boolean tied() {
        return nextMoves(player).isEmpty() && nextMoves(bot).isEmpty() && availableTiles.isEmpty();
    }

    public List<Pair<Tile, GameDirection>> nextMoves(Player player) {
        List<Pair<Tile, GameDirection>> moves = new ArrayList<>();

        Tile firstTile = tiles.peekFirst();
        Tile lastTile = tiles.peekLast();

        for (Tile tile : player.getHand()) {

            if (firstTile.getLeft() == tile.getLeft() || firstTile.getLeft() == tile.getRight()) {
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


        if (doubleBot == null && doublePlayer == null) {
            if (player.getHighestTile().getWeight() >= bot.getHighestTile().getWeight()) {
                return Pair.of(player, player.getHighestTile());
            } else {
                return Pair.of(bot, bot.getHighestTile());
            }
        }

        if (doubleBot == null) {
            return Pair.of(player, player.getHighestDoubleTile());
        }

        if (doublePlayer == null) {
            return Pair.of(bot, bot.getHighestDoubleTile());
        }


        if (doublePlayer.getWeight() >= doubleBot.getWeight()) {
            return Pair.of(player, doublePlayer);
        } else {
            return Pair.of(bot, doubleBot);
        }
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

    public GameLayout getLayout() {
        return layout;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getBot() {
        return bot;
    }

    public Deque<Tile> getTiles() {
        return tiles;
    }

    public Queue<Tile> getAvailableTiles() {
        return availableTiles;
    }

    public EndGameState getEndGameState() {
        return endGameState;
    }
}
