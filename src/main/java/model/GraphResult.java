package model;

public class GraphResult {
    private int graphId;
    private int vertices;
    private int edges;
    private MSTResult primResult;
    private MSTResult kruskalResult;

    public GraphResult(int graphId, int vertices, int edges, MSTResult primResult, MSTResult kruskalResult) {
        this.graphId = graphId;
        this.vertices = vertices;
        this.edges = edges;
        this.primResult = primResult;
        this.kruskalResult = kruskalResult;
    }

    public int getGraphId() { return graphId; }
    public int getVertices() { return vertices; }
    public int getEdges() { return edges; }
    public MSTResult getPrimResult() { return primResult; }
    public MSTResult getKruskalResult() { return kruskalResult; }
}
