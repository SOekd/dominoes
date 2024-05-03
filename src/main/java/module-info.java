module dominoes.dominoes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.jetbrains.annotations;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.feather;
    requires org.kordamp.ikonli.material2;
    requires com.google.common;
    requires atlantafx.base;


    opens dominoes.dominoes to javafx.fxml;
    exports dominoes.dominoes;
    exports dominoes.dominoes.ui.views;
    opens dominoes.dominoes.ui.views to javafx.fxml;
    exports dominoes.dominoes.tile;
    opens dominoes.dominoes.tile to javafx.fxml;
    exports dominoes.dominoes.ai;
    opens dominoes.dominoes.ai to javafx.fxml;
    exports dominoes.dominoes.ui.cards;
    opens dominoes.dominoes.ui.cards to javafx.fxml;
    exports dominoes.dominoes.game;
    opens dominoes.dominoes.game to javafx.fxml;
    exports dominoes.dominoes.player;
    opens dominoes.dominoes.player to javafx.fxml;
    exports dominoes.dominoes.util.tuple;
    opens dominoes.dominoes.util.tuple to javafx.fxml;
}