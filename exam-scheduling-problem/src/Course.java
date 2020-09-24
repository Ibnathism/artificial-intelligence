public class Course {
    private int value;
    private int numberOfStudents;

    public Course(int value, int numberOfStudents) {
        this.value = value;
        this.numberOfStudents = numberOfStudents;
    }

    public Course(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
