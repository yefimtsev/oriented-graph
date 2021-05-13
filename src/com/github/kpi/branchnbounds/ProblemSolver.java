package com.github.kpi.branchnbounds;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

// a = 0, g = 6
public class ProblemSolver {

    private final HashMap<Integer, String> letterToInteger = createLettersToIntegersMap();
    private final int[][] matrix = {
            {0, 2, 4, 5, 0, 0, 0},
            {0, 0, 1, 0, 5, 12, 0},
            {0, 0, 0, 0, 0, 9, 18},
            {0, 0, 0, 0, 0, 0, 8},
            {0, 0, 0, 0, 0, 4, 0},
            {0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0}};
    private final HashMap<Integer, Integer> currentPath = new HashMap<>();
    private final HashMap<Integer, Integer> currentNodeWeights = new HashMap<>(); // node index, node weight
    private final HashMap<Integer, HashMap<Integer, Integer>> previousChildNodes = new HashMap<>();
    private int previousNode = 0;
    private int currentNode = 0;

    public ProblemSolver() {
        printMatrix();
        currentNodeWeights.put(currentNode, 0); // set default node as 0 (a) and it's weight to 0;
        worker();
    }


    private void worker() {
        updateCurrentPath(previousNode);
        updateCurrentNodeWeights(currentNode);
//        printDebugData();
        while (!currentNodeWeights.containsKey(6)) {
            updateState();
        }
        updateState();
        updateState();
        printHumanReadablePath();
    }

    private void updateState() {
        updateCurrentNodeWeights(currentNode);
        printHumanReadableCurrentNode();
        printHumanReadablePrevChildNodes();
        updateNodeTracking();
        updatePreviousChildNodeMap(previousNode);
        updateCurrentPath(currentNode);
        updateNodesMap();

    }

    private void updateNodesMap() {
        clearCurrentNodeWeights();
        updateCurrentNodeWeights(currentNode);
    }

    private void updateCurrentPath(int node) {
        currentPath.put(node, currentNodeWeights.get(node));
    }

    private void updateNodeTracking() {
        AtomicInteger currentNodesMin = new AtomicInteger(Integer.MAX_VALUE);
        AtomicInteger previousNodesMin = new AtomicInteger(Integer.MAX_VALUE);

        if (previousChildNodes.get(previousNode) != null) {
            previousChildNodes.get(previousNode).forEach((k, v) -> {
                if (k != 0) {
                    if (k < previousNodesMin.get()) {
                        previousNodesMin.set(k);
                    }
                }
            });
        }


        currentNodeWeights.forEach((k, v) -> {
            // we need to exclude values which greater than values that is already in path.
            if (k != 0) {
                if (k < currentNodesMin.get()) {
                    currentNodesMin.set(k);
                }
            }
        });
        //todo cleanup this mess
        System.out.println();
        System.out.println("updateNodeTracking HERE:");
        System.out.println("CurrentNodesMin: " + currentNodeWeights.get(currentNodesMin.get()));
        if (previousChildNodes.get(previousNode) != null)
            System.out.println("PreviousNodesMin: " + previousChildNodes.get(previousNode).get(previousNodesMin.get()));
        System.out.println("updateNodeTracking DONE:");
        System.out.println();
        previousNode = currentNode;
        currentNode = currentNodesMin.get();

    }

    private void updatePreviousChildNodeMap(int previousNode) {
        previousChildNodes.clear();
        HashMap<Integer, Integer> temp = new HashMap<>();
        Map<Integer, Integer> prevChildNodes = findChildNodes(previousNode);
        prevChildNodes.forEach((k, v) -> temp.put(k, v + currentPath.get(previousNode)));
        previousChildNodes.put(previousNode, temp);
    }

    private Map<Integer, Integer> findChildNodes(int currentNode) {
        Map<Integer, Integer> childNodes = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[currentNode][i] != 0) {
                childNodes.put(i, matrix[currentNode][i]);
            }
        }
        return childNodes;
    }

    private void updateCurrentNodeWeights(int currentNode) {
        Map<Integer, Integer> tempMap = findChildNodes(currentNode);
        tempMap.forEach((k, v) -> currentNodeWeights.put(k, v + currentPath.get(currentNode)));
    }

    private void clearCurrentNodeWeights() {
        currentNodeWeights.clear();
    }

    private void printDebugData() {
        System.out.println("-----------------------------------------------");
        System.out.println("Prev: " + previousNode);
        System.out.println("Curr: " + currentNode);
        printHumanReadableNodes();
        printHumanReadableCurrentNode();
        System.out.println("-----------------------------------------------");
    }

    private void printHumanReadableNodes() {
        currentNodeWeights.forEach((k, v) -> System.out.print(letterToInteger.get(k) + " = " + v + " | "));
        System.out.println();
    }

    private void printHumanReadableCurrentNode() {
        System.out.println("Current node: " + letterToInteger.get(currentNode));
    }

    private void printHumanReadablePath() {
        currentPath.forEach((k, v) -> System.out.println(letterToInteger.get(k) + " total weight = " + v + " |"));
    }

    private void printHumanReadablePrevChildNodes() {
        System.out.println("PREV CHILD NODES START for node " + letterToInteger.get(previousNode));
        previousChildNodes.forEach((k, v) -> {

            System.out.print(letterToInteger.get(k) + " = ");
            v.forEach((n, p) -> System.out.print(" {" + letterToInteger.get(n) + "=" + p + "} "));
        });
        System.out.println("\nPREV CHILD NODES END\n\n");

    }

    public void printMatrix() {
        for (int[] ints : matrix) {
            for (int value : ints) {
                System.out.printf("%5d ", value);
            }
            System.out.println();
        }
    }

    private HashMap<Integer, String> createLettersToIntegersMap() {
        HashMap<Integer, String> temp = new HashMap<>();
        temp.put(0, "a");
        temp.put(1, "b");
        temp.put(2, "c");
        temp.put(3, "d");
        temp.put(4, "e");
        temp.put(5, "f");
        temp.put(6, "g");
        temp.put(7, "h");
        temp.put(8, "i");
        return temp;
    }
}
