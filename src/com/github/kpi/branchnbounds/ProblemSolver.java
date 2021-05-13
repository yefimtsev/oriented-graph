package com.github.kpi.branchnbounds;

import java.util.*;
import java.util.stream.Collectors;

// a = 0, g = 6
public class ProblemSolver {

    private int[][] matrix = {
            {0, 2, 4, 5, 0, 0, 0},
            {0, 0, 1, 0, 5, 12, 0},
            {0, 0, 0, 0, 0, 9, 18},
            {0, 0, 0, 0, 0, 0, 8},
            {0, 0, 0, 0, 0, 4, 0},
            {0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0}};

    public ProblemSolver() {
        printMatrix();
        currentNodeWeights.put(0, 0); // set default node as 0 (a) and it's weight to 0;
        currentNodeWeights.put(1, 2); // set default node as 1 (b) and it's weight to 2;
        updateCurrentNodeWeights(0, 1);
        updateCurrentNodeWeights(1, 2);
        printHumanReadableNodes();
    }

    private ArrayList<Integer> finalPath = new ArrayList<>();
    private ArrayList<Integer> currentPath = new ArrayList<>();
    private HashMap<Integer, Integer> currentNodeWeights = new HashMap<>(); // node index, node weight

    private int currentSize = 0; // current size represents the amount of the connected nodes

    public void printMatrix() {
        for (int[] ints : matrix) {
            for (int value : ints) {
                System.out.printf("%5d ", value);
            }
            System.out.println();
        }
    }

    private void printHumanReadableNodes() {
        currentNodeWeights.forEach((k, v) -> System.out.print(letterToInteger.get(k) + " = " + v + " | "));
    }

    private Map<Integer, Integer> findChildNodes(int currentNode) {
        Map<Integer, Integer> childNodes = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[currentNode][i] != 0) {
                childNodes.put(i, matrix[currentNode][i]);
                currentSize++;
            }
        }

        return childNodes;
    }

    private void updateCurrentNodeWeights(int parentNode, int currentNode) {
        Map<Integer, Integer> tempMap = findChildNodes(currentNode);
        tempMap.forEach((k, v) -> currentNodeWeights.put(k, v + currentNodeWeights.get(currentNode)));
    }


    private final HashMap<Integer, String> letterToInteger = createHashMap();

    private HashMap<Integer, String> createHashMap() {
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
