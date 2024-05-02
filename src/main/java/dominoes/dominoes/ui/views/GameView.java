package dominoes.dominoes.ui.views;

import dominoes.dominoes.ai.ArtificialIntelligenceType;
import dominoes.dominoes.game.Game;
import dominoes.dominoes.game.GameDirection;
import dominoes.dominoes.game.GameLayout;
import dominoes.dominoes.tile.Tile;
import dominoes.dominoes.ui.cards.PhantomTileCard;
import dominoes.dominoes.ui.cards.TileCard;
import dominoes.dominoes.ui.handlers.BoundsHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class GameView extends Scene {
    private final HBox playerHand = new HBox();
    private final HBox botHand = new HBox();
    private final ScrollPane board = new ScrollPane();

    private final ViewManager viewManager;

    private final HBox boardItems = new HBox();
    private final Game game;
    private final Group root = new Group();
    private final PhantomTileCard leftPhantomTile = new PhantomTileCard();
    private final PhantomTileCard rightPhantomTile = new PhantomTileCard();
    private double mouseX;
    private double mouseY;

    public GameView(double screenWidth, double screenHeight, ViewManager viewManager) {
        super(viewManager, screenWidth, screenHeight);
        this.viewManager = viewManager;
        VBox background = new VBox();
        background.setPrefSize(screenWidth, screenHeight);

        Button menuButton = new Button();
        Button buyButton = new Button();

        menuButton.setText("Sair");
        buyButton.setText("Comprar");

        HBox header = new HBox();

        header.getChildren().add(menuButton);
        header.getChildren().add(buyButton);

        board.setContent(boardItems);
        board.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        board.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        background.getChildren().add(header);
        background.getChildren().add(botHand);
        background.getChildren().add(board);
        background.getChildren().add(playerHand);

        playerHand.setAlignment(Pos.BOTTOM_CENTER);
        botHand.setAlignment(Pos.TOP_CENTER);
        boardItems.setAlignment(Pos.CENTER);
        menuButton.setAlignment(Pos.CENTER);
        buyButton.setAlignment(Pos.CENTER);

        playerHand.setPrefHeight(screenHeight / 4);
        botHand.setPrefHeight(screenHeight / 4);
        boardItems.setPrefHeight(screenHeight / 2 - 50);
        boardItems.setMinWidth(screenWidth - 42);
        boardItems.setPrefWidth(Region.USE_COMPUTED_SIZE);
        botHand.setMaxWidth(screenWidth);
        playerHand.setMaxWidth(screenWidth);
        board.setPrefSize(screenWidth, screenHeight / 2);

        menuButton.setPrefSize(100, 20);
        buyButton.setPrefSize(100, 20);
        header.setPrefSize(screenWidth, 40);
        header.setAlignment(Pos.CENTER);

        background.setBackground(Background.fill(Paint.valueOf("#A1EFA7")));
        botHand.setBackground(Background.fill(Paint.valueOf("#41F062")));
        playerHand.setBackground(Background.fill(Paint.valueOf("#41F062")));
        board.setBackground(Background.fill(Paint.valueOf("#A1EFA7")));
        menuButton.setBackground(Background.fill(Paint.valueOf("#F04933")));
        buyButton.setBackground(Background.fill(Paint.valueOf("#3E49F0")));

        Insets padding = new Insets(10, 10, 10, 10);

        header.setPadding(padding);
        background.setPadding(padding);
        botHand.setPadding(padding);
        playerHand.setPadding(padding);
        this.board.setPadding(padding);

        header.setSpacing(10.0);
        botHand.setSpacing(10.0);
        playerHand.setSpacing(10.0);
        boardItems.setSpacing(10.0);

        root.getChildren().add(background);
        this.setRoot(root);

        game = new Game(GameLayout.DOUBLE_SIX, ArtificialIntelligenceType.SEARCH.getArtificialIntelligence());
        game.init();

        boardItems.getChildren().addFirst(leftPhantomTile);
        boardItems.getChildren().addLast(rightPhantomTile);

        leftPhantomTile.setVisible(false);
        rightPhantomTile.setVisible(false);

        menuButton.setOnAction(event -> viewManager.changeToMenu());

        buyButton.setOnAction(event -> {
            game.buy(game.getPlayer());
            update();
        });

        this.board.setOnMouseDragged(e -> {
            mouseX = e.getSceneX() - this.board.getTranslateX();
            e.consume();
        });

        update();
    }

    public void update() {

        playerHand.getChildren().clear();
        boardItems.getChildren().clear();
        botHand.getChildren().clear();

        for (int i = 0; i < game.getPlayer().getHand().size(); i++) {
            TileCard tilecard = new TileCard(game.getPlayer().getHand().get(i), false);
            playerHand.getChildren().add(tilecard);
            makeDraggable(tilecard);
        }

        for (int i = 0; i < game.getBot().getHand().size(); i++) {
            botHand.getChildren().add(new TileCard(game.getBot().getHand().get(i), false));
        }

        List<Tile> boardTile = game.getTiles().stream().toList();
        for (int i = 0; i < game.getTiles().size(); i++) {
            if (boardTile.get(i).isDouble()) {
                boardItems.getChildren().add(new TileCard(boardTile.get(i), false));
            } else {
                boardItems.getChildren().add(new TileCard(boardTile.get(i), true));
            }
        }
        boardItems.getChildren().remove(leftPhantomTile);
        boardItems.getChildren().remove(rightPhantomTile);
        boardItems.getChildren().addFirst(leftPhantomTile);
        boardItems.getChildren().addLast(rightPhantomTile);
    }

    public void makeDraggable(TileCard tileCard) {

        tileCard.setOnMousePressed(e -> {
            mouseX = e.getSceneX() - tileCard.getTranslateX();
            mouseY = e.getSceneY() - tileCard.getTranslateY();
            e.consume();
        });

        tileCard.setOnMouseDragged(e -> {
            tileCard.setTranslateX(e.getSceneX() - mouseX);
            tileCard.setTranslateY(e.getSceneY() - mouseY);

            if (e.getSceneY() <= board.getLayoutY() + board.getHeight() &&
                    e.getSceneY() >= board.getLayoutY()) {
                leftPhantomTile.setVisible(true);
                rightPhantomTile.setVisible(true);
            } else {
                leftPhantomTile.setVisible(false);
                rightPhantomTile.setVisible(false);
            }
        });

        tileCard.setOnMouseReleased(e -> {
            if (leftPhantomTile.isVisible()) {
                Rectangle mouseBounds = new Rectangle();
                mouseBounds.setLayoutX(e.getSceneX());
                mouseBounds.setLayoutY(e.getSceneY());
                mouseBounds.setHeight(5);
                mouseBounds.setWidth(5);
                root.getChildren().add(mouseBounds);
                if (BoundsHandler.checkIntersection(mouseBounds, leftPhantomTile)) {
                    System.out.println("Esquerda");
                    if (game.placeTile(game.getPlayer(), tileCard.getTile(), GameDirection.LEFT)) {
                        if(!game.changeTurn()){

                        }
                        update();
                    }
                } else if (BoundsHandler.checkIntersection(mouseBounds, rightPhantomTile)) {
                    System.out.println("Direita");
                    if (game.placeTile(game.getPlayer(), tileCard.getTile(), GameDirection.RIGHT)) {
                        game.changeTurn();
                        update();
                    }
                }
                root.getChildren().remove(mouseBounds);
            }
            tileCard.setTranslateX(0);
            tileCard.setTranslateY(0);

            leftPhantomTile.setVisible(false);
            rightPhantomTile.setVisible(false);
        });
    }
}
