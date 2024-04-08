package dominoes.dominoes.player;

import dominoes.dominoes.tile.Tile;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Player {

    private final List<Tile> hand;

    public Player(List<Tile> hand) {
        this.hand = hand;
    }

    public Tile getHighestDoubleTile() {
        return getDoubleTiles().stream()
                .max(Comparator.comparing(Tile::getWeight))
                .orElse(null);
    }

    public Tile getHighestTile() {
        return hand.stream()
                .max(Comparator.comparing(Tile::getWeight))
                .orElse(null);
    }

    public List<Tile> getDoubleTiles() {
        return hand.stream()
                .filter(Tile::isDouble)
                .toList();
    }

    public List<Tile> getHand() {
        return hand;
    }

    @Override
    public String toString() {
        return "Player{" +
                "hand=" + hand +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(hand, player.hand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hand);
    }
}
