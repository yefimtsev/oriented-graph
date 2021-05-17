package com.github.kpi.branchnbounds;
//private HashMap<Integer, String> createLettersToIntegersMap() {
//        HashMap<Integer, String> temp = new HashMap<>();
//        temp.put(0, "a");
//        temp.put(1, "b");
//        temp.put(2, "c");
//        temp.put(3, "d");
//        temp.put(4, "e");
//        temp.put(5, "f");
//        temp.put(6, "g");
//        temp.put(7, "h");
//        temp.put(8, "i");
//        return temp;
//        }
public class Main {
    public static void main(String[] args) {
//        new ProblemSolver();
        long startTime = System.nanoTime();
        new BBSolver();
        long stopTime = System.nanoTime();
        double elapsedTimeInSecond = (double) (stopTime - startTime) / 1_000_000_000;
        System.out.println(elapsedTimeInSecond + " seconds");
    }
}
