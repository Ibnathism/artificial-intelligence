import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class ExamTimetable {
    public static List<Course> getCourseList(String name){
        BufferedReader bufferedReader;
        File courseFile = new File(name);
        List<Course> courseList = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(courseFile));
            String str = bufferedReader.readLine();
            while (str!=null) {
                String[] temp = str.split(" ");
                Course course = new Course(temp[0], Integer.parseInt(temp[1]));
                courseList.add(course);
                str = bufferedReader.readLine();
            }
            //System.out.println(courseList.get(3).getNumberOfStudents());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return courseList;
    }
    public static List<Student> getStudentList(String name){
        BufferedReader bufferedReader;
        File studentFile = new File(name);
        List<Student> studentList = new ArrayList<>();
        try {

            bufferedReader = new BufferedReader(new FileReader(studentFile));
            String str = bufferedReader.readLine();

            while (str!=null) {
                String[] temp = str.split(" ");
                List<Course> myCourses = new ArrayList<>();
                Student student = new Student();
                for (String val:
                        temp) {
                    myCourses.add(new Course(val));
                }
                student.setMyCourses(myCourses);
                studentList.add(student);
                str = bufferedReader.readLine();
            }

            //System.out.println(studentList.get(3).getMyCourses().get(0).getValue());

        } catch (Exception e){
            e.printStackTrace();
        }
        return studentList;
    }
    public static List<Solution> getSolutionList(String name){
        BufferedReader bufferedReader;
        File solutionFile = new File(name);
        List<Solution> solutionList = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(solutionFile));
            String str = bufferedReader.readLine();
            while (str!=null) {
                String[] temp = str.trim().split(" ");
                Solution solution = new Solution(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
                solutionList.add(solution);
                str = bufferedReader.readLine();
            }

            //System.out.println(solutionList.get(3).getCourseNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return solutionList;
    }

    private static Graph initialize(String courseFileName, String studentFileName) {
        List<Course> courseList = ExamTimetable.getCourseList(courseFileName);
        List<Student> studentList = ExamTimetable.getStudentList(studentFileName);
        List<Node> nodes = new ArrayList<>();
        for (Course c:
                courseList) {
            nodes.add(new Node(c.getValue()));
        }
        Graph g = new Graph(nodes);
        //System.out.println(g.getMyNodes().size());
        g = g.addEdges(studentList);
        return g;
    }

    private static double calculatePenalty(Graph graph, List<Student> students) {
        double penalty = 0.0;
        int[] penaltyConstants = {16, 8, 4, 2, 1};
        for (Student student :
                students) {
            List<Course> myCourses = student.getMyCourses();
            int[] myExamDays = graph.getExamDays(myCourses);
            Arrays.sort(myExamDays);
            int myPenalty = 0;
            for (int i = 0; i < myExamDays.length - 1; i++) {
                if (myExamDays[i+1]!=-1 && myExamDays[i]!=-1){
                    int diff = myExamDays[i+1] - myExamDays[i];
                    if (diff>=1 && diff<=5) myPenalty += myPenalty + penaltyConstants[diff-1];
                }

            }
            penalty = penalty + myPenalty;

        }
        penalty = (penalty/students.size());
        return penalty;
    }

    public static void main(String[] args) {

        Graph graph1 = ExamTimetable.initialize("car-s-91.crs", "car-s-91.stu");
        List<Student> students1 = ExamTimetable.getStudentList("car-s-91.stu");
        int numberOfColors1 = graph1.colorGraphBrelaz();
        System.out.println("CAR91: "+numberOfColors1 + "    " + ExamTimetable.calculatePenalty(graph1, students1));

        Graph graph2 = ExamTimetable.initialize("car-f-92.crs", "car-f-92.stu");
        List<Student> students2 = ExamTimetable.getStudentList("car-f-92.stu");
        int numberOfColors2 = graph2.colorGraphBrelaz();
        System.out.println("CAR92: "+numberOfColors2+ "    " + ExamTimetable.calculatePenalty(graph2, students2));

        Graph graph3 = ExamTimetable.initialize("kfu-s-93.crs", "kfu-s-93.stu");
        List<Student> students3 = ExamTimetable.getStudentList("kfu-s-93.stu");
        int numberOfColors3 = graph3.colorGraphBrelaz();
        System.out.println("KFU93: "+numberOfColors3+ "    " + ExamTimetable.calculatePenalty(graph3, students3));

        Graph graph4 = ExamTimetable.initialize("tre-s-92.crs", "tre-s-92.stu");
        List<Student> students4 = ExamTimetable.getStudentList("tre-s-92.stu");
        int numberOfColors4 = graph4.colorGraphBrelaz();
        System.out.println("TRE92: "+numberOfColors4+ "    " + ExamTimetable.calculatePenalty(graph4, students4));

        Graph graph5 = ExamTimetable.initialize("yor-f-83.crs", "yor-f-83.stu");
        List<Student> students5 = ExamTimetable.getStudentList("yor-f-83.stu");
        int numberOfColors5 = graph5.colorGraphBrelaz();
        System.out.println("YOR83: "+numberOfColors5+ "    " + ExamTimetable.calculatePenalty(graph5, students5));




    }



}
