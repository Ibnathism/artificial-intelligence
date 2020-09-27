import java.util.*;
class BrelazNodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node node, Node t1) {
        if (node.getUniqueSaturationDegree()<t1.getUniqueSaturationDegree()) return 1;
        else if (node.getUniqueSaturationDegree()>t1.getUniqueSaturationDegree()) return -1;
        else {
            return Integer.compare(t1.getMyNeighbours().size(), node.getMyNeighbours().size());
        }
    }
}
public class Graph {
    private List<Node> myNodes;
    private int totalNodes;

    public Graph(List<Node> myNodes) {
        this.myNodes = myNodes;
        this.totalNodes = this.myNodes.size();
    }

    public Node getNode(String name){
        for (Node n:
             myNodes) {
            //System.out.println(n.getName()+"   "+name);
            if (n.getName().equals(name)) return n;
        }
        return null;
    }


    public Graph addEdges(List<Student> studentList) {
        for (Student s: studentList) {
            List<Course> myCourses = s.getMyCourses();
            for (int i = 0; i < myCourses.size()-1 ; i++) {
                //System.out.println(myCourses.get(i).getValue());
                Node node1 = this.getNode(myCourses.get(i).getValue());
                for (int j = 1; j < myCourses.size(); j++) {
                    Node node2 = this.getNode(myCourses.get(j).getValue());
                    node1.addNeighbour(node2);
                    node2.addNeighbour(node1);
                }
            }
        }
        //System.out.println(this.myNodes.size());
        return this;
    }

    public int colorGraphBrelaz(){
        PriorityQueue<Node> pq = new PriorityQueue<>(this.totalNodes, new BrelazNodeComparator());
        pq.addAll(myNodes);
        int count = 0;
        boolean[] colors = new boolean[totalNodes];

        Arrays.fill(colors, false);
        while (!pq.isEmpty()) {
            boolean[] availableColors = new boolean[totalNodes];
            Arrays.fill(availableColors, true);
            Node temp = pq.poll();
            if (temp==null) break;
            else {
                //System.out.println("Temp "+temp.getName()+" Saturation "+temp.getSaturationDegree());
                if (temp.getColor() == -1) {
                    //color temp
                    for (Node n: temp.getMyNeighbours()) {
                        n.setSaturationDegree(n.getSaturationDegree()+1);
                        if (n.getColor()!=-1) availableColors[n.getColor()] = false;
                    }
                    for (int i = 0; i < totalNodes; i++) {
                        if (availableColors[i]) {
                            temp.setColor(i);
                            if (!colors[i]){
                                count++;
                                colors[i] = true;
                            }
                            break;
                        }
                    }
                }
                //Arrays.fill(availableColors, true);
                pq.add(temp);
                //System.out.println(temp);
                pq.remove(temp);
            }
        }

        return count;
    }
}
