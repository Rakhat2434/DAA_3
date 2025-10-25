package algorithms;

import model.*;
import java.util.*;

public class KruskalAlgorithm {

    public static MSTResult findMST(Graph graph) {
        long startTime = System.nanoTime();

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        int operations = 0;

        // Step 1: Sort edges by weight
        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        Collections.sort(sortedEdges);
        operations += sortedEdges.size() * (int)(Math.log(sortedEdges.size()) / Math.log(2.0));
        // Sorting operations

        // Step 2: Initialize DSU
        DSU dsu = new DSU(graph.getVertices());

        // Step 3: Process edges in ascending order
        for (Edge edge : sortedEdges) {
            operations++; // Count comparison
            int fromIdx = graph.getNodeIndex(edge.getFrom());
            int toIdx = graph.getNodeIndex(edge.getTo());

            if (dsu.find(fromIdx) != dsu.find(toIdx)) {
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                dsu.union(fromIdx, toIdx);

                // MST complete when we have V-1 edges
                if (mstEdges.size() == graph.getVertices() - 1) {
                    break;
                }
            }
        }

        operations += dsu.getOperations();

        long endTime = System.nanoTime();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        return new MSTResult(mstEdges, totalCost, operations, executionTimeMs);
    }
}