package dominoes.dominoes.ui.cards;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class BlackTileCard extends VBox {

    public BlackTileCard() {
        VBox background = new VBox();
        background.setStyle("""
                -fx-background-color: #FFFFFF;
                -fx-background-radius: 10;
        """);
        background.setPrefSize(40, 90);
        this.setAlignment(Pos.CENTER);
        getChildren().add(background);
    }

}
