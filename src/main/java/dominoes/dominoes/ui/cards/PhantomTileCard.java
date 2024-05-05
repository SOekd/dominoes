package dominoes.dominoes.ui.cards;

import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class PhantomTileCard extends VBox {

    public PhantomTileCard() {
        HBox background = new HBox();
        background.setBorder(Border.stroke(Paint.valueOf("#E0312B")));
        background.setPrefSize(90, 40);
        this.setAlignment(Pos.CENTER);
        getChildren().add(background);
    }

}