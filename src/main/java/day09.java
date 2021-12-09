import shared.loadInputFile;

import java.util.*;
import java.util.stream.IntStream;

public class day09 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(
                Objects.requireNonNull(
                        loadInputFile.class.getClassLoader().getResourceAsStream("day09_input.txt")
                ));

        List<Integer> lowPoints = new ArrayList<>();
        String prev = "";
        String cur = "";
        String next = "";

        while (scanner.hasNext()) {
            next = scanner.nextLine();

            if (!cur.isBlank()){
                FindMoreLowPoints(lowPoints, prev, cur, next);
            }

            prev = cur;
            cur = next;
            next = "";
        }

        //run one more time for the last line
        FindMoreLowPoints(lowPoints, prev, cur, next);

        System.out.println(lowPoints.stream().reduce(0, (x,y) -> x + y + 1));
    }

    private static void FindMoreLowPoints(List<Integer> lowPoints, String prev, String cur, String next) {
        int centerPoint;
        List<Integer> points = new ArrayList<>(Collections.nCopies(4,Integer.MAX_VALUE));

        for (int i = 0; i < cur.length(); i++) {
            centerPoint = Integer.parseInt(String.valueOf(cur.charAt(i)));
            if (!prev.isBlank()) points.set(0, Integer.parseInt(String.valueOf(prev.charAt(i))));
            if (i < cur.length() - 1) points.set(1, Integer.parseInt(String.valueOf(cur.charAt(i+1))));
            if(!next.isBlank()) points.set(2, Integer.parseInt(String.valueOf(next.charAt(i))));
            if (i > 0) points.set(3, Integer.parseInt(String.valueOf(cur.charAt(i-1))));

            if (centerPoint < points.stream().min(Integer::compare).get()) {
                lowPoints.add(centerPoint);
            }

            points.replaceAll(x -> Integer.MAX_VALUE);
        }

    }
}

//9144 (too high)
//1774 too high
//1327 too high
//