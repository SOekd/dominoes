package dominoes.dominoes;

import dominoes.dominoes.tile.Tile;
import dominoes.dominoes.tile.TileGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Dominioes extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Dominioes.class.getResource("dominoes-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dominó!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        int index = 1;
        for (Tile tile : TileGenerator.generate(6)) {
            System.out.println("%i - %s".formatted(index++ , tile));
        }

//        launch();
    }
}