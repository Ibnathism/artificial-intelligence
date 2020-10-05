import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;



public class ExamTimetable {
    public static List<Node> getCourseList(String name){
        BufferedReader bufferedReader;
        File courseFile = new File(name);
        List<Node> nodes = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(courseFile));
            String str = bufferedReader.readLine();
            while (str!=null) {
                String[] temp = str.split(" ");
                Node node = new Node(temp[0], Integer.parseInt(temp[1]));
                nodes.add(node);
                str = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nodes;
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
                List<Node> nodes = new ArrayList<>();
                Student student = new Student();
                for (String val:
                        temp) {
                    nodes.add(new Node(val));
                }
                student.setMyCourses(nodes);
                studentList.add(student);
                str = bufferedReader.readLine();
            }
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
        List<Student> studentList = ExamTimetable.getStudentList(studentFileName);
        List<Node> nodes = ExamTimetable.getCourseList(courseFileName);
        Graph g = new Graph(nodes);
        g = g.addEdges(studentList);
        //System.out.println("Course Size "+nodes.size()+"   Graph nodes size "+g.getMyNodes().size());
        return g;
    }

    public static void main(String[] args) {

        Graph graph1 = ExamTimetable.initialize("car-s-91.crs", "car-s-91.stu");
        List<Student> students1 = ExamTimetable.getStudentList("car-s-91.stu");
        int numberOfColors1 = graph1.colorGraphBrelaz();
        //int numberOfColors1 = graph1.colorGraphLargestDegree();
        System.out.println("CAR91: "+numberOfColors1 + "    " + graph1.calculatePenalty(students1));

        Graph graph2 = ExamTimetable.initialize("car-f-92.crs", "car-f-92.stu");
        List<Student> students2 = ExamTimetable.getStudentList("car-f-92.stu");
        int numberOfColors2 = graph2.colorGraphBrelaz();
        //int numberOfColors2 = graph2.colorGraphLargestDegree();
        System.out.println("CAR92: "+numberOfColors2+ "    " + graph2.calculatePenalty(students2));

        Graph graph3 = ExamTimetable.initialize("kfu-s-93.crs", "kfu-s-93.stu");
        List<Student> students3 = ExamTimetable.getStudentList("kfu-s-93.stu");
        int numberOfColors3 = graph3.colorGraphBrelaz();
        //int numberOfColors3 = graph3.colorGraphLargestDegree();
        System.out.println("KFU93: "+numberOfColors3+ "    " + graph3.calculatePenalty(students3));

        Graph graph4 = ExamTimetable.initialize("tre-s-92.crs", "tre-s-92.stu");
        List<Student> students4 = ExamTimetable.getStudentList("tre-s-92.stu");
        int numberOfColors4 = graph4.colorGraphBrelaz();
        //int numberOfColors4 = graph4.colorGraphLargestDegree();
        System.out.println("TRE92: "+numberOfColors4+ "    " + graph4.calculatePenalty(students4));

        Graph graph5 = ExamTimetable.initialize("yor-f-83.crs", "yor-f-83.stu");
        List<Student> students5 = ExamTimetable.getStudentList("yor-f-83.stu");
        int numberOfColors5 = graph5.colorGraphBrelaz();
        //int numberOfColors5 = graph5.colorGraphLargestDegree();
        System.out.println("YOR83: "+numberOfColors5+ "    " + graph5.calculatePenalty(students5));




    }

}
