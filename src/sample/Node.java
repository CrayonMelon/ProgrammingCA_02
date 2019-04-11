package sample;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String name;
    private List<Edge> edges;
    private int xLocation;
    private int yLocation;


    public Node(String name, int xLocation, int yLocation) {
        this.name = name;
        this.edges = new ArrayList<Edge>();
        this.xLocation = xLocation;
        this.yLocation = yLocation;

    }



    public List<Edge> getEdges() {
        return edges;
    }

    public List<Node> getNeighbors() {
        List<Node> neighbors = new ArrayList<Node>(getEdges().size());
        for (Edge e : getEdges()) {
            neighbors.add(e.getTo());
        }
        return neighbors;
    }

    // Add an Edge that goes one way e.g. A -> B
    public void addOneWayNeighbor(Node neighbor, int weight) {
        this.edges.add(new Edge(this, neighbor, weight));
    }

    // Add an Edge that goes both ways e.g. A <-> B
    public void addTwoWayNeighbor(Node neighbor, int weight) {
        addOneWayNeighbor(neighbor, weight);
        neighbor.addOneWayNeighbor(this, weight);
    }

    public int getxLocation() {
        return xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }

    public void setxLocation(int xLocation) {
        this.xLocation = xLocation;
    }

    public void setyLocation(int yLocation) {
        this.yLocation = yLocation;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Node [name=" + name + ", edges=" + edges.size() + "]";
    }
}
