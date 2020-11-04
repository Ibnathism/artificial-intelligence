import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LineOfAction {
    private TypesOfLoa type;
    int checkerCount;
    private List<Block> blocks;

    public LineOfAction(TypesOfLoa type) {
        this.type = type;
        this.checkerCount = 0;
        this.blocks = new ArrayList<>();
    }


    public List<Block> getBoardPositions() {
        return blocks;
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
                Objects.equals(blocks, that.blocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blocks);
    }

    @Override
    public String toString() {
        return "LOA(" + type + ") - " + blocks;
    }
}
