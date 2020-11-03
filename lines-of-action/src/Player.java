import java.util.ArrayList;
import java.util.List;

public class Player {
    private String type;
    private List<BoardPosition> boardPositionsOfMyType;

    public Player(String type, List<BoardPosition> boardPositions) {
        this.type = type;
        boardPositionsOfMyType = boardPositions;
    }


}
