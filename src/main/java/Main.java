import model.*;
import algorithms.*;
import io.*;
import com.google.gson.*;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Read input graphs
            List<GraphInput> graphInputs = JSONReader.readGraphs("src/main/resources/input.json");

            List<GraphResult> results = new ArrayList<>();

            for (GraphInput graphInput : graphInputs) {
                System.out.println("\nProcessing Graph ID: " + graphInput.getId());

                // Create graph
                Graph graph = new Graph(graphInput.getNodes(), graphInput.getEdges());

                // Check if graph is connected
                if (!graph.isConnected()) {
                    System.out.println("Warning: Graph " + graphInput.getId() + " is not connected!");
                    continue;
                }

                // Run Prim's algorithm
                System.out.println("Running Prim's Algorithm...");
                MSTResult primResult = PrimAlgorithms.findMST(graph);

                // Run Kruskal's algorithm
                System.out.println("Running Kruskal's Algorithm...");
                MSTResult kruskalResult = KruskalAlgorithm.findMST(graph);

                // Create result object
                GraphResult result = new GraphResult(
                        graphInput.getId(),
                        graph.getVertices(),
                        graph.getEdges().size(),
                        primResult,
                        kruskalResult
                );

                results.add(result);

                // Print comparison
                System.out.println("\n--- Results ---");
                System.out.println("Vertices: " + graph.getVertices() + ", Edges: " + graph.getEdges().size());
                System.out.printf("Prim:    Cost=%d, Time=%.3fms, Ops=%d%n",
                        primResult.getTotalCost(), primResult.getExecutionTimeMs(), primResult.getOperationsCount());
                System.out.printf("Kruskal: Cost=%d, Time=%.3fms, Ops=%d%n",
                        kruskalResult.getTotalCost(), kruskalResult.getExecutionTimeMs(), kruskalResult.getOperationsCount());
            }

            // Write results to output.json
            new File("output").mkdirs();
            JSONWriter.writeResults(results, "output/output.json");
            JSONWriter.writeCSV(results, "output/performance_comparison.csv");

            System.out.println("\n✓ Results saved to output/output.json and performance_comparison.csv");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}