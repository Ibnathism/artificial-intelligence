import java.util.ArrayList;
import java.util.List;

public class Student {
    private List<Node> myCourses;

    public Student() {
        myCourses = new ArrayList<>();
    }

    public void setMyCourses(List<Node> myCourses) {
        this.myCourses = myCourses;
    }

    public List<Node> getMyCourses() {
        return myCourses;
    }
}
