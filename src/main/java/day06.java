import shared.loadInputFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class day06 {
    public static List<Integer> fish;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(
                Objects.requireNonNull(
                        loadInputFile.class.getClassLoader().getResourceAsStream("day06_input.txt")
                )
        );

        //initialize fish list
        fish = Arrays.stream(scanner.nextLine().split(",")).map(Integer::parseInt).collect(Collectors.toList());
        //fish = Arrays.stream("3,4,3,1,2".split(",")).map(Integer::parseInt).collect(Collectors.toList());

        int days = 80;

        for (int i = 0; i < days; i++) {
            fish = fish.stream().map(x -> x-1).collect(Collectors.toList());
            IntStream.range(0,fish.size()).forEach(day06::addFish);
        }
    }

    private static void addFish(Integer i) {
        if (fish.get(i) == -1) {
            fish.add(8);
            fish.set(i, 6);
        }
    }

    //part2 guess...
    //1619067452340 (high)
}
