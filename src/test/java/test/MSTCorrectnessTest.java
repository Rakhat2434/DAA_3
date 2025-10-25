package test;

import model.*;
import algorithms.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.*;

public class MSTCorrectnessTest {

    @Test
    public void testMSTCostEquality() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("A", "C", 4),
                new Edge("B", "C", 2),
                new Edge("C", "D", 3),
                new Edge("B", "D", 5)
        );
        Graph graph = new Graph(nodes, edges);

        MSTResult prim = PrimAlgorithms.findMST(graph);
        MSTResult kruskal = KruskalAlgorithm.findMST(graph);

        assertEquals(prim.getTotalCost(), kruskal.getTotalCost(),
                "MST total cost should match between Prim and Kruskal");
    }

    @Test
    public void testMSTEdgeCount() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("B", "C", 2),
                new Edge("C", "D", 3)
        );
        Graph graph = new Graph(nodes, edges);

        MSTResult result = PrimAlgorithms.findMST(graph);
        assertEquals(graph.getVertices() - 1, result.getMstEdges().size(),
                "MST must contain V - 1 edges");
    }

    @Test
    public void testMSTIsAcyclic() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 4),
                new Edge("A", "C", 3),
                new Edge("B", "C", 2),
                new Edge("B", "D", 5),
                new Edge("C", "D", 7),
                new Edge("C", "E", 8),
                new Edge("D", "E", 6)
        );
        Graph graph = new Graph(nodes, edges);

        MSTResult result = KruskalAlgorithm.findMST(graph);
        assertEquals(graph.getVertices() - 1, result.getMstEdges().size(),
                "MST must be a tree (acyclic)");
    }

    @Test
    public void testExecutionTimeNonNegative() {
        List<String> nodes = Arrays.asList("A", "B", "C");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1),
                new Edge("B", "C", 2)
        );
        Graph graph = new Graph(nodes, edges);

        MSTResult result = PrimAlgorithms.findMST(graph);
        assertTrue(result.getExecutionTimeMs() >= 0,
                "Execution time should not be negative");
    }
}
