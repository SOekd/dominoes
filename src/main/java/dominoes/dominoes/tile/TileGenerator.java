package dominoes.dominoes.tile;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TileGenerator {

    public List<@NotNull Tile> generate(int pips) {
        List<Tile> tiles = new ArrayList<>();
        for (int right = 0; right <=  pips; right++) {
            for (int left = 0; left <=  pips; left++) {
                tiles.add(new Tile(right, left));
            }
        }

        return tiles;
    }

}
