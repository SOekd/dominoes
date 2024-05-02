package dominoes.dominoes.ui.views;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class ViewManager extends Parent {

    private final Stage stage;
    private MenuView menuScene;
    private GameView gameScene;

    private ScoreView scoreView;

    public ViewManager(Stage stage) {
        this.stage = stage;
    }

    public void changeToMenu() {
        stage.setScene(menuScene);
        stage.show();
    }

    public void changeToGame() {
        stage.setScene(gameScene);
        stage.show();
    }

    public void changeToScore(){
        stage.setScene(scoreView);
        stage.show();
    }
    public void close() {
        stage.close();
    }

    public void setMenuScene(MenuView menuScene) {
        this.menuScene = menuScene;
    }

    public void setGameScene(GameView gameScene) {
        this.gameScene = gameScene;
    }

    public void setScoreView(ScoreView scoreView) {this.scoreView = scoreView;}

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }


}
