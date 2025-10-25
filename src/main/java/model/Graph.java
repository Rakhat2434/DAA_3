package model;

import java.util.*;

public class Graph {
    private int vertices;
    private List<String> nodeNames;
    private Map<String, Integer> nodeIndex;
    private List<Edge> edges;
    private List<List<Edge>> adjacencyList;

    public Graph(List<String> nodes, List<Edge> edges) {
        this.nodeNames = new ArrayList<>(nodes);
        this.vertices = nodes.size();
        this.edges = new ArrayList<>(edges);
        this.nodeIndex = new HashMap<>();
        this.adjacencyList = new ArrayList<>();

        // Initialize node indices
        for (int i = 0; i < nodes.size(); i++) {
            nodeIndex.put(nodes.get(i), i);
            adjacencyList.add(new ArrayList<>());
        }

        // Build adjacency list
        for (Edge edge : edges) {
            int fromIdx = nodeIndex.get(edge.getFrom());
            int toIdx = nodeIndex.get(edge.getTo());
            adjacencyList.get(fromIdx).add(edge);
            adjacencyList.get(toIdx).add(new Edge(edge.getTo(), edge.getFrom(), edge.getWeight()));
        }
    }

    public int getVertices() { return vertices; }
    public List<String> getNodeNames() { return nodeNames; }
    public List<Edge> getEdges() { return edges; }
    public List<List<Edge>> getAdjacencyList() { return adjacencyList; }
    public int getNodeIndex(String nodeName) { return nodeIndex.get(nodeName); }
    public String getNodeName(int index) { return nodeNames.get(index); }

    public boolean isConnected() {
        if (vertices == 0) return true;
        boolean[] visited = new boolean[vertices];
        dfs(0, visited);
        for (boolean v : visited) {
            if (!v) return false;
        }
        return true;
    }

    private void dfs    (int v, boolean[] visited) {
        visited[v] = true;
        for (Edge edge : adjacencyList.get(v)) {
            int next = nodeIndex.get(edge.getTo());
            if (!visited[next]) {
                dfs(next, visited);
            }
        }
    }
}