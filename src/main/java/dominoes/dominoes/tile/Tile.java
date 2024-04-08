package dominoes.dominoes.tile;

import java.util.Objects;

public class Tile {

    private final int left;

    private final int right;

    private int fit;

    public Tile(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getFit() {
        return fit;
    }

    public void setFit(int fit) {
        this.fit = fit;
    }

    public int getAvailableSide() {
        if (fit == right)
            return left;
        else
            return right;
    }

    public int getWeight() {
        return left + right;
    }

    public boolean isDouble() {
        return right == left;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return left == tile.left && right == tile.right;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "Tile{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }

}
