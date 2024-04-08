module dominoes.dominoes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.jetbrains.annotations;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    opens dominoes.dominoes to javafx.fxml;
    exports dominoes.dominoes;
    exports dominoes.dominoes.ui.main;
    opens dominoes.dominoes.ui.main to javafx.fxml;
    exports dominoes.dominoes.tile;
    opens dominoes.dominoes.tile to javafx.fxml;
}