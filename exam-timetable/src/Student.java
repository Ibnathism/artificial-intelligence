import java.util.ArrayList;
import java.util.List;

public class Student {
    private List<Course> myCourses;

    public Student() {
        myCourses = new ArrayList<>();
    }

    public void setMyCourses(List<Course> myCourses) {
        this.myCourses = myCourses;
    }

    public List<Course> getMyCourses() {
        return myCourses;
    }
}
