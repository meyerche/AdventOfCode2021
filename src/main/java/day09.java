import shared.loadInputFile;

import java.util.*;
import java.util.stream.IntStream;

public class day09 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(
                Objects.requireNonNull(
                        loadInputFile.class.getClassLoader().getResourceAsStream("day09_input.txt")
                ));

        //Part1(scanner);

        Part2(scanner);

    }

    private static void Part2(Scanner scanner) {
        //create hashmap of all the points
        List<Integer> points = new ArrayList<>();
        int idx;
        int len = 0;

        while(scanner.hasNext()) {
            String[] newline = scanner.nextLine().split("");
            len = newline.length;
            IntStream.range(0,newline.length).forEach(i -> points.add(Integer.parseInt(newline[i])));
        }

        HashSet<Integer> pointsChecked = new HashSet<>();
        List<Integer> basinSizes = new ArrayList<>();

        idx = 0;
        while (idx < points.size()) {

            if (!pointsChecked.contains(idx) && points.get(idx) != 9) {
                basinSizes.add(FindBasin(idx, len, points, pointsChecked)) ;
            }

            idx++;
        }

        System.out.println(basinSizes.stream().sorted(Comparator.reverseOrder()).limit(3).reduce(1,(x,y)->x*y));
    }

    private static Integer FindBasin(int idx, int lineLength, List<Integer> points, HashSet<Integer> pointsChecked) {
        int count = 0;
        Stack<Integer> pointsToCheck = new Stack<>();
        pointsToCheck.push(idx);

        int i, up, right, down, left;

        List<Integer> pointsInBasin = new ArrayList<>();

        while (!pointsToCheck.isEmpty()) {
            i = pointsToCheck.remove(0);
            up = i - lineLength;
            right = i % lineLength == lineLength - 1 ? Integer.MAX_VALUE : i + 1;
            down = i + lineLength;
            left = i % lineLength == 0 ? -1 : i - 1;

            //check neighbors
            if (!pointsChecked.contains(i)) {
                pointsChecked.add(i);
                count++;
                pointsInBasin.add(i);

                if (up >= 0 && points.get(up) < 9 && !pointsChecked.contains(up)) {
                    pointsToCheck.push(up);
                }
                if (right < points.size() && points.get(right) < 9 && !pointsChecked.contains(right)) {
                    pointsToCheck.push(right);
                }
                if (down < points.size() && points.get(down) < 9 && !pointsChecked.contains(down)) {
                    pointsToCheck.push(down);
                }
                if (left >= 0 && points.get(left) < 9 && !pointsChecked.contains(left)) {
                    pointsToCheck.push(left);
                }
            }
        }

        return count;
    }

    private static void Part1(Scanner scanner) {
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

//6744295 too high