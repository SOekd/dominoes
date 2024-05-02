package dominoes.dominoes.ui.handlers;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public class BoundsHandler {

    public static boolean checkIntersection(Node source, Node target) {
        Bounds sourceBoundaries = source.localToScene(source.getBoundsInLocal());
        Bounds targerBoundaries = target.localToScene(target.getBoundsInLocal());
        return sourceBoundaries.intersects(targerBoundaries);
    }
}
