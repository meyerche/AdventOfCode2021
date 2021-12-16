import shared.loadInputFile;

import java.util.*;
import java.util.stream.IntStream;

public class day15 {
    public static HashMap<List<Integer>, Integer> riskMap;
    public static HashMap<List<Integer>, Integer> riskTot;
    public static int[] dx = {1, 0, -1, 0};
    public static int[] dy = {0, 1, 0, -1};
    private static Comparator<List<Integer>> PriorityComparator;
    private static int DATA_SIZE = 10;

    public static void main(String[] args) {
        List<String> data = loadInputFile.ReadFileString("day15_input.txt");

        riskMap = new HashMap<>();
        riskTot = new HashMap<>();

        //populate risk and sumRisk maps
        IntStream.range(0,data.size())
                .forEach(
                        r -> IntStream.range(0, data.get(r).length())
                                .forEach(c -> {
                                    riskMap.put(List.of(r,c), Integer.parseInt(data.get(r).split("")[c]));
                                    riskTot.put(List.of(r,c), Integer.MAX_VALUE);
                                }));



        FindPath(List.of(0,0));  //start from (0,0)

        int sizeX = data.get(0).length();
        int sizeY = data.size();

        System.out.println(riskTot.get(List.of(sizeX-1, sizeY-1)));

        for (int i = 0; i < DATA_SIZE; i++) {
            for (int j = 0; j < DATA_SIZE; j++) {
                System.out.print(riskTot.get(List.of(i,j)));
                System.out.print("  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void FindPath(List<Integer> start) {
        riskTot.put(start, 0);

        PriorityQueue<List<Integer>> prQueue = new PriorityQueue<>(1, new PriorityComparator());

        prQueue.add(start);

        while (!prQueue.isEmpty()) {
            List<Integer> curPoint = prQueue.poll();

            for (int i = 0; i < 4; i++) {
                //get each direction
                List<Integer> nextMapCoord = List.of(curPoint.get(0) + dx[i], curPoint.get(1) + dy[i]);

                if (riskMap.containsKey(nextMapCoord)) {
                    if (riskTot.get(nextMapCoord) > riskTot.get(curPoint) + riskMap.get(nextMapCoord)) {

                        //if already visited, remove from queue
                        prQueue.remove(nextMapCoord);

                        //add the new point back in
                        riskTot.put(nextMapCoord, riskMap.get(nextMapCoord) + riskTot.get(curPoint));
                        prQueue.add(nextMapCoord);
                    }
                }
            }
        }

    }

    static class PriorityComparator implements Comparator<List<Integer>> {

        @Override
        public int compare(List<Integer> o1, List<Integer> o2) {
            return riskTot.get(o1).compareTo(riskTot.get(o2));
        }
    }

    //442 - high
}
