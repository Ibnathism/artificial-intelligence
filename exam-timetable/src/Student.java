import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private List<Node> myCourses;

    public Student(int id) {
        this.id = id;
        myCourses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMyCourses(List<Node> myCourses) {
        this.myCourses = myCourses;
    }

    public List<Node> getMyCourses() {
        return myCourses;
    }
}
