package dominoes.dominoes.ui.views;

import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import dominoes.dominoes.ai.ArtificialIntelligenceType;
import dominoes.dominoes.game.EndGameState;
import dominoes.dominoes.game.Game;
import dominoes.dominoes.game.GameDirection;
import dominoes.dominoes.game.GameLayout;
import dominoes.dominoes.tile.Tile;
import dominoes.dominoes.ui.cards.BlackTileCard;
import dominoes.dominoes.ui.cards.PhantomTileCard;
import dominoes.dominoes.ui.cards.TileCard;
import dominoes.dominoes.ui.handlers.BoundsHandler;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameView extends Scene {

    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();

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
        game.getArtificialIntelligence().setListener((game, move) -> {

            Notification wait = new Notification("""
                    O Bot está pensando! Aguarde!
                    """,
                    new FontIcon(Material2OutlinedAL.GAMES)
            );

            wait.getStyleClass().addAll(
                    Styles.ACCENT, Styles.ELEVATED_1
            );
            wait.setPrefHeight(Region.USE_PREF_SIZE);
            wait.setMaxHeight(Region.USE_PREF_SIZE);
            StackPane.setAlignment(wait, Pos.TOP_RIGHT);
            StackPane.setMargin(wait, new Insets(10, 10, 0, 0));

            Platform.runLater(() -> {
                var in1 = Animations.slideInDown(wait, Duration.millis(250));
                if (!root.getChildren().contains(wait)) {
                    root.getChildren().add(wait);
                }
                in1.playFromStart();
            });

            SCHEDULER.schedule(() -> {

                Platform.runLater(() -> {
                    if (move != null) {
                        var tile = move.getLeft();
                        var direction = move.getRight();
                        wait.setMessage("""
                                O BOT fez seu movimento!
                                                            
                                Jogou %s - %s na %s
                                """.formatted(tile.getRight(), tile.getLeft(), direction == GameDirection.RIGHT ? "Direita" : "Esquerda"));
                    } else {
                        wait.setMessage("O BOT passou a vez!");
                    }

                });
                if (move != null) {
                    System.out.printf("O BOT jogou: -| %s . %s |- na %s %n",
                            move.getLeft().getLeft(),
                            move.getLeft().getRight(),
                            move.getRight() == GameDirection.RIGHT ? "direita" : "esquerda");
                    game.placeTile(game.getBot(), move.getLeft(), move.getRight());
                    game.changeTurn();
                    Platform.runLater(this::update);
                }

                System.out.println("SCHEDULE!!!");

                SCHEDULER.schedule(() -> {
                    Platform.runLater(() -> {
                        System.out.println(" REMVOE TEST ");
                        root.getChildren().forEach(it -> System.out.println(it.getClass()));
                        root.getChildren().remove(wait);
                        System.out.println("AFTER");
                        root.getChildren().forEach(it -> System.out.println(it.getClass()));
                        System.out.println("REMOVED");
                    });
                }, 2, TimeUnit.SECONDS);

            }, 2, TimeUnit.SECONDS);
        });
        game.init();

        boardItems.getChildren().add(0, leftPhantomTile);
        boardItems.getChildren().add(rightPhantomTile);

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
        if (game.getEndGameState() == EndGameState.BOT_WIN || game.getEndGameState() == EndGameState.TIE) {
            viewManager.setScoreView(new ScoreView(this.getWidth(), this.getHeight(), viewManager, game.getEndGameState()));
            viewManager.changeToScore();
        }


        playerHand.getChildren().clear();
        boardItems.getChildren().clear();
        botHand.getChildren().clear();

        for (int i = 0; i < game.getPlayer().getHand().size(); i++) {
            TileCard tilecard = new TileCard(game.getPlayer().getHand().get(i), false);
            playerHand.getChildren().add(tilecard);
            makeDraggable(tilecard);
        }

        for (int i = 0; i < game.getBot().getHand().size(); i++) {
            botHand.getChildren().add(new BlackTileCard());
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
//        boardItems.getChildren().addFirst(leftPhantomTile);
//        boardItems.getChildren().addLast(rightPhantomTile);
        boardItems.getChildren().add(0, leftPhantomTile);
        boardItems.getChildren().add(rightPhantomTile);
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
                        if (!game.changeTurn()) {
                            viewManager.setScoreView(new ScoreView(this.getWidth(), this.getHeight(), viewManager, game.getEndGameState()));
                            viewManager.changeToScore();
                        }
                        update();
                    }
                } else if (BoundsHandler.checkIntersection(mouseBounds, rightPhantomTile)) {
                    System.out.println("Direita");
                    if (game.placeTile(game.getPlayer(), tileCard.getTile(), GameDirection.RIGHT)) {
                        if (!game.changeTurn()) {
                            viewManager.setScoreView(new ScoreView(this.getWidth(), this.getHeight(), viewManager, game.getEndGameState()));
                            viewManager.changeToScore();
                        }
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
