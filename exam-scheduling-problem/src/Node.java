import java.util.*;

class BrelazNodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node node, Node t1) {
        if (node.getSaturationDegree()<t1.getSaturationDegree()) return 1;
        else if (node.getSaturationDegree()>t1.getSaturationDegree()) return -1;
        else {
            return Integer.compare(t1.getNeighbours().size(), node.getNeighbours().size());
        }
    }
}


public class Node {
    private String name;
    private boolean isColored;
    private PriorityQueue<Node> neighbours;
    private Integer color;

    public Node(String name, boolean isColored) {
        this.name = name;
        this.isColored = isColored;
        this.neighbours = new PriorityQueue<>(new BrelazNodeComparator());
    }

    public Node() {
        this.isColored = false;
        this.neighbours = new PriorityQueue<>();
    }

    public String getName() {
        return name;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isColored() {
        return isColored;
    }

    public void setColored(boolean colored) {
        isColored = colored;
    }

    public void setNeighbours(PriorityQueue<Node> neighbours) {
        this.neighbours = neighbours;
    }

    public void addNeighbour(Node node){
        if (!this.neighbours.contains(node)) this.neighbours.add(node);
    }

    public PriorityQueue<Node> getNeighbours() {
        return this.neighbours;
    }

    public boolean isNeighbour(Node node){
        ///TODO
        return neighbours.contains(node);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return name.equals(node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public int getSaturationDegree() {
        int saturationDegree = 0;
        for (Node neighbour:
             this.getNeighbours()) {
            if (neighbour.isColored) saturationDegree++;
        }
        return saturationDegree;
    }
}
