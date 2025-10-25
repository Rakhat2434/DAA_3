package io;

import com.google.gson.*;
import model.*;
import java.io.*;
import java.util.*;

public class JSONWriter {

    public static void writeResults(List<GraphResult> results, String filename) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject root = new JsonObject();
        JsonArray resultsArray = new JsonArray();

        for (GraphResult result : results) {
            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("graph_id", result.getGraphId());

            // Input stats
            JsonObject inputStats = new JsonObject();
            inputStats.addProperty("vertices", result.getVertices());
            inputStats.addProperty("edges", result.getEdges());
            resultObj.add("input_stats", inputStats);

            // Prim's result
            JsonObject primObj = createAlgorithmResult(result.getPrimResult());
            resultObj.add("prim", primObj);

            // Kruskal's result
            JsonObject kruskalObj = createAlgorithmResult(result.getKruskalResult());
            resultObj.add("kruskal", kruskalObj);

            resultsArray.add(resultObj);
        }

        root.add("results", resultsArray);

        // Write to file
        FileWriter writer = new FileWriter(filename);
        writer.write(gson.toJson(root));
        writer.close();
    }

    private static JsonObject createAlgorithmResult(MSTResult result) {
        JsonObject obj = new JsonObject();

        // MST edges
        JsonArray edgesArray = new JsonArray();
        for (Edge edge : result.getMstEdges()) {
            JsonObject edgeObj = new JsonObject();
            edgeObj.addProperty("from", edge.getFrom());
            edgeObj.addProperty("to", edge.getTo());
            edgeObj.addProperty("weight", edge.getWeight());
            edgesArray.add(edgeObj);
        }
        obj.add("mst_edges", edgesArray);

        obj.addProperty("total_cost", result.getTotalCost());
        obj.addProperty("operations_count", result.getOperationsCount());
        obj.addProperty("execution_time_ms",
                Math.round(result.getExecutionTimeMs() * 100.0) / 100.0);

        return obj;
    }

    public static void writeCSV(List<GraphResult> results, String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);

        // Header
        writer.write("Graph_ID,Vertices,Edges,Algorithm,Total_Cost,Operations,Execution_Time_ms\n");

        // Data rows
        for (GraphResult result : results) {
            // Prim's row
            writer.write(String.format("%d,%d,%d,%s,%d,%d,%.2f\n",
                    result.getGraphId(),
                    result.getVertices(),
                    result.getEdges(),
                    "Prim",
                    result.getPrimResult().getTotalCost(),
                    result.getPrimResult().getOperationsCount(),
                    result.getPrimResult().getExecutionTimeMs()
            ));

            // Kruskal's row
            writer.write(String.format("%d,%d,%d,%s,%d,%d,%.2f\n",
                    result.getGraphId(),
                    result.getVertices(),
                    result.getEdges(),
                    "Kruskal",
                    result.getKruskalResult().getTotalCost(),
                    result.getKruskalResult().getOperationsCount(),
                    result.getKruskalResult().getExecutionTimeMs()
            ));
        }

        writer.close();
    }
}