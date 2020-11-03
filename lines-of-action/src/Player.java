import java.util.ArrayList;
import java.util.List;

public class Player {
    private String type;
    private List<BoardPosition> boardPositionsOfMyType;

    public Player(String type) {
        this.type = type;
        boardPositionsOfMyType = new ArrayList<>();
    }

    public List<BoardPosition> getBoardPositionsOfMyType() {
        return boardPositionsOfMyType;
    }
}
