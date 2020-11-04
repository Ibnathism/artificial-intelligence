public class Move {
    String playerType;
    Block initPosition;
    Block finalPosition;

    public Move(String player, Block initPosition, Block finalPosition) {
        this.playerType = player;
        this.initPosition = initPosition;
        this.finalPosition = finalPosition;
    }

    @Override
    public String toString() {
        return "{" + playerType + ", " + initPosition + ", " + finalPosition + "}";
    }
}
