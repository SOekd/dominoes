package dominoes.dominoes.tile;

import java.util.Objects;

public class Tile {

    private int left;

    private int right;

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

    public void invert() {
        int temp = left;
        left = right;
        right = temp;
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
