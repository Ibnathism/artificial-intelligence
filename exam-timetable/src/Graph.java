import java.util.*;

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

    public List<Node> getMyNodes() {
        return myNodes;
    }

    public void setMyNodes(List<Node> myNodes) {
        this.myNodes = myNodes;
    }

    public Graph addEdges(List<Student> studentList) {

        for (Student s:
             studentList) {
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

    public int colorGraph() {
        int doneColoring = 0;
        List<Integer> totalColors = new ArrayList<>();
        Node max = myNodes.get(0);
        for (Node node:
                this.myNodes) {
            if (max.getNeighbours().size()<node.getNeighbours().size()) {
                //System.out.println("Max "+max.getName()+"  maxNeigh "+max.getNeighbours().size());

                //System.out.println("Node "+node.getName()+"  nodeNeigh "+node.getNeighbours().size());
                max=this.getNode(node.getName());
            }
        }
        boolean[] availableColors = new boolean[this.myNodes.size()];
        Arrays.fill(availableColors, true);
        //System.out.println("final "+max.getName()+"  Neigh "+max.getNeighbours().size());
        max.setColor(0);
        //System.out.println("Final color : " + max.getColor());
        totalColors.add(0);
        doneColoring++;
        availableColors[0] = false;
        while(doneColoring<this.totalNodes) {
            Node temp = max.getNeighbours().poll();
            if (temp!=null){

                Object[] tempNeighbours = temp.getNeighbours().toArray();
                Arrays.fill(availableColors, true);

                for (int i = 0; i < tempNeighbours.length; i++) {
                    Node node = (Node) tempNeighbours[i];
                    if(node.getColor()!=-1) availableColors[node.getColor()] = false;
                }
                for (int i = 0; i < availableColors.length; i++) {
                    if (availableColors[i]) {
                        temp.setColor(i);
                        totalColors.add(i);
                        doneColoring++;
                        availableColors[i] = false;
                        break;
                    }
                }
                max = temp;
                Arrays.fill(availableColors, true);
                availableColors[max.getColor()] = false;
            }
        }

        Set<Integer> uniqueColor = new HashSet<>(totalColors);
        //System.out.println(uniqueColor.size());

        return uniqueColor.size();
    }


    /*public int colorGraphBrelaz(){
        PriorityQueue<Node> pq = new PriorityQueue<>(this.totalNodes, new BrelazNodeComparator());
        pq.addAll(myNodes);
        int doneColoring = 0;
        while (doneColoring<this.totalNodes){
            Node temp = pq.poll();
            if (temp!=null){
                if (temp.getColor()!=-1){
            }
        }

    }*/
}
