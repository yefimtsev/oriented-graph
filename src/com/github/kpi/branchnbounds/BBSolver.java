package com.github.kpi.branchnbounds;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class BBSolver {

    private final String DEFAULT_START_NODE = "a";
    private final String DEFAULT_END_NODE = "g";

    private Graph originGraph;
    private Graph workingGraph;

    private String head;

    private String currentPath;
    private String previousPath;


    private final Map<String, Integer> path = new HashMap<>();
    private final Map<String, Integer> finalPaths = new HashMap<>();

    public BBSolver() {
        configureGraphs();
        configureSolver();
        print(workingGraph);

        do {
            findLeastCostPath();
            updateSolutionFlow();
        } while (isSamePath());

    }

    private boolean isSamePath() {
        return currentPath.equals(previousPath);
    }

    private void writePaths() {
        finalPaths.put(getStringPath(), path.get(DEFAULT_END_NODE));
    }

    private void getLeastCostResult() {
        Map.Entry<String, Integer> min = null;
        for (Map.Entry<String, Integer> entry : finalPaths.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }
        System.out.printf("Shortest path is [ %s ] and it costs [ %d ]\n", min.getKey(), min.getValue());
    }

    private String getStringPath() {
        AtomicReference<String> path = new AtomicReference<>("");

        this.path.keySet().forEach(s -> path.set(path.get().concat(s.toUpperCase() + "->")));

        path.set(path.get().substring(0, path.get().length() - 2));

        return path.get();
    }

    private void configureSolver() {
        setHead(DEFAULT_START_NODE); //default head
        path.put(DEFAULT_START_NODE, 0); // update path with default values
    }

    private void updateSolutionFlow() {
        dropLastSucceedBranch();
        nullifyPath();
        updateWorkingGraph();
        configureSolver();
    }

    private void findLeastCostPath() {
        String nextHead;
        String previousHead = "";
        while (!path.containsKey(DEFAULT_END_NODE)) {
            previousHead = head;
            nextHead = findLeastCostChild(head);
            updateWeights(head, nextHead);
            updatePath(nextHead, getCurrentNodeWeight(nextHead));
            dropBranch(head);
            setHead(nextHead);
        }
        path.replace(DEFAULT_END_NODE, workingGraph.getEdge(previousHead).get(head));
        writePaths();
    }

    private void dropLastSucceedBranch() {
        AtomicReference<String> previousNode = new AtomicReference<>();
        AtomicReference<String> parentNode = new AtomicReference<>();
        AtomicReference<String> childNode = new AtomicReference<>();

        path.forEach((pathNode, unused) -> {
            if (workingGraph.getAL().get(pathNode).size() == 1
            && workingGraph.getAL().get(previousNode.get()).size() > 1) {
                parentNode.set(previousNode.get());
                childNode.set(pathNode);
            }
            previousNode.set(pathNode);
        });

        originGraph.getAL().forEach((n, map) -> map.remove(childNode.get()));
        originGraph.removeVertex(childNode.get());
    }

    private void nullifyPath() {
        System.out.println("Path before clear: " + path);
        path.clear();
    }

    private void dropBranch(String head) {
        if (head.equals(DEFAULT_START_NODE)) return;

        AtomicReference<String> superN = new AtomicReference<>();
        AtomicReference<String> n = new AtomicReference<>();

        workingGraph.getAL().forEach((superNode, map) -> map.forEach((node, weight) -> {
            if (workingGraph.getEdge(head).containsKey(node)) {
                if (weight > workingGraph.getEdge(head).get(node)) {
                    superN.set(superNode);
                    n.set(node);
                }
            }
        }));
        if (workingGraph.getAL().containsKey(superN.get())
                && workingGraph.getEdge(superN.get()).containsKey(n.get())) {
            workingGraph.getAL().get(superN.get()).remove(n.get());
        }
    }

    private void printCostAndPath() {
        AtomicReference<Integer> currentCost = new AtomicReference<>(0);
        System.out.print("Current path is ");
        path.forEach((node, cost) -> {
            currentCost.set(cost);
            System.out.printf("  %s ", node);
        });
        System.out.printf("\nTotal cost is %d\n", currentCost.get());
    }

    private void updatePath(String head, Integer weight) {
        path.put(head, weight);
    }


    private Integer getCurrentNodeWeight(String nextHead) {
        if (nextHead.equals(DEFAULT_END_NODE)) {
            return workingGraph.getEdge(head).get(head);
        }
        else {
            return workingGraph.getEdge(head).get(nextHead);
        }
    }

    private void updateWeights(String previousHead, String nextHead) {
        int nextHeadWeight = workingGraph.getEdge(previousHead).get(nextHead);

        workingGraph.getEdge(nextHead)
                .forEach((node, weight) -> workingGraph.getEdge(nextHead).replace(node, weight + nextHeadWeight));
    }


    private String findLeastCostChild(String head) {
        AtomicReference<String> leastCostNode = new AtomicReference<>();
        AtomicReference<Integer> minimumWeight = new AtomicReference<>(Integer.MAX_VALUE);
        workingGraph.getEdge(head).forEach((node, weight) -> {
            if (weight < minimumWeight.get())
            {
                minimumWeight.set(weight);
                leastCostNode.set(node);
            }
        });

        return leastCostNode.get();
    }

    private void setHead(String nextHead) {
        this.head = nextHead;
    }

    private void configureGraphs() {
        this.originGraph = new Graph(6);
        this.workingGraph = new Graph(6);
        setupGraph(this.originGraph);
        updateWorkingGraph();
    }

    private void updateWorkingGraph() {
        workingGraph.getAL().clear();
        Map<String, Map<String, Integer>> tmp = originGraph.getAL();
        tmp.forEach((node, map) -> workingGraph.getAL().put(node, new HashMap<>(map)));
    }

    private void setupGraph(Graph g) {
        g.setEdge("a", "b", 2);
        g.setEdge("a", "c", 4);
        g.setEdge("a", "d", 5);

        g.setEdge("b", "c", 1);
        g.setEdge("b", "e", 5);
        g.setEdge("b", "f", 12);

        g.setEdge("c", "f", 9);
        g.setEdge("c", "g", 18);

        g.setEdge("d", "g", 8);

        g.setEdge("e", "f", 4);

        g.setEdge("f", "g", 1);

        g.addVertex("g");

    }

    private void print(Graph graph) {
        System.out.println("==========================================================");
        graph.getAL().forEach((k, v) -> System.out.println(k + " v: " + v));
        System.out.println("==========================================================");
    }
}
