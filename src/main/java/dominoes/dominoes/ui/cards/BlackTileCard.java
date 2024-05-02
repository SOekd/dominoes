package dominoes.dominoes.ui.cards;

import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class BlackTileCard extends VBox {

    public BlackTileCard() {
        VBox background = new VBox();
        background.setBorder(Border.stroke(Paint.valueOf("#FFFFFF")));
        background.setPrefSize(40,90);
        this.setAlignment(Pos.CENTER);
        getChildren().add(background);
    }

}
