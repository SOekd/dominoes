package dominoes.dominoes.ui.cards;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PhantomTileCard extends VBox {

    public PhantomTileCard() {
        HBox background = new HBox();
        background.setStyle("""
                -fx-border-color: #ff0000;
                -fx-border-width: 1;
                -fx-border-radius: 10;
        """);
        background.setPrefSize(90, 40);
        this.setAlignment(Pos.CENTER);
        getChildren().add(background);
    }

}