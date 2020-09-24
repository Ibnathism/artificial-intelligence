import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ExamTimetable {
    public static void main(String[] args) {
        BufferedReader bufferedReader;

        String courseFileName = "yor-f-83.crs";
        String studentFileName = "yor-f-83.stu";
        String solutionFileName = "yor83.sol";

        try {
            File courseFile = new File(courseFileName);
            bufferedReader = new BufferedReader(new FileReader(courseFile));
            String str = bufferedReader.readLine();
            List<Course> courseList = new ArrayList<>();
            while (str!=null) {
                String[] temp = str.split(" ");
                Course course = new Course(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
                courseList.add(course);
                str = bufferedReader.readLine();
            }
            System.out.println(courseList.get(3).getNumberOfStudents());


            File studentFile = new File(studentFileName);
            bufferedReader = new BufferedReader(new FileReader(studentFile));
            str = bufferedReader.readLine();
            List<Student> studentList = new ArrayList<>();
            while (str!=null) {
                String[] temp = str.split(" ");
                List<Course> myCourses = new ArrayList<>();
                Student student = new Student();
                for (String val:
                     temp) {
                    myCourses.add(new Course(Integer.parseInt(val)));
                }
                student.setMyCourses(myCourses);
                studentList.add(student);
                str = bufferedReader.readLine();
            }

            System.out.println(studentList.get(3).getMyCourses().get(0).getValue());

            File solutionFile = new File(solutionFileName);
            bufferedReader = new BufferedReader(new FileReader(solutionFile));
            str = bufferedReader.readLine();
            List<Solution> solutionList = new ArrayList<>();
            while (str!=null) {
                String[] temp = str.trim().split(" ");
                Solution solution = new Solution(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
                solutionList.add(solution);
                str = bufferedReader.readLine();
            }

            System.out.println(solutionList.get(3).getCourseNumber());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
