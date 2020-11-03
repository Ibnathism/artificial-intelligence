public class Move {
    String playerType;
    BoardPosition initPosition;
    BoardPosition finalPosition;

    public Move(String player, BoardPosition initPosition, BoardPosition finalPosition) {
        this.playerType = player;
        this.initPosition = initPosition;
        this.finalPosition = finalPosition;
    }
}
