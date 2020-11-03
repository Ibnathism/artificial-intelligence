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


}
