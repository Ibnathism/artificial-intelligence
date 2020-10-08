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

    private static void applyScheme(String[] courseFileNames, String[] studentFileNames, int scheme, String[] datasets){
        for (int i = 4; i >= 0; i--) {
            ExamTimetable.showOutputs(scheme, courseFileNames[i], studentFileNames[i], datasets[i]);
        }
    }

    private static void showOutputs(int scheme, String courseFileName, String studentFileName, String dataset) {
        Graph graph = ExamTimetable.initialize(courseFileName, studentFileName);
        List<Student> students = ExamTimetable.getStudentList(studentFileName);
        int numberOfColors = 0;
        double penalty;
        int n = 1000;

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
        System.out.print(dataset+": "+numberOfColors);
        penalty = graph.applyKempe(students, "AllPair", n);
        //penalty = graph.applyPairSwap(students, "AllPair", n);
        System.out.print("    "+penalty);
        System.out.println();
    }

    public static void main(String[] args) {


        String[] courseFileNames = {"car-s-91.crs", "car-f-92.crs", "kfu-s-93.crs", "tre-s-92.crs", "yor-f-83.crs"};
        String[] studentFileNames = {"car-s-91.stu",  "car-f-92.stu", "kfu-s-93.stu", "tre-s-92.stu", "yor-f-83.stu"};
        String[] datasets = {"CAR91", "CAR92", "KFU93", "TRE92", "YOR83"};


       // int scheme = 1; //FOR largestDegree + kempeChain
        //System.out.println("Scheme1 LargestDegree + kempeChain");


        int scheme = 2; //FOR dsatur + kempeChain
        System.out.println("Scheme2 DSatur + kempeChain");


        ExamTimetable.applyScheme(courseFileNames, studentFileNames, scheme, datasets);



    }

}
