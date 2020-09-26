public class Course {
    private String value;
    private int numberOfStudents;
    private boolean isAddedToGraph;

    public Course(String value, int numberOfStudents, boolean isAddedToGraph) {
        this.value = value;
        this.numberOfStudents = numberOfStudents;
        this.isAddedToGraph = isAddedToGraph;
    }

    public Course(String value) {
        this.value = value;
    }

    public boolean isAddedToGraph() {
        return isAddedToGraph;
    }

    public void setAddedToGraph(boolean addedToGraph) {
        isAddedToGraph = addedToGraph;
    }



    public String getValue() {
        return value;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
