import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static shared.loadInputFile.ReadFileString;

public class day05 {
    public static void main(String[] args) {
        HashMap<List<Integer>, Integer> pointMap = new HashMap<>();

        List<String> data = ReadFileString("day05_input.txt");

        //add points to the hash map
        data.forEach(x -> addPoints(x, pointMap));

        //find the points in the hash map that were added more than once
        Long ans = pointMap.keySet().stream()
                .filter(p -> pointMap.get(p) > 1)
                //.peek(p -> System.out.println(p.toString() + " " + pointMap.get(p)))
                .count();

        System.out.println(ans);

    }

    private static void addPoints(String endpoints, HashMap<List<Integer>, Integer> pointMap) {
        //input string in the form "(x1,y1) -> (x2,y2)"
        //process endpoint string to get list of 4 points
        endpoints = endpoints.replaceAll("[^0-9]+", " ");
        List<Integer> points = Arrays.stream(endpoints.trim().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        //set x and y points for brevity later on
        int x1 = points.get(0), x2 = points.get(2), y1 = points.get(1) , y2 = points.get(3);

        //if only considering vertical (x1=x2) or horizontal (y1=y2) lines, remove "else"
        if (x1 == x2) {
            IntStream.rangeClosed(Math.min(y1,y2), Math.max(y1,y2)).forEach(y -> addSinglePoint(x1, y, pointMap));
        }
        else if (y1 == y2) {
            IntStream.rangeClosed(Math.min(x1,x2), Math.max(x1,x2)).forEach(x -> addSinglePoint(x, y1, pointMap));
        }
        else {
            //find direction of slope
            int dx = x2 - x1 > 0 ? 1 : -1;
            int dy = y2 - y1 > 0 ? 1 : -1;

            IntStream.rangeClosed(0, Math.abs(x1-x2))
                    .forEach(d -> addSinglePoint(x1 + d*dx, y1 + d*dy, pointMap));
        }


    }

    private static void addSinglePoint(int x, int y, HashMap<List<Integer>, Integer> pointMap) {
        List<Integer> point = Arrays.asList(x,y);
        Integer value = pointMap.get(point);

        if (value == null) {
            value = 1;
        } else {
            value += 1;
        }

        pointMap.put(point, value);
    }

}
