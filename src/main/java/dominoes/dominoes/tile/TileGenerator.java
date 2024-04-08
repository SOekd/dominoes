package dominoes.dominoes.tile;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TileGenerator {

    private TileGenerator()  {
        throw new IllegalStateException();
    }

    public static List<@NotNull Tile> generate(int pips) {
        List<Tile> tiles = new ArrayList<>();
        for (int right = 0; right <=  pips; right++) {
            for (int left = right; left <=  pips; left++) {
                tiles.add(new Tile(right, left));
            }
        }
        return tiles;
    }

}
