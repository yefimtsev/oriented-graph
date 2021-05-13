package com.github.kpi.branchnbounds;

import java.util.ArrayList;
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
    private final ArrayList<Integer> finalPath = new ArrayList<>();
    private final ArrayList<Integer> currentPath = new ArrayList<>();
    private final HashMap<Integer, Integer> currentNodeWeights = new HashMap<>(); // node index, node weight
    private int previousNode = 0;
    private int currentNode = 0;
    private int currentSize = 0; // current size represents the amount of the connected nodes //todo check if this really necessary

    public ProblemSolver() {
        printMatrix();
        currentNodeWeights.put(currentNode, 0); // set default node as 0 (a) and it's weight to 0;

        worker();
    }


    private void worker() {
        updateCurrentNodeWeights(currentNode, 0);
        while (!currentNodeWeights.containsKey(6)) {
            printDebugData();
            updateCurrentNodeWeights(previousNode, currentNode);
            updateNodeTracking();
            printDebugData();
            break; //todo fix cycle -> now it's going to infinite cycle --> possible fix: make new currentNodeWeight map every time
        }
    }

    private void updateNodeTracking() {
        previousNode = currentNode;
        AtomicInteger minIndex = new AtomicInteger(Integer.MAX_VALUE);

        currentNodeWeights.forEach((k, v) -> {
            if (k != 0) {
                if (k < minIndex.get()) {
                    minIndex.set(k);
                }
            }
        });

        currentNode = minIndex.get();
    }

    private Map<Integer, Integer> findChildNodes(int currentNode) {
        Map<Integer, Integer> childNodes = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[currentNode][i] != 0) {
                childNodes.put(i, matrix[currentNode][i]);
                currentSize++; //todo check if this really necessary
            }
        }

        return childNodes;
    }

    //todo check if we really need parentNode
    private void updateCurrentNodeWeights(int parentNode, int currentNode) {
        Map<Integer, Integer> tempMap = findChildNodes(currentNode);
        tempMap.forEach((k, v) -> currentNodeWeights.put(k, v + currentNodeWeights.get(currentNode)));
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
    }

    private void printHumanReadableCurrentNode() {
        System.out.println("Current node: " + letterToInteger.get(currentNode));
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
