import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private String name;
    private boolean isColored;
    private List<Node> neighbours;
    private Integer color;

    public Node(String name, boolean isColored) {
        this.name = name;
        this.isColored = isColored;
        this.neighbours = new ArrayList<>();
    }

    public Node() {
        this.isColored = false;
        this.neighbours = new ArrayList<>();
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

    public void setNeighbours(List<Node> neighbours) {
        this.neighbours = neighbours;
    }

    public void addNeighbour(Node node){
        if (!this.neighbours.contains(node)) this.neighbours.add(node);
    }

    public List<Node> getNeighbours() {
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
        return name == node.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
