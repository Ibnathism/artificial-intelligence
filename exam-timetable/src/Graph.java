import java.text.DecimalFormat;
import java.util.*;

class BrelazNodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node node, Node t1) {
        if (node.getSaturationDegree()<t1.getSaturationDegree()) return 1;
        else if (node.getSaturationDegree()>t1.getSaturationDegree()) return -1;
        else {
            return Integer.compare(t1.getMyNeighbours().size(), node.getMyNeighbours().size());
        }
    }
}
class LargestDegreeComparator implements Comparator<Node> {
    @Override
    public int compare(Node node, Node t1) {
        return Integer.compare(t1.getMyNeighbours().size(), node.getMyNeighbours().size());
    }
}



public class Graph {
    private final List<Node> myNodes;
    private final int totalNodes;
    //boolean[] usedColors;

    public Graph() {
        this.myNodes = new ArrayList<>();
        this.totalNodes = 0;
    }

    public Graph(List<Node> myNodes) {
        this.myNodes = myNodes;
        this.totalNodes = this.myNodes.size();
        //this.usedColors = new boolean[totalNodes];
    }

    public Node getNode(String name){

        for (Node n: myNodes) {
            //System.out.println(n.getName()+"   "+name);
            if (n.getName().equals(name)) return n;
        }
        return null;
    }

    public Graph addEdges(List<Student> studentList) {
        for (Student s: studentList) {
            List<Node> myCourses = s.getMyCourses();
            for (int i = 0; i < myCourses.size()-1 ; i++) {
                Node node1 = this.getNode(myCourses.get(i).getName());
                for (int j = 1; j < myCourses.size(); j++) {
                    Node node2 = this.getNode(myCourses.get(j).getName());
                    node1.addNeighbour(node2);
                    node2.addNeighbour(node1);
                }
            }
        }
        return this;
    }

    public List<Node> getMyNodes() {
        return myNodes;
    }

    private int getColoring(PriorityQueue<Node> pq) {
        int colors = 0;


        Node first = pq.poll();
        assert first != null;
        first.setColor(colors);
        //Arrays.fill(usedColors, false);
        while (!pq.isEmpty()) {
            //boolean[] availableColors = new boolean[totalNodes];
            //Arrays.fill(availableColors, true);
            Node temp = pq.poll();

            if (temp==null) break;
            else {
                //System.out.println(temp.getName());
                if (temp.getColor() == -1) {
                    //color temp

                    boolean isValid = false;
                    int color;
                    for (color = 0; color <= colors; color++) {
                        isValid = checkValidityOfColor(temp, color);
                        if (isValid) break;
                    }
                    if (isValid) temp.setColor(color);
                    else {
                        colors++;
                        temp.setColor(colors);
                    }
                    pq.add(temp);
                    pq.remove(temp);
                }

            }
        }
        /*for (int i = 0; i < totalNodes; i++) {
            if (usedColors[i]) count++;
        }*/
        return colors+1;
    }

    private boolean checkValidityOfColor(Node node, int color) {
        List<Node> neighbours = node.getMyNeighbours();
        for (Node n:neighbours){
            if (n.getColor()==color) return false;
        }
        return true;
    }

    public int colorGraphBrelaz() {
        PriorityQueue<Node> pq = new PriorityQueue<>(this.totalNodes, new BrelazNodeComparator());
        pq.addAll(myNodes);
        return this.getColoring(pq);
    }

    public int colorGraphLargestDegree() {
        PriorityQueue<Node> pq = new PriorityQueue<>(this.totalNodes, new LargestDegreeComparator());
        pq.addAll(myNodes);
        return this.getColoring(pq);
    }

    public double applyPairSwap(List<Student> students, String type, int n) {
        double myPenalty = calculatePenalty(students, type);
        for (int i = 0; i < n; i++) {
            Node node1 = chooseVertex(this.getMyNodes());
            Node node2 = chooseVertex(this.getMyNodes());
            int color1 = node1.getColor();
            int color2 = node2.getColor();

            if (color1!=color2) {

                boolean isValid1 = checkValidityOfPairSwap(node1, color2, node2);
                boolean isValid2 = checkValidityOfPairSwap(node2, color1, node1);

                if (isValid1 && isValid2){
                    node1.setColor(color2);
                    node2.setColor(color1);

                    double newPenalty = calculatePenalty(students, type);

                    if (newPenalty>=myPenalty) {
                        node1.setColor(color1);
                        node2.setColor(color2);
                    }
                    else {
                        //System.out.println("Penalty decreased " + newPenalty);
                        myPenalty = newPenalty;
                    }
                }



            }

        }
        return myPenalty;
    }

    private boolean checkValidityOfPairSwap(Node node, int color, Node other) {
        List<Node> neighbours = node.getMyNeighbours();
        for (Node n: neighbours){
            if (n.getColor()==color && !n.getName().equals(other.getName())) return false;
        }
        return true;
    }

    public double applyKempe(List<Student> students, String type, int n) {

        double myPenalty = calculatePenalty(students, type);

        for (int i = 0; i < n; i++) {
            Node startNode = chooseVertex(this.getMyNodes());
            int color1 = startNode.getColor();
            int color2 = 0;
            List<Node> startNodeNeighbours = startNode.getMyNeighbours();
            if (startNodeNeighbours.size() >= 1) {
                Node color2Node = chooseVertex(startNodeNeighbours);
                color2 = color2Node.getColor();
            } else continue;

            if (color1 != color2) {
                Queue<Node> queue = new LinkedList<>();
                HashSet<Node> kempeNodes = new HashSet<>();
                HashMap<String, Boolean> visited = new HashMap<>();
                for (Node mynode:myNodes) {
                    visited.put(mynode.getName(), false);
                }
                queue.add(startNode);
                kempeNodes.add(startNode);
                visited.put(startNode.getName(), true);
                int alternate = color2;
                while (!queue.isEmpty()) {
                    Node varNode = queue.poll();
                    if (varNode.getColor() == color1) alternate = color2;
                    else if (varNode.getColor() == color2) alternate = color1;
                    else System.out.println("Error found : Other color in kempe chain");

                    List<Node> myNeighbours = varNode.getMyNeighbours();

                    for (Node neighbour : myNeighbours) {
                        if (neighbour.getColor() == alternate && !visited.get(neighbour.getName())) {
                            kempeNodes.add(neighbour);
                            visited.put(neighbour.getName(), true);
                            queue.add(neighbour);
                        }

                    }
                }
                //interchange colors
                for (Node node :
                        kempeNodes) {
                    if (node.getColor() == color1) this.getNode(node.getName()).setColor(color2);
                    else if (node.getColor() == color2) this.getNode(node.getName()).setColor(color1);
                    else System.out.println("Error found : Other color in kempe chain");
                }

                //checkPenalty
                double newPenalty = calculatePenalty(students, type);
                if (newPenalty >= myPenalty) {
                    for (Node node : kempeNodes) {
                        if (node.getColor() == color1) this.getNode(node.getName()).setColor(color2);
                        else if (node.getColor() == color2) this.getNode(node.getName()).setColor(color1);
                        else System.out.println("Error found : Other color in kempe chain");
                    }
                } else {
                    //System.out.println("Decreased Penalty " + newPenalty);
                    myPenalty = newPenalty;
                }
            }
        }

        return myPenalty;
    }

    public Node chooseVertex(List<Node> nodes) {
        int min = 0, max = nodes.size()-1;
        int rand = (int) ((Math.random() * (max - min)) + min);
        return nodes.get(rand);
    }

    public double calculatePenalty(List<Student> students, String type) {
        double penalty = 0.0;
        int[] penaltyConstants = {Integer.MAX_VALUE, 16, 8, 4, 2, 1};
        for (Student student : students) {
            List<Node> myCourses = student.getMyCourses();
            if (myCourses.isEmpty()) continue;
            int[] myExamDays = new int[myCourses.size()];
            for (int i = 0; i < myCourses.size(); i++) {
                if (!myCourses.get(i).getName().equals("")) {
                    Node node = this.getNode(myCourses.get(i).getName());
                    myExamDays[i] = node.getColor();
                }
            }
            int myPenalty = 0;
            if (myExamDays.length==1) continue;

            if (type.equals("Consecutive")){
                Arrays.sort(myExamDays);
                for (int i = 0; i < myExamDays.length - 1; i++) {

                    if (myExamDays[i+1]!=-1 && myExamDays[i]!=-1){
                        int diff = Math.abs(myExamDays[i+1] - myExamDays[i]);
                        assert (diff!=0);
                        //System.out.println(myExamDays[i] + "   "+ myExamDays[i+1]+"   Diff "+ diff);

                        if (diff>=1 && diff<=5) {
                            myPenalty = myPenalty + penaltyConstants[diff];
                            //System.out.println("My Penalty "+myPenalty);
                        }
                    }
                }
            }
            else if (type.equals("AllPair")){
                for (int i = 0; i < myExamDays.length-1; i++) {
                    for (int j = i+1; j < myExamDays.length; j++) {
                        if (myExamDays[i]!=-1 && myExamDays[j]!=-1) {
                            int diff = Math.abs(myExamDays[i] - myExamDays[j]);
                            if (diff>=1 && diff<=5 && i!=j) myPenalty = myPenalty + penaltyConstants[diff];
                        }
                    }
                }
            }
            penalty = penalty + myPenalty;
        }
        //System.out.println(students.size());
        penalty = (penalty/(double) students.size());
        return Double.parseDouble(new DecimalFormat("##.##").format(penalty));
    }
}
