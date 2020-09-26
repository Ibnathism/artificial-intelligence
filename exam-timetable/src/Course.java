public class Course {
    private String value;
    private int numberOfStudents;

    public Course(String value, int numberOfStudents) {
        this.value = value;
        this.numberOfStudents = numberOfStudents;
    }
    public Course(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
