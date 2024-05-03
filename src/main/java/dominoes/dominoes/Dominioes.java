package dominoes.dominoes;

import atlantafx.base.theme.PrimerDark;
import dominoes.dominoes.ui.views.GameView;
import dominoes.dominoes.ui.views.MenuView;
import dominoes.dominoes.ui.views.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Dominioes extends Application {

    private static final double screenWidth = 800;
    private static final double screenHeight = 600;

    @Override
    public void start(Stage stage){
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());

        ViewManager viewManager = new ViewManager(stage);
        MenuView menuView = new MenuView(screenWidth,screenHeight,viewManager);
        GameView gameView = new GameView(screenWidth,screenHeight,viewManager);
        viewManager.setGameScene(gameView);
        viewManager.setMenuScene(menuView);

        stage.setTitle("Dominó!");
        stage.setResizable(false);
        viewManager.changeToMenu();
    }

    public static void main(String[] args) {
        launch();
    }

}