import java.util.ArrayList;
import java.util.Objects;

public class Block {
    private LineOfAction horizontal;
    private LineOfAction vertical;
    private LineOfAction leadingDiagonal;
    private LineOfAction counterDiagonal;

    private int row;
    private int column;

    private String condition;

    public Block(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public LineOfAction findSpecificLoa(Block movedPosition) {
        if (horizontal.getBoardPositions().contains(movedPosition)) return horizontal;
        if (vertical.getBoardPositions().contains(movedPosition)) return vertical;
        if (leadingDiagonal.getBoardPositions().contains(movedPosition)) return leadingDiagonal;
        if (counterDiagonal.getBoardPositions().contains(movedPosition)) return counterDiagonal;
        return null;
    }

    public ArrayList<LineOfAction> findEffectedLoaList(LineOfAction specific) {
        ArrayList<LineOfAction> lineOfActions = new ArrayList<>();
        if (specific.getType().equals(TypesOfLoa.HORIZONTAL)) {
            lineOfActions.add(vertical);
            lineOfActions.add(leadingDiagonal);
            lineOfActions.add(counterDiagonal);
        }
        if (specific.getType().equals(TypesOfLoa.VERTICAL)) {
            lineOfActions.add(horizontal);
            lineOfActions.add(leadingDiagonal);
            lineOfActions.add(counterDiagonal);
        }
        if (specific.getType().equals(TypesOfLoa.LEADING_DIAGONAL)) {
            lineOfActions.add(horizontal);
            lineOfActions.add(vertical);
            lineOfActions.add(counterDiagonal);
        }
        if (specific.getType().equals(TypesOfLoa.COUNTER_DIAGONAL)) {
            lineOfActions.add(horizontal);
            lineOfActions.add(vertical);
            lineOfActions.add(leadingDiagonal);
        }
        return lineOfActions;
    }

    public void incrementCheckerCount() {
        horizontal.checkerCount++;
        vertical.checkerCount++;
        leadingDiagonal.checkerCount++;
        counterDiagonal.checkerCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block that = (Block) o;
        return row == that.row &&
                column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ", " + condition + ")";
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setHorizontal(LineOfAction horizontal) {
        this.horizontal = horizontal;
    }

    public void setVertical(LineOfAction vertical) {
        this.vertical = vertical;
    }

    public void setLeadingDiagonal(LineOfAction leadingDiagonal) {
        this.leadingDiagonal = leadingDiagonal;
    }

    public void setCounterDiagonal(LineOfAction counterDiagonal) {
        this.counterDiagonal = counterDiagonal;
    }

    public LineOfAction getHorizontal() {
        return horizontal;
    }

    public LineOfAction getVertical() {
        return vertical;
    }

    public LineOfAction getLeadingDiagonal() {
        return leadingDiagonal;
    }

    public LineOfAction getCounterDiagonal() {
        return counterDiagonal;
    }
}
