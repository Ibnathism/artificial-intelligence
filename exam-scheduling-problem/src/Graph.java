import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {
    private PriorityQueue<Node> pq;

    public Graph() {
        pq = new PriorityQueue<>();
    }

    public Graph(List<Node> graph){
        //pq = new PriorityQueue<>(graph.size(),new HighDegreeComparator());
        pq = new PriorityQueue<>(graph.size(),new BrelazComparator());
        pq.addAll(graph);
    }

    public PriorityQueue<Node> getPq() {
        return pq;
    }

    public void setPq(PriorityQueue<Node> pq) {
        this.pq = pq;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Graph PriorityQueue : ");
        for (Node n:
             pq) {
            str.append(n.getName()).append("  ");
        }
        return str.toString();
    }
}

class HighDegreeComparator implements Comparator<Node> {

    @Override
    public int compare(Node node, Node t1) {
        return Integer.compare(t1.getNeighbours().size(), node.getNeighbours().size());
    }
}

class BrelazComparator implements Comparator<Node> {
    @Override
    public int compare(Node node, Node t1) {
        if (node.getSaturationDegree()<t1.getSaturationDegree()) return 1;
        else if (node.getSaturationDegree()>t1.getSaturationDegree()) return -1;
        else {
            return Integer.compare(t1.getNeighbours().size(), node.getNeighbours().size());
        }
    }
}
