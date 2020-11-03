import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LineOfAction {
    private TypesOfLoa type;
    int checkerCount;
    private List<BoardPosition> boardPositions;

    public LineOfAction(TypesOfLoa type) {
        this.type = type;
        this.checkerCount = 0;
        this.boardPositions = new ArrayList<>();
    }


    public List<BoardPosition> getBoardPositions() {
        return boardPositions;
    }

    public TypesOfLoa getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineOfAction that = (LineOfAction) o;
        return type == that.type &&
                Objects.equals(boardPositions, that.boardPositions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardPositions);
    }

    @Override
    public String toString() {
        return "LOA(" + type + ") - " + boardPositions;
    }
}
