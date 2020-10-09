import java.util.*;

public class Node{
    private String name;
    private List<Node> myNeighbours;
    private int numberOfStudents;
    private Integer color;
    private Node parent;

    public Node(String name) {
        this.name = name;
        this.myNeighbours = new ArrayList<>();
        this.color = -1;
        parent = null;
    }
    public Node(String name, int numberOfStudents) {
        this.name = name;
        this.myNeighbours = new ArrayList<>();
        this.color = -1;
        this.numberOfStudents = numberOfStudents;
        parent = null;
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

    public int getSaturationDegree() {
        List<Integer> colors = new ArrayList<>();
        for (Node n:myNeighbours) {
            if (n.getColor()!=-1) colors.add(n.getColor());
        }
        Set<Integer> uniqueColor = new HashSet<Integer>(colors);
        return uniqueColor.size();
    }

    public void addNeighbour(Node node){
        if (!this.myNeighbours.contains(node)) {
            this.myNeighbours.add(node);
        }
    }

    public List<Node> getMyNeighbours() {
        return myNeighbours;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("\nNode : " + this.getName() + " SatDeg: " + this.getSaturationDegree() + "\nNeighbours: ");
        for (Node n:
             myNeighbours) {
            str.append(n.getName()).append("->").append(n.getSaturationDegree()).append(",");
        }
        return str.toString();
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


}
