import java.util.ArrayList;
import java.util.List;

public class LineOfAction {
    private int checkerCount;
    private List<BoardPosition> boardPositions;

    public LineOfAction() {
        checkerCount = 0;
        boardPositions = new ArrayList<>();
    }

    public List<BoardPosition> getBoardPositions() {
        return boardPositions;
    }

}
