import java.util.Objects;

public class Move {
    String playerType;
    Block initPosition;
    Block finalPosition;

    public Move(String player, Block initPosition, Block finalPosition) {
        this.playerType = player;
        this.initPosition = initPosition;
        this.finalPosition = finalPosition;
    }

    public String getPlayerType() {
        return playerType;
    }

    @Override
    public String toString() {
        return "{" + playerType + ", " + initPosition + ", " + finalPosition + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return playerType.equals(move.playerType) && initPosition.equals(move.initPosition) && finalPosition.equals(move.finalPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerType, initPosition, finalPosition);
    }
}
