package algorithms;

import model.*;
import java.util.*;

public class PrimAlgorithms {

    public static MSTResult findMST(Graph graph) {
        long startTime = System.nanoTime();

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        int operations = 0;

        boolean[] inMST = new boolean[graph.getVertices()];
        PriorityQueue<EdgeWithNode> pq = new PriorityQueue<>();

        // Start from vertex 0
        inMST[0] = true;
        for (Edge edge : graph.getAdjacencyList().get(0)) {
            pq.offer(new EdgeWithNode(edge, graph.getNodeIndex(edge.getTo())));
            operations++; // Count insertion
        }

        while (!pq.isEmpty() && mstEdges.size() < graph.getVertices() - 1) {
            EdgeWithNode current = pq.poll();
            operations++; // Count extraction

            if (inMST[current.toIndex]) {
                continue;
            }

            // Add edge to MST
            mstEdges.add(current.edge);
            totalCost += current.edge.getWeight();
            inMST[current.toIndex] = true;

            // Add adjacent edges
            for (Edge edge : graph.getAdjacencyList().get(current.toIndex)) {
                int nextIdx = graph.getNodeIndex(edge.getTo());
                if (!inMST[nextIdx]) {
                    pq.offer(new EdgeWithNode(edge, nextIdx));
                    operations++; // Count insertion
                }
            }
        }

        long endTime = System.nanoTime();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        return new MSTResult(mstEdges, totalCost, operations, executionTimeMs);
    }

    private static class EdgeWithNode implements Comparable<EdgeWithNode> {
        Edge edge;
        int toIndex;

        EdgeWithNode(Edge edge, int toIndex) {
            this.edge = edge;
            this.toIndex = toIndex;
        }

        @Override
        public int compareTo(EdgeWithNode other) {
            return Integer.compare(this.edge.getWeight(), other.edge.getWeight());
        }
    }
}