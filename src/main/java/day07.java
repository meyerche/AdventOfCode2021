import shared.loadInputFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class day07 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(
                Objects.requireNonNull(
                        loadInputFile.class.getClassLoader().getResourceAsStream("day07_input.txt")
                )
        );

        //initialize fish list
        List<Integer> crabPos = Arrays.stream(scanner.nextLine().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Integer minPos = IntStream.range(Collections.min(crabPos), Collections.max(crabPos))
                .map(x -> FindSum(x, crabPos))
                .reduce(Integer.MAX_VALUE, (x1,x2) -> Math.min(x1,x2));

        Integer minPos2 = IntStream.range(Collections.min(crabPos), Collections.max(crabPos))
                .map(x -> FindSum2(x, crabPos))
                .reduce(Integer.MAX_VALUE, (x1,x2) -> Math.min(x1,x2));

        System.out.println(minPos);
        System.out.println(minPos2);

//        Integer pos = (Collections.max(crabPos) + Collections.min(crabPos))/2;
//        Integer fuelSum = FindSum(pos, crabPos);
//
//        Integer moveL;
//        Integer moveR;
//        while(true) {
        //}
    }

    private static Integer FindSum(Integer pos, List<Integer> crabPos) {
        return crabPos.stream().mapToInt(x -> Math.abs(x - pos)).sum();
    }

    private static Integer FindSum2(Integer pos, List<Integer> crabPos) {
        return crabPos.stream()
                .mapToInt(x -> SingleMove(Math.abs(x-pos)))
                .sum();
    }

    private static Integer SingleMove(int x) {
        return IntStream.rangeClosed(1, x).sum();
    }
}
