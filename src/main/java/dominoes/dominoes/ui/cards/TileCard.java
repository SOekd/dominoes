package dominoes.dominoes.ui.cards;

import dominoes.dominoes.tile.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class TileCard extends VBox {

    private final Tile tile;

    public TileCard(Tile tile, boolean sideways) {
        this.tile = tile;
        this.setAlignment(Pos.CENTER);
        Label rightLabel = new Label();
        rightLabel.setText(String.valueOf(tile.getRight()));
        rightLabel.setTextFill(Paint.valueOf("#000000"));

        Label leftLabel = new Label();
        leftLabel.setText(String.valueOf(tile.getLeft()));
        leftLabel.setTextFill(Paint.valueOf("#000000"));

        Rectangle separator = new Rectangle();
        separator.setFill(Paint.valueOf("#000000"));

        if (sideways) {
            HBox background = new HBox();
            background.setPrefSize(90, 40);
            background.setPadding(new Insets(5, 5, 5, 5));
            background.setBackground(Background.fill(Paint.valueOf("#FFFFFF")));
            separator.setHeight(30);
            separator.setWidth(3);

            background.getChildren().add(leftLabel);
            background.getChildren().add(separator);
            background.getChildren().add(rightLabel);
            background.setAlignment(Pos.CENTER);
            background.setSpacing(15);

            this.getChildren().add(background);
        } else {
            VBox background = new VBox();
            background.setPrefSize(40, 90);
            background.setPadding(new Insets(5, 5, 5, 5));
            background.setBackground(Background.fill(Paint.valueOf("#FFFFFF")));
            separator.setHeight(3);
            separator.setWidth(30);

            background.getChildren().add(leftLabel);
            background.getChildren().add(separator);
            background.getChildren().add(rightLabel);
            background.setAlignment(Pos.CENTER);
            background.setSpacing(10);

            this.getChildren().add(background);
        }
    }

    public Tile getTile() {
        return tile;
    }
}
