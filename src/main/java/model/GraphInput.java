package model;

import java.util.List;

public class GraphInput {
    private int id;
    private List<String> nodes;
    private List<Edge> edges;

    public GraphInput(int id, List<String> nodes, List<Edge> edges) {
        this.id = id;
        this.nodes = nodes;
        this.edges = edges;
    }

    public int getId() { return id; }
    public List<String> getNodes() { return nodes; }
    public List<Edge> getEdges() { return edges; }
}
