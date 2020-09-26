import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

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
    private static Node checkGraphAndAdd(List<Course> courseList, int i, List<Node> graph, List<Course> myCourses) {
        Node node = null;
        for (Course c: courseList) {
            if (c.getValue().equals(myCourses.get(i).getValue())) myCourses.get(i).setAddedToGraph(c.isAddedToGraph());
        }

        if (!myCourses.get(i).isAddedToGraph()) {
            node = new Node(myCourses.get(i).getValue(), false);
        }
        else{
            for (Node n: graph) {
                if (myCourses.get(i).getValue().equals(n.getName())) node = n;
            }
        }
        return node;
    }
    public static List<Node> initialize(String courseFileName, String studentFileName, String solutionFileName){

        List<Course> courseList = ExamTimetable.getCourseList(courseFileName);
        List<Student> studentList = ExamTimetable.getStudentList(studentFileName);
        List<Solution> solutionList = ExamTimetable.getSolutionList(solutionFileName);



        List<Node> graph = new ArrayList<>();
        for (Student student:
             studentList) {

            List<Course> myCourses = student.getMyCourses();
            //System.out.println("Student-> NumberOfCourses:  "+ myCourses.size());
            for (int i = 0; i < myCourses.size()-1; i++) {
                Node mainNode = ExamTimetable.checkGraphAndAdd(courseList, i, graph, myCourses);
                mainNode.setColored(false);
                mainNode.setColor(-1);
                for (int j = 1; j < myCourses.size(); j++) {
                    Node subNode = ExamTimetable.checkGraphAndAdd(courseList, j, graph, myCourses);
                    subNode.setColored(false);
                    subNode.setColor(-1);

                    mainNode.addNeighbour(subNode);
                    subNode.addNeighbour(mainNode);

                }
            }
        }
        return graph;
    }



    //static int index=0;
    public static Node pickNodeSerially(Graph graph){
        //index++;
        if (!graph.getPq().isEmpty()) return graph.getPq().poll();
        else return null;
    }
    /*public static int colorGraph(Graph graph){
        for (Node n: graph.getPq()) {
            n.setColored(false);
            n.setColor(-1);
        }
        System.out.println("Graph PriorityQueue Size : " + graph.getPq().size());
        boolean[] availableColors = new boolean[graph.getPq().size()];
        Arrays.fill(availableColors, true);
        int numberOfNodes = graph.getPq().size();

        int index = 0;
        while (index<numberOfNodes) {
            Node n = ExamTimetable.pickNodeSerially(graph);
             if (n!=null){
                 System.out.println("Index : "+index+"  Node : "+n.getName()+"  Degree : "+n.getNeighbours().size() + "  SatDegree : "+n.getSaturationDegree());

                 PriorityQueue<Node> myNeighbours = n.getNeighbours();
                if (!n.isColored()) {
                    for (Node neighbour: myNeighbours) {
                        if (neighbour.isColored()) availableColors[neighbour.getColor()] = false;
                    }
                    for (int i = 0; i < availableColors.length ; i++) {
                        if (availableColors[i]) {
                            n.setColored(true);
                            n.setColor(i);
                            availableColors[i] = false;
                        }
                    }
                }
            }
            index++;
        }
        int count=0;
        for (int i = 0; i < availableColors.length; i++) {
            if (!availableColors[i]) count++;
        }
        System.out.println("Color count : "+count);
        return count;

    }*/

    public static int colorGraph(List<Node> graph){
        for (Node n: graph) {
            n.setColored(false);
            n.setColor(-1);
        }
        System.out.println("Graph Size : " + graph.size());
        boolean[] availableColors = new boolean[graph.size()];
        Arrays.fill(availableColors, true);
        int numberOfNodes = graph.size();

        int index = 0;
        while (index<numberOfNodes) {
            //Node n = ExamTimetable.pickNodeSerially(graph);
            Node n = graph.get(index);
            if (n!=null){
                System.out.println("Index : "+index+"  Node : "+n.getName()+"  Degree : "+n.getNeighbours().size() + "  SatDegree : "+n.getSaturationDegree());

                PriorityQueue<Node> myNeighbours = n.getNeighbours();
                if (!n.isColored()) {
                    for (Node neighbour: myNeighbours) {
                        if (neighbour.isColored()) availableColors[neighbour.getColor()] = false;
                    }
                    for (int i = 0; i < availableColors.length ; i++) {
                        if (availableColors[i]) {
                            n.setColored(true);
                            n.setColor(i);
                            availableColors[i] = false;
                        }
                    }
                }
            }
            index++;
        }
        int count=0;
        for (int i = 0; i < availableColors.length; i++) {
            if (!availableColors[i]) count++;
        }
        System.out.println("Color count : "+count);
        return count;

    }




    public static void main(String[] args) {
        List<Node> graph = ExamTimetable.initialize("yor-f-83.crs", "yor-f-83.stu", "yor83.sol");
        ExamTimetable.colorGraph(graph);
    }

}
