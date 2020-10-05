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

    public Graph(List<Node> myNodes) {
        this.myNodes = myNodes;
        this.totalNodes = this.myNodes.size();
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
        int count = 0;

        boolean[] usedColors = new boolean[totalNodes];
        Arrays.fill(usedColors, false);
        while (!pq.isEmpty()) {
            boolean[] availableColors = new boolean[totalNodes];
            Arrays.fill(availableColors, true);
            Node temp = pq.poll();
            if (temp==null) break;
            else {
                if (temp.getColor() == -1) {
                    //color temp
                    for (Node neighbour: temp.getMyNeighbours()) {
                        if (neighbour.getColor()!=-1) availableColors[neighbour.getColor()] = false;
                    }
                    for (int i = 0; i < totalNodes; i++) {
                        if (availableColors[i]) {
                            temp.setColor(i);
                            usedColors[i] = true;
                            break;
                        }
                    }
                    pq.add(temp);
                    pq.remove(temp);
                }

            }
        }
        for (int i = 0; i < totalNodes; i++) {
            if (usedColors[i]) count++;
        }
        return count;
    }

    public int colorGraphBrelaz(){
        PriorityQueue<Node> pq = new PriorityQueue<>(this.totalNodes, new BrelazNodeComparator());
        //pq.addAll(myNodes);
        for (Node node:
             myNodes) {
            pq.add(node);
        }
        return this.getColoring(pq);
    }
    public int colorGraphLargestDegree(){
        PriorityQueue<Node> pq = new PriorityQueue<>(this.totalNodes, new LargestDegreeComparator());
        pq.addAll(myNodes);
        return this.getColoring(pq);
    }


    public double calculatePenalty(List<Student> students) {
        double penalty = 0.0;
        int[] penaltyConstants = {-1, 16, 8, 4, 2, 1};
        for (Student student : students) {
            List<Node> myCourses = student.getMyCourses();
            int[] myExamDays = this.getExamDays(myCourses);
            Arrays.sort(myExamDays);
            int myPenalty = 0;
            if (myExamDays.length==1) continue;
            for (int i = 0; i < myExamDays.length - 1; i++) {
                if (myExamDays[i+1]!=-1 && myExamDays[i]!=-1){
                    int diff = Math.abs(myExamDays[i+1] - myExamDays[i]);
                    if (diff>=1 && diff<=5) myPenalty += myPenalty + penaltyConstants[diff];
                }

            }
            penalty = penalty + myPenalty;

        }
        penalty = (penalty/(double) students.size());
        return penalty;
    }
    public int[] getExamDays(List<Node> myCourses) {
        int[] colors = new int[myCourses.size()];
        for (int i = 0; i < myCourses.size(); i++) {
            Node node = this.getNode(myCourses.get(i).getName());
            //System.out.println(node);
            if (node == null) {
                //System.out.println("Null found");
                colors[i] = -1;
            }
            else colors[i] = node.getColor();

        }
        return colors;
    }
}
