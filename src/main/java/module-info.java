module dominoes.dominoes {
    requires javafx.controls;
    requires org.jetbrains.annotations;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.feather;
    requires org.kordamp.ikonli.material2;
    requires com.google.common;
    requires atlantafx.base;


    exports dominoes.dominoes;
    exports dominoes.dominoes.ui.views;
    exports dominoes.dominoes.tile;
    exports dominoes.dominoes.ai;
    exports dominoes.dominoes.ui.cards;
    exports dominoes.dominoes.game;
    exports dominoes.dominoes.player;
    exports dominoes.dominoes.util.tuple;
}