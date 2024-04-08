package dominoes.dominoes.game;

public enum GameLayout {

    DOUBLE_SIX(6, 7);

    private final int size;
    private final int hand;

    GameLayout(int size, int hand) {
        this.size = size;
        this.hand = hand;
    }

    public int getSize() {
        return size;
    }

    public int getHand() {
        return hand;
    }

}
