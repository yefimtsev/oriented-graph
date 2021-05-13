package com.github.kpi.branchnbounds;

import java.util.*;
// a = 0, g = 6
public class ProblemSolver {

    public ProblemSolver() {
        printMatrix();
//        System.out.println(findChildNodes(0));
        System.out.println(letterToInteger);
    }

    int[][] matrix = {
            {0, 2, 4, 5, 0, 0, 0},
            {0, 0, 1, 0, 5, 12, 0},
            {0, 0, 0, 0, 0, 9, 18},
            {0, 0, 0, 0, 0, 0, 8},
            {0, 0, 0, 0, 0, 4, 0},
            {0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0}};

    ArrayList<Integer> final_path = new ArrayList<>();

    public void printMatrix() {
        for (int[] ints : matrix) {
            for (int value : ints) {
                System.out.printf("%5d ", value);
            }
            System.out.println();
        }
    }

    private Map<Integer, Integer> findChildNodes(int parentNode, int currentNode) {
        Map<Integer, Integer> childNodes= new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[currentNode][i] != 0) {
                childNodes.put(i, calculateNodeWeight(parentNode, currentNode));
            }
        }

        return childNodes;
    }

    private int calculateNodeWeight(int parentNode, int currentNode) {

        return 0;
    }

    private final HashMap<String, Integer> letterToInteger = createHashMap();


    private HashMap<String, Integer> createHashMap() {
        HashMap<String, Integer> temp = new HashMap<>();
        temp.put("a", 0);
        temp.put("b", 1);
        temp.put("c", 2);
        temp.put("d", 3);
        temp.put("e", 4);
        temp.put("f", 5);
        temp.put("g", 6);
        temp.put("h", 7);
        temp.put("i", 8);
        return temp;
    }
}
