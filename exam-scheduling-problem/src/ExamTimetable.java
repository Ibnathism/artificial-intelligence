import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
                Course course = new Course(temp[0], Integer.parseInt(temp[1]), false);
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
    public static List<Node> initialize(String courseFileName, String studentFileName, String solutionFileName){

        List<Course> courseList = ExamTimetable.getCourseList(courseFileName);
        List<Student> studentList = ExamTimetable.getStudentList(studentFileName);
        List<Solution> solutionList = ExamTimetable.getSolutionList(solutionFileName);

        List<Node> graph = new ArrayList<>();

        for (Student s: studentList) {
            List<Course> courses = s.getMyCourses();
            for (Course courseOuter: courses) {
                Node mainNode = new Node();
                for (Course c: courseList) {
                    if (c.getValue().equals(courseOuter.getValue())) courseOuter.setAddedToGraph(c.isAddedToGraph());
                }
                if (!courseOuter.isAddedToGraph()) mainNode = new Node(courseOuter.getValue(), false);
                else{
                    for (Node n: graph) {
                        if (courseOuter.getValue().equals(n.getName())) mainNode = n;
                    }
                }
                for (Course courseInner: courses) {
                    Node subNode = new Node(courseInner.getValue(), false);
                    if (!mainNode.getName().equals(subNode.getName())) {
                        mainNode.addNeighbour(subNode);
                        subNode.addNeighbour(mainNode);
                    }
                }
                if (!courseOuter.isAddedToGraph()) {
                    graph.add(mainNode);
                    //courseOuter.setAddedToGraph(true);
                    for (Course c: courseList) {
                        if (c.getValue().equals(courseOuter.getValue())) {
                            c.setAddedToGraph(true);
                            courseOuter.setAddedToGraph(true);
                        }
                    }
                }

            }
        }
        //System.out.println(graph.get(3).getName());
        return graph;
    }

    static int index=0;
    static List<Integer> usedColors = new ArrayList<>();
    public static Node pickNode(List<Node> graph){
        index++;
        return graph.get(index);
    }

    public static List<Node> colorGraph(List<Node> graph){
        for (Node n:
             graph) {
            n.setColored(false);
            n.setColor(-1);
        }
        System.out.println("Graph Size : " + graph.size());
        boolean[] availableColors = new boolean[graph.size()];
        Arrays.fill(availableColors, true);
        for (Node n: graph) {
            List<Node> myNeighbours = n.getNeighbours();
            if (!n.isColored()) {
                for (Node neighbour:
                     myNeighbours) {
                    if (neighbour.isColored()) availableColors[neighbour.getColor()] = false;
                }
                for (int i = 0; i < availableColors.length ; i++) {
                    if (availableColors[i]) {
                        n.setColor(i);
                        availableColors[i] = false;
                    }
                }
            }
        }
        int count=0;
        for (int i = 0; i < availableColors.length; i++) {
            if (!availableColors[i]) count++;
        }
        System.out.println("Color count : "+count);
        return graph;

    }




    public static void main(String[] args) {
        List<Node> graph = ExamTimetable.initialize("yor-f-83.crs", "yor-f-83.stu", "yor83.sol");
        ExamTimetable.colorGraph(graph);
    }

}
