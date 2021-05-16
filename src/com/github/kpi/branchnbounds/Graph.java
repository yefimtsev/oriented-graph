package com.github.kpi.branchnbounds;

import java.util.HashMap;

import java.util.Map;

class Graph {

    private final Map<String, Map<String, Integer>> adjacencyList;

    private String getLetter(int i) {
        switch (i) {
            case 0: return "a";
            case 1: return "b";
            case 2: return "c";
            case 3: return "d";
            case 4: return "e";
            case 5: return "f";
            case 6: return "g";
            case 7: return "h";
            case 8: return "i";
            default: return null;
        }
    }

    public Graph(int numberOfNodes) {    //Constructor
        adjacencyList = new HashMap<String, Map<String, Integer>>();

        for (int i = 0; i < numberOfNodes; ++i) {
            adjacencyList.put(getLetter(i), new HashMap<String, Integer>());
        }
    }

    public Graph(Graph g) {
        adjacencyList = new HashMap<String, Map<String, Integer>>(g.getAL());
    }

    public void setEdge(String edgeNode, String childNode, int weight) {    //method to add an edge
        Map<String, Integer> edges = adjacencyList.get(edgeNode);
        edges.put(childNode, weight);
    }

    public Map<String, Integer> getEdge(String parentNode) {
        return adjacencyList.get(parentNode);
    }

    public boolean contain(String parentNode, String childNode) {
        return adjacencyList.get(parentNode).containsKey(childNode);
    }

    public int numberOfEdges(String parentNode) {
        return adjacencyList.get(parentNode).size();
    }

    public void removeEdge(String parentNode, String childNode) {
        adjacencyList.get(parentNode).remove(childNode);
    }

    public void removeVertex(String parentNode) {
        adjacencyList.get(parentNode);
    }

    public void addVertex(String parentNode) {
        adjacencyList.put(parentNode, new HashMap<String, Integer>());
    }

    public Map<String, Map<String, Integer>> getAL() {
        return this.adjacencyList;
    }
}
