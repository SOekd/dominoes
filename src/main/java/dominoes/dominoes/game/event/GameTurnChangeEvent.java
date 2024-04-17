package dominoes.dominoes.game.event;

import dominoes.dominoes.game.Game;

import java.util.Objects;

public class GameTurnChangeEvent {

    private final Game game;

    private final boolean turn;

    public GameTurnChangeEvent(Game game, boolean turn) {
        this.game = game;
        this.turn = turn;

    }

    public Game getGame() {
        return game;
    }

    public boolean isTurn() {
        return turn;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameTurnChangeEvent that = (GameTurnChangeEvent) o;
        return turn == that.turn && Objects.equals(game, that.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, turn);
    }

}
