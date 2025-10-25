package io;

import com.google.gson.*;
import model.*;
import java.io.*;
import java.util.*;

public class JSONReader {

    public static List<GraphInput> readGraphs(String filename) throws IOException {
        Gson gson = new Gson();
        Reader reader = new FileReader(filename);

        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        JsonArray graphsArray = jsonObject.getAsJsonArray("graphs");

        List<GraphInput> graphs = new ArrayList<>();

        for (JsonElement graphElement : graphsArray) {
            JsonObject graphObj = graphElement.getAsJsonObject();

            int id = graphObj.get("id").getAsInt();

            // Parse nodes
            JsonArray nodesArray = graphObj.getAsJsonArray("nodes");
            List<String> nodes = new ArrayList<>();
            for (JsonElement node : nodesArray) {
                nodes.add(node.getAsString());
            }

            // Parse edges
            JsonArray edgesArray = graphObj.getAsJsonArray("edges");
            List<Edge> edges = new ArrayList<>();
            for (JsonElement edgeElement : edgesArray) {
                JsonObject edgeObj = edgeElement.getAsJsonObject();
                String from = edgeObj.get("from").getAsString();
                String to = edgeObj.get("to").getAsString();
                int weight = edgeObj.get("weight").getAsInt();
                edges.add(new Edge(from, to, weight));
            }

            graphs.add(new GraphInput(id, nodes, edges));
        }

        reader.close();
        return graphs;
    }
}