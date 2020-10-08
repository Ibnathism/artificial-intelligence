import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;



public class ExamTimetable {
    private static List<Node> getCourseList(String name){
        BufferedReader bufferedReader;
        File courseFile = new File(name);
        List<Node> nodes = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(courseFile));
            String str = bufferedReader.readLine();
            while (str!=null) {
                String[] temp = str.trim().split(" ");
                Node node = new Node(temp[0], Integer.parseInt(temp[1]));
                nodes.add(node);
                str = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nodes;
    }
    private static List<Student> getStudentList(String name){
        BufferedReader bufferedReader;
        File studentFile = new File(name);
        List<Student> studentList = new ArrayList<>();
        try {

            bufferedReader = new BufferedReader(new FileReader(studentFile));
            String str = bufferedReader.readLine();
            int index = 0;
            while (str!=null) {
                String[] temp = str.trim().split(" ");
                List<Node> nodes = new ArrayList<>();
                Student student = new Student(index);
                for (String val:
                        temp) {
                    nodes.add(new Node(val));
                }
                student.setMyCourses(nodes);
                studentList.add(student);
                str = bufferedReader.readLine();
                index++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return studentList;
    }
    private static List<Solution> getSolutionList(String name){
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

    private static void showOutputs(int scheme, String courseFileName, String studentFileName, String dataset) {
        Graph graph = ExamTimetable.initialize(courseFileName, studentFileName);
        List<Student> students = ExamTimetable.getStudentList(studentFileName);
        int numberOfColors = 0;
        double penalty;
        int n = 10000;

        if (scheme==1){             //LargestDegree
            numberOfColors = graph.colorGraphLargestDegree();

        }
        else if (scheme==2){    //BrelazHeuristic
            numberOfColors = graph.colorGraphBrelaz();
            /*List<Node> nodes = graph.getMyNodes();
            for (Node node :
                    nodes) {
                System.out.println(node.getName()+" "+node.getColor());
            }*/
        }
        System.out.println(dataset+": "+numberOfColors + "    "+graph.calculatePenalty(students, "AllPair"));
        penalty = graph.applyKempe(students, "AllPair", n);
        System.out.println("Penalty after applying kempe chain :    "+penalty);
    }

    public static void main(String[] args) {
        /*System.out.println("Scheme1 LargestDegree");
        System.out.println();;
        ExamTimetable.showOutputs(1, "car-s-91.crs", "car-s-91.stu", "CAR91");
        ExamTimetable.showOutputs(1, "car-f-92.crs", "car-f-92.stu", "CAR92");
        ExamTimetable.showOutputs(1, "kfu-s-93.crs", "kfu-s-93.stu", "KFU93");
        ExamTimetable.showOutputs(1, "tre-s-92.crs", "tre-s-92.stu", "TRE92");
        ExamTimetable.showOutputs(1, "yor-f-83.crs", "yor-f-83.stu", "YOR83");
        System.out.println();
        System.out.println();*/
        System.out.println("Scheme2  Brelaz");
        System.out.println();
        ExamTimetable.showOutputs(2, "car-s-91.crs", "car-s-91.stu", "CAR91");
        //ExamTimetable.showOutputs(2, "car-f-92.crs", "car-f-92.stu", "CAR92");
        //ExamTimetable.showOutputs(2, "kfu-s-93.crs", "kfu-s-93.stu", "KFU93");
        //ExamTimetable.showOutputs(2, "tre-s-92.crs", "tre-s-92.stu", "TRE92");
        //ExamTimetable.showOutputs(2, "yor-f-83.crs", "yor-f-83.stu", "YOR83");




    }

}
