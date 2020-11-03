public class BoardPosition {
    private LineOfAction horizontal;
    private LineOfAction vertical;
    private LineOfAction leadingDiagonal;
    private LineOfAction counterDiagonal;

    private int row;
    private int column;

    public BoardPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public LineOfAction getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(LineOfAction horizontal) {
        this.horizontal = horizontal;
    }

    public LineOfAction getVertical() {
        return vertical;
    }

    public void setVertical(LineOfAction vertical) {
        this.vertical = vertical;
    }

    public LineOfAction getLeadingDiagonal() {
        return leadingDiagonal;
    }

    public void setLeadingDiagonal(LineOfAction leadingDiagonal) {
        this.leadingDiagonal = leadingDiagonal;
    }

    public LineOfAction getCounterDiagonal() {
        return counterDiagonal;
    }

    public void setCounterDiagonal(LineOfAction counterDiagonal) {
        this.counterDiagonal = counterDiagonal;
    }
}
